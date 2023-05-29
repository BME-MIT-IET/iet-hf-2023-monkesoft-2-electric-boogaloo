package iet.hf.monkesoft.electric.boogaloo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Interface{

	View view;
	Game game;
	String [] commandNames = {
		"help",
		"random",
		"load", 
		"save",
		"run",
		"field",
		"lab",
		"storage",
		"shelter",
		"virologist",
		"neighbour",
		"addgeneticcode",
		"addvirus",
		"addvaccine",
		"addeffect",
		"addmaterial",
		"addequipment",
		"look",
		"move",
		"usevirus",
		"usevaccine",
		"interact",
		"steal",
		"equip",
		"unequip",
		"discard",
		"make",
		"end",
		"exit",
		"kill"
	};
	HashMap<String, Command> commands = new HashMap<>();
	
	// method for GUI to call
	public void GUIcommand(String command, Object ... args)
	{
		String s = command + " ";
		for(Object a : args)
			s += a.toString() + " ";
		processCommand(s);
	}
	
	public static void main(String[] args) {
		new Interface();
	}
	Interface(){
		game = new Game();
		for(String name : commandNames)
		{
			try {
				commands.put(
					name,
						(Command) Class.forName("iet.hf.monkesoft.electric.boogaloo.Interface$" + name)
					// nested non-static class konstruktor�nak elej�re odaker�l a k�ls� oszt�ly
						.getDeclaredConstructor(Interface.class)
						.newInstance(this)
				);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				Println("No such command: " + name);
				e.printStackTrace();
			}
		}
		
		view = new View(this);
	}
	
	static TestRandom Random = new Interface.TestRandom();
	/**
	 * Extended random class for testing. Randomization can be turned off and predetermined value can be set.
	 */
	static class TestRandom extends Random
	{
		private static final long serialVersionUID = 3286206953995722174L;
		/**
		 * tells if the randomizer should be turned off for testing purposes
		 */
		boolean isDeterministic = false;
		/**
		 * when isDeterministic is true, this is returned instead of a random value
		 */
		int intResult = 1;
		boolean booleanResult = false;
		@Override
		public boolean nextBoolean()
		{
			return isDeterministic ? booleanResult: super.nextBoolean();
		}
		boolean nextBoolean(double chance)
		{
			return isDeterministic ? booleanResult: super.nextDouble() < chance;
		}
		/*
		 * Returns a random integer between min and max. 
		 * Warning: this method does not overwrite Random.nextInt 
		 */
		public int nextInt(int min, int max)
		{
			return isDeterministic ? intResult : max == min ? max : super.nextInt(max) + min;
		}
		@Override
		public int nextInt(int max)
		{
			return nextInt(0, max);
		}
	}
	
	String output = "";
	void Print(String s)
	{
		output += s;
	}
	void Println(String s)
	{
		output += s + "\n";
	}
	void Println()
	{
		output += "\n";
	}
	/**
	 * @param in The input stream containing commands
	 * @param printCommands tells if the command should be printed
	 */
	void processInput(InputStream in, boolean printCommands)
	{
		Scanner sc = new Scanner(in);
		while(true)
		{
			if(!sc.hasNextLine())
				break;
			String line = sc.nextLine();
			if(printCommands)
				Println(line);
			if(processCommand(line))
				break;
		}
		sc.close();
	}
	/**
	 * Processes a line of command
	 * @param line The line with one command ans its arguments
	 * @return True, if exit command was found, false otherwise
	 */
	boolean processCommand(String line) 
	{
		line = line.trim(); 
		if(line.equals(""))
			return false;
		if(line.equals("exit"))
			return true;
		String name = splitCommand(line)[0].toLowerCase();
		int cut = line.indexOf(" ");
		String [] args;
		if(cut == -1)
			args = new String[0];
		else
			args = splitCommand(line.substring(cut + 1));
		try {
			Command command = commands.get(name);
			command.execute(args);
		} catch (IllegalArgumentException | SecurityException | NullPointerException e) {
			Println("No such command: \"" + name + "\"");
			Println("Type \"help\" for list of commands");
			e.printStackTrace();
		} catch (ArgumentException e) {
			Println("Argument error");
			e.Println();
		}
		return false;
	}
	
	String [] splitCommand(String command)
	{
		return command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	}

	String nf = " not found";
	
	GeneticCode getGeneticCode(String type) throws ArgumentException
	{
		GeneticCode res = game.FindGeneticCode(type.toLowerCase());
		if(res == null)
			throw new ArgumentException("Genetic code " + type + nf);
		return res;
	}
	Effect getEffect(String type) throws ArgumentException 
	{
		Effect res = game.FindEffect(type.toLowerCase());
		if(res == null)
			throw new ArgumentException("Effect " + type + nf);
		return res;
	}
	Equipment createEquipment(String type, int id) throws ArgumentException
	{
		Equipment eq = null;
		switch(type.toLowerCase())
		{
			case "cape":
				eq = new Cape(game.FindEffect("general"), id);
				break;
			case "glove":
				eq = new Glove(game.FindEffect("reflect"), id);
				break;
			case "bag":
				eq = new Bag(game.FindEffect("inventoryincrease"), id);
				break;
			case "axe":
				eq = new Axe(id);
				break;
			default:
				throw new ArgumentException("Equipment type " + type + nf);
		}
		return eq;
	}
	Field getField(int id) throws ArgumentException
	{
		Field res = Field.list.get(id);
		if(res == null)
		{
			throw new ArgumentException("Field " + id + nf);
		}
		return res;
	}
	Virologist getVirologist(int id) throws ArgumentException
	{
		Virologist v = Virologist.list.get(id);
		if(v == null)
			throw new ArgumentException("Virologist " + id + nf);
		return v;
	}

	/**
	
	 * Thrown when the user makes a mistake in a command argument
	 */
	public class ArgumentException extends Exception {
		private static final long serialVersionUID = 4230380829341466785L;
		String error;
		public ArgumentException(String error) {
			this.error = error;
		}
		public void Println()
		{
			Interface.this.Println(error);
		}
	}
	abstract class Command {
		public Command() {};
		/**
		 * Executes the command
		 * @param args Command line arguments
		 * @throws ArgumentException When the arguments does not match the requirements
		 */
		public abstract void execute(String [] args) throws ArgumentException;
		/**
		 * @return the name of the command and its arguments
		 */
		public abstract String getName();
		/**
		 * @return description of the command
		 */
		public abstract String getDescription();
		/**
		 * Prints the name and description of the command
		 */
		public void help() {
			Println(getName());
			Println("\t" + getDescription());
		}
		/**
		 * Checks if the number of arguments reach the required number
		 * Supposed to be called at the beginning of execute()
		 * @param args The args of the running command
		 * @param min The minimum number of arguments
		 * @throws ArgumentException When min > 0 and number of arguments < min
		 */
		public void validate(String [] args, int min) throws ArgumentException
		{
			if(args.length < min && min > 0)
				throw new ArgumentException(min + " arguments needed");
		}
	}
	// options
	class help extends Command {
		public void execute(String [] args)
		{
			if(args.length > 0)
			{
				Command c = commands.get(args[0]);
				if(c == null)
					Println("No such command: " + args[0]);
				else
					c.help();
			} else 
			{
				Println("List of available commands: ");
				for(String name : commandNames)
				{
					if(!name.equals("help"))
					{
						commands.get(name).help();
					}
				}
			}
		}
		@Override
		public String getName() {
			return "help COMMAND";
		}
		@Override
		public String getDescription() {
			return "Lists available commands";
		}
		
	}
	class random extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			if(args[0].toLowerCase().equals("on"))
			{
				Random.isDeterministic = false;
			} else if(args[0].toLowerCase().equals("off")){
				try {
					if(args[1].equals("false"))
					{
						Random.booleanResult = false;
					} else if(args[1].equals("true"))
					{
						Random.booleanResult = true;
					} else 
					{
						Random.intResult = Integer.parseInt(args[1]);
					}
					Random.isDeterministic = true;
				} catch(IndexOutOfBoundsException | NumberFormatException e) {
					throw new ArgumentException("Please provide an integer or true/false");
				}
			} else {
				throw new ArgumentException("First argument must be on/off");
			}
		}
		@Override
		public String getName() {
			return "random on/off [VALUE]";
		}
		@Override
		public String getDescription() {
			return "Turns on/off randomization. When turned off, an integer VALUE must be provided to return instead randomizing. For boolean values, use 0 or 1.";
		}
	}
	class load extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
				game = (Game) in.readObject();
				in.close();
			} catch (IOException | ClassNotFoundException e) {
				Println("File error:");
				e.printStackTrace();
			}
		}
		public String getName() {
			return "load FILE";
		}
		public String getDescription() {
			return "Loads a game state from FILE";
		}
	}
	class save extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(args[0]));
				objectOutputStream.writeObject(game);
				objectOutputStream.flush();
				objectOutputStream.close();
			} catch (IOException e) {
				Println("File error:");
				e.printStackTrace();
			}
		}
		public String getName() {
			return "save FILE";
		}
		public String getDescription() {
			return "Saves a game state into FILE";
		}
	}
	String filenameinput(String filename)
	{
		if(filename.charAt(0) == '\"')
		{
			filename = filename.substring(1);
		}
		if(filename.charAt(filename.length() - 1) == '\"')
		{
			filename = filename.substring(0, filename.length() - 1);
		}
		return filename;
	}
	class run extends Command {
		public void execute(String [] args) throws Interface.ArgumentException
		{
			validate(args, 1);
			String arg = filenameinput(args[0]);
			String filename = ".\\teszt\\" + arg;
			output = "";
			try {
				processInput(new FileInputStream(filename), false);
			} catch (FileNotFoundException e) {
				throw new ArgumentException("File \"" + filename + "\" not found");
			}
			// compare output
			String filename1 = ".\\output\\" + filenameinput(args[0]);
			ArrayList<String> expectedLines;
			try {
				expectedLines = (ArrayList<String>) Files.readAllLines(Paths.get(filename1));
			} catch (IOException e) {
				throw new ArgumentException("File \"" + filename1 + "\" not found");
			}
			ArrayList<String> actualLines = new ArrayList<String>(Arrays.asList(output.split("\n")));
			int i = 1;
			boolean ok = true;
			for(i = 0; i < expectedLines.size(); i++)
			{
				if(!expectedLines.get(i).trim().toLowerCase()
					.equals(actualLines.get(i).trim().toLowerCase()))
				{
					ok = false;
					break;
				}
			}
			Println("-----------");
			Println(arg);
			if(ok)
			{
				Println("Passed");
			} else 
			{
				Println("Failed at line " + i);
				Println("Expected:\n\"" + expectedLines.get(i) + "\"");
				Println("Actual:\n\"" + actualLines.get(i) + "\"");
			}
			Println("-----------");
		
		}
		public String getName() {
			return "run FILE";
		}
		public String getDescription() {
			return "Runs commands from FILE";
		}
	}
	// generation
	class field extends Command {
		public void execute(String [] args) throws Interface.ArgumentException
		{
			Field f;
			int id = 0;
			if(args.length > 0)
			{
				try {
					id = Integer.parseInt(args[0]);
				} catch (NumberFormatException e)
				{
					throw new ArgumentException("FIELD_ID must be a number");
				}
			}
			f = new Field(id);
			game.GetTown().AddField(f);
		}
		public String getName() {
			return "field [FIELD_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class lab extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Laboratory f;
			int id = 0;
			if(args.length > 2)
			{
				try {
					id = Integer.parseInt(args[2]);
				} catch (NumberFormatException e)
				{
					throw new ArgumentException("FIELD_ID must be a number");
				}
			} 
			if(args[1].toLowerCase().equals("i"))
				f = new Laboratory(getGeneticCode(args[0]), getEffect("bearconfusion"), id);
			else if(args[1].toLowerCase().equals("n"))
				f = new Laboratory(getGeneticCode(args[0]), id);
			else
				throw new ArgumentException("2nd argument must be i or n");
			game.GetTown().AddField(f);
		}
		public String getName() {
			return "lab GENETIC_CODE i/n [FIELD_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class storage extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Storage f;
			int id = 0;
			if(args.length > 2)
			{
				try {
					id = Integer.parseInt(args[2]);
				} catch (NumberFormatException e)
				{
					throw new ArgumentException("FIELD_ID must be a number");
				}
			}
			try {
				f = new Storage(Integer.parseInt(args[0]), Integer.parseInt(args[1]), id);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("AMINO_ACID and NUCLEOTID must be numbers");
			}
			game.GetTown().AddField(f);
		}
		public String getName() {
			return "storage AMINO_ACID NUCLEOTID [FIELD_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class shelter extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			Shelter f;
			int field_id = 0, equipment_id = 0;
			try {
				if(args.length > 1)
					field_id = Integer.parseInt(args[1]);
				if(args.length > 2)
					equipment_id = Integer.parseInt(args[2]);
			}catch (NumberFormatException e)
			{
				throw new ArgumentException("FIELD_ID and EQUIPMENT_ID must be numbers");
			}
			f = new Shelter(createEquipment(args[0], equipment_id), field_id);
			
			game.GetTown().AddField(f);
		}
		public String getName() {
			return "shelter EQUIPMENT [FIELD_ID] [EQUIPMENT_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class virologist extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			int field_id;
			try {
				field_id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("FIELD_ID must be a number");
			}
			Field f = getField(field_id);
			
			int id = 0;
			if(args.length > 1)
			{
				try {
					id = Integer.parseInt(args[1]);
				} catch (NumberFormatException e)
				{
					throw new ArgumentException("VIROLOGIST_ID must be a number");
				}
			}
			Virologist v = new Virologist(id);
			game.AddVirologist(v);
			v.SetField(f);
			f.Add(v);
		}
		public String getName() {
			return "virologist FIELD_ID [VIROLOGIST_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class neighbour extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			try {
				Field a = getField(Integer.parseInt(args[0]));
				Field b = getField(Integer.parseInt(args[1]));
				if(a == b)
					throw new ArgumentException("Field cannot be neighbour with itself");
				
				a.AddNeighbour(b);
				b.AddNeighbour(a);		
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("FIELD_ID must be a number");
			}
		}
		public String getName() {
			return "neighbour FIELD_ID FIELD_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class addgeneticcode extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID must be a number");
			}
			v.AddGenCode(getGeneticCode(args[1]));
		}
		public String getName() {
			return "addgeneticcode VIROLOGIST_ID GENETIC_CODE";
		}
		public String getDescription() {
			return "";
		}
	}
	class addvirus extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v;
			int a_id = 0;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
				if(args.length > 2)
					a_id = Integer.parseInt(args[2]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID and AGENT_ID must be numbers");
			}
			v.AddAgent(new Virus(getGeneticCode(args[1]).getVirus(), a_id));
		}
		public String getName() {
			return "addvirus VIROLOGIST_ID GENETIC_CODE [AGENT_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class addvaccine extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v;
			int a_id = 0;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
				if(args.length > 2)
					a_id = Integer.parseInt(args[2]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID and AGENT_ID must be numbers");
			}
			v.AddAgent(new Vaccine(getGeneticCode(args[1]).getVaccine(), a_id));
		}
		public String getName() {
			return "addvaccine VIROLOGIST_ID GENETIC_CODE [AGENT_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class addeffect extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID must be a number");
			}
			Effect e;
			if(args[1].toLowerCase().equals("individual"))
			{
				if(args.length > 2)
				{
					e = new Individual(getEffect(args[2]));
				} else
				{
					throw new ArgumentException("Please provide an effect to protect against.");
				}
			} else
			{
				e = getEffect(args[1]);
			}
			new AppliedEffect(v, e);
		}
		public String getName() {
			return "addeffect VIROLOGIST_ID EFFECT [PROTECT_FROM_EFFECT]";
		}
		public String getDescription() {
			return "";
		}
	}
	class addmaterial extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 3);
			Virologist v;
			int amino, nucleo;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
				amino = Integer.parseInt(args[1]);
				nucleo = Integer.parseInt(args[2]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID, AMINO_ACID and NUCLEOTID must be numbers");
			}
			for(Material m : v.getMaterialList())
			{
				m.AddAminoAcid(amino);
				m.AddNucleotid(nucleo);
			}
		}
		public String getName() {
			return "addmaterial VIROLOGIST_ID AMINO_ACID NUCLEOTID";
		}
		public String getDescription() {
			return "";
		}
	}
	class addequipment extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v;
			int eq_id = 0;
			String eq_name;
			try
			{
				v = getVirologist(Integer.parseInt(args[0]));
				eq_name = args[1];
				if(args.length > 2)
					eq_id = Integer.parseInt(args[2]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID and EQUIPMENT_ID must be numbers");
			}
			
			Equipment eq = createEquipment(eq_name, eq_id);
			v.AddEquipment(eq);
			
		}
		public String getName() {
			return "addequipment VIROLOGIST_ID EQUIPMENT [EQUIPMENT_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	// player actions
	class look extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			Field current = null;
			if(args.length < 1)
			{
				Virologist v = null;
				try
				{
					v = game.GetActualVirologist();
				} catch(IndexOutOfBoundsException e)
				{
					Println("No virologists");
					return;
				}
				
				Println("Materials:");
				int amino = 0, nucleo = 0;
				for(Material m : v.getMaterialList())
				{
					amino += m.getAminoAcid();
					nucleo += m.getNucleotid();
				}
				Println("\tAmino acid: " + amino);
				Println("\tNucleotid: " + nucleo);
				
				Println("Equipments:");
				Println("\tEquipped:");
				for(Equipment eq : v.getEquippedEquipments())
					Println("\t\t("+ eq.Id + ") " + eq.getType());
				Println("\tInventory:");
				for(Equipment eq : v.getEquipmentInventory())
					Println("\t\t("+ eq.Id + ") " + eq.getType());
				
				Println("Agent inventory:");
				ArrayList<Agent> viruses = new ArrayList<Agent>();
				ArrayList<Agent> vaccines = new ArrayList<Agent>();
				for(Agent a : v.getAgentInventory())
					if(a.getClass() == Virus.class)
						viruses.add(a);
					else
						vaccines.add(a);
				
				Println("\tViruses:");
				for(Agent a : viruses)
					Println("\t\t"+ a.toString());
				Println("\tVaccines:");
				for(Agent a : vaccines)
					Println("\t\t"+ a.toString());
				
				Println("Effects:");
				for(AppliedEffect e : v.getAe())
					Println("\t" + e.toString());
				
				Println("Known genetic codes:");
				for(GeneticCode g : v.getCodeList())
					Println("\t" + g.getName());
				
				if (v.haveWon()) {
					Println("Virologist (" + v.Id + ") have won the game!");
				}
				
				if(!v.Stunned())
				{
					current = v.getField();
				}
			} else 
			{
				try
				{
					current = getField(Integer.parseInt(args[1]));
				} catch(NumberFormatException e)
				{
					throw new ArgumentException("FIELD_ID must be a number"); 
				}
			}
			
			if(current != null)
			{
				Println("Current location:");
				Println(current.toString());
				for(Virologist v2 : current.GetVirologists())
				{
					Println(v2.toString());
				}
				Println("Neighbours:");
				for(Field f : current.GetNeighbours())
				{
					Println(f.toString());
					for(Virologist v2 : f.GetVirologists())
					{
						Println(v2.toString());
					}
				}
			}
		}
		public String getName() {
			return "look";
		}
		public String getDescription() {
			return "";
		}
	}
	class move extends Command {
		public void execute(String [] args) throws NumberFormatException, ArgumentException
		{
			validate(args, 1);
			try {
				Virologist v = game.GetActualVirologist();
				Field f = v.getField();
				v.Move(getField(Integer.parseInt(args[0])));
				Field f2 = v.getField();
				if(f == f2)
				{
					Println("Virologist cannot step because the fields are not neighbours.");
				}
			} catch (NumberFormatException e)
			{
				Println("FIELD_ID must be a number");
			}
		}
		public String getName() {
			return "move FIELD_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class usevirus extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			int virus_id;
			Virologist v, v2;
			try
			{
				v2 = getVirologist(Integer.parseInt(args[1]));
				virus_id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID and AGENT_ID must be a number");
			}
			v = game.GetActualVirologist();
			Agent virus = null;
			for(Agent a : v.getAgentInventory())
			{
				if(a.Id == virus_id)
				{
					virus = a;
					break;
				}
			}
			if(virus == null)
				throw new ArgumentException("Virus id " + virus_id + nf);
			
			v.UseAgent(virus, v2);
		}
		public String getName() {
			return "usevirus AGENT_ID VIROLOGIST_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class usevaccine extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			int vaccine_id;
			Virologist v;
			try
			{
				vaccine_id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("AGENT_ID must be a number");
			}
			v = game.GetActualVirologist();
			Agent vaccine = null;
			for(Agent a : v.getAgentInventory())
			{
				if(a.Id == vaccine_id)
				{
					vaccine = a;
					break;
				}
			}
			if(vaccine == null)
				throw new ArgumentException("Vaccine id " + vaccine_id + nf);
			
			v.UseAgent(vaccine, v);
		}
		public String getName() {
			return "usevaccine AGENT_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class interact extends Command {
		public void execute(String [] args)
		{
			Virologist v = game.GetActualVirologist();
			v.Interact(v.getField());
		}
		public String getName() {
			return "interact";
		}
		public String getDescription() {
			return "The current player interacts with its location.";
		}
	}
	class steal extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			Virologist v = game.GetActualVirologist(), v2;
			try
			{
				v2 = getVirologist(Integer.parseInt(args[1]));
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID must be a number");
			}
			String o = args[0].toLowerCase();
			if(o.equals("n"))
			{
				v.StealNucleotid(v2, Integer.MAX_VALUE);
			} else if(o.equals("a"))
			{
				v.StealAminoAcid(v2, Integer.MAX_VALUE);
			} else if(o.equals("e"))
			{
				int id;
				try
				{
					id = Integer.parseInt(args[2]);
				} catch (NumberFormatException e)
				{
					throw new ArgumentException("EQUIPMENT_ID must be a number");
				}
				Equipment eq = null;
				for(Equipment e : v2.getEquippedEquipments())
					if(e.Id == id)
						eq = e;
				for(Equipment e : v2.getEquipmentInventory())
					if(e.Id == id)
						eq = e;
				if(eq == null)
					throw new ArgumentException("Equipment id " + id + nf);
				v.StealEq(v2, eq);
			} else 
			{
				throw new ArgumentException("First argument must be n/a/e");
			}
			
		}
		public String getName() {
			return "steal n/a/e [VIROLOGIST_ID] [EQUIPMENT_ID]";
		}
		public String getDescription() {
			return "";
		}
	}
	class equip extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			Virologist v = game.GetActualVirologist();
			int id;
			try
			{
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("EQUIPMENT_ID must be a number");
			}
			Equipment eq = null;
			for(Equipment e : v.getEquipmentInventory())
				if(e.Id == id)
					eq = e;
			if(eq == null)
				throw new ArgumentException("Equipment id " + id + nf);
			v.Equip(eq);
		}
		public String getName() {
			return "equip EQUIPMENT_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class unequip extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			Virologist v = game.GetActualVirologist();
			int id;
			try
			{
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("EQUIPMENT_ID must be a number");
			}
			Equipment eq = null;
			for(Equipment e : v.getEquippedEquipments())
				if(e.Id == id)
					eq = e;
			if(eq == null)
				throw new ArgumentException("Equipment id " + id + nf);
			v.UnEquip(eq);
		}
		public String getName() {
			return "unequip EQUIPMENT_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class discard extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 1);
			Virologist v = game.GetActualVirologist();
			int id;
			try
			{
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("EQUIPMENT_ID must be a number");
			}
			Equipment eq = null;
			for(Equipment e : v.getEquipmentInventory())
				if(e.Id == id)
					eq = e;
			for(Equipment e : v.getEquippedEquipments())
				if(e.Id == id)
					eq = e;
			if(eq == null)
				throw new ArgumentException("Equipment id " + id + nf);
			v.DisCardEquipment(eq);
		}
		public String getName() {
			return "discard EQUIPMENT_ID";
		}
		public String getDescription() {
			return "Removes EQUIPMENT_ID from inventory and deletes it";
		}
	}
	class make extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			int id = 0;
			try
			{
				if(args.length > 2)
					id = Integer.parseInt(args[2]);
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("AGENT_ID must be a number");
			}
			Virologist v = game.GetActualVirologist();
			GeneticCode gc = getGeneticCode(args[1]);
			boolean found = false;
			for(GeneticCode g : v.getCodeList())
				if(g == gc)
					found = true;
			if(!found)
				throw new ArgumentException("Genetic code " + args[1] + " not learned or does not exsist."); 
			
			int count = v.getAgentInventory().size();
			
			String o = args[0].toLowerCase();
			if(o.equals("virus"))
				v.MakeVirus(gc, id);
			if(o.equals("vaccine"))
				v.MakeVaccine(gc, id);
			
			if(count == v.getAgentInventory().size())
				Println("Virologist can not create " + args[1] + " virus. There is not enough material.");
		}
		public String getName() {
			return "make virus/vaccine GENETIC_CODE [AGENT_ID]";
		}
		public String getDescription() {
			return "Makes an agent of GENETIC_CODE an puts it into the inventory";
		}
	}
	class kill extends Command {
		public void execute(String [] args) throws ArgumentException
		{
			validate(args, 2);
			Virologist v, v2;
			v = game.GetActualVirologist();
			Axe axe = null;
			int axe_id;
			try
			{
				axe_id = Integer.parseInt(args[0]);
				v2 = getVirologist(Integer.parseInt(args[1]));
			} catch (NumberFormatException e)
			{
				throw new ArgumentException("VIROLOGIST_ID and AXE_ID must be numbers");
			}
			for(Equipment e : v.getEquippedEquipments())
				if(e.Id == axe_id)
					axe = (Axe) e;
			for(Equipment e : v.getEquipmentInventory())
				if(e.Id == axe_id)
					axe = (Axe) e;
			if(axe == null)
			{
				throw new ArgumentException("Axe id " + axe_id + nf);
			}
			v.UseEquipment(axe, v2);
		}
		public String getName() {
			return "kill AXE_ID VIROLOGIST_ID";
		}
		public String getDescription() {
			return "";
		}
	}
	class end extends Command {
		public void execute(String [] args)
		{
			game.EndTurn();
		}
		public String getName() {
			return "end";
		}
		public String getDescription() {
			return "Ends the current turn";
		}
	}
	// exit
	class exit extends Command {
		public void execute(String [] args)
		{
			// actually never gets called 
			// this class is only for description
		}
		public String getName() {
			return "exit";
		}
		public String getDescription() {
			return "Exits the program";
		}
	}
}
