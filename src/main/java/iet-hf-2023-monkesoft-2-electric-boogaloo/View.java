import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;

/**
 * A grafikus ablakot megjelenÃ­tÅ‘ osztÃ¡ly
 * */
public class View extends JFrame implements MouseListener{
	private JButton bEndTurn;
	private JButton interactions[];
	private ArrayList<JField> fields = new ArrayList<JField>();
	private ArrayList<JVirologist> virologists =  new ArrayList<JVirologist>();
	private ArrayList<JEquipment> equipments =  new ArrayList<JEquipment>();
	
	Interface iface;
	JPanel map;
	JInventory inventoryPanel;

	int mapwidth = 6;
	
	enum ViewState {
		none, 
		fieldSelect, 
		virologistSelectToVirus, 
		virologistSelectToKill,
		virologistSelectToLoot
	};
	
	ViewState state = ViewState.none;
	Agent selectedAgent;
	Axe selectedAxe;
	Virologist selectedVirologist = null;
	
	JButton bStealAmino = new JButton("Steal amino acid");
	JButton bStealNucleo = new JButton("Steal nucleotid");
	
	View(Interface iface) {
		this.iface = iface;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 900);
		setLayout(new BorderLayout());
		map = new JPanel();
		map.setLayout(new GridLayout(mapwidth, mapwidth));
		map.setBackground(Color.DARK_GRAY);
		map.setPreferredSize(new Dimension(900,900));
		add(map, BorderLayout.LINE_START);
		setLocationRelativeTo(null);  
		
		bStealAmino.addMouseListener(this);
		bStealNucleo.addMouseListener(this);
		
		newGame();
	}
	
	public void EndGame() {
		JFrame end = new JFrame();
		JPanel ep = new JPanel();
		end.setDefaultCloseOperation(EXIT_ON_CLOSE);
		end.setSize(300, 300);
		ep.setLayout(new BorderLayout());
		ep.setAlignmentX(CENTER_ALIGNMENT);
		JLabel wintext = new JLabel("                                         GG EZ");
		wintext.setAlignmentX(CENTER_ALIGNMENT);
		Icon icon = new ImageIcon("resources/monki-flip-monkey.gif");
        JLabel label = new JLabel(icon);
        ep.add(wintext, BorderLayout.NORTH);
        ep.add(label, BorderLayout.SOUTH);
        end.add(ep);
        end.setLocationRelativeTo(null);
        end.setVisible(true);
        
        try {
        	File audioFile = new File("resources/monkemusic.wav");
        	AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());  
        	Clip clip = AudioSystem.getClip();
        	clip.open(audioIn);
        	clip.start();
        	audioIn.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
	}
	
	protected void newGame()
	{
		// generate players
		int numberOfPlayers = 0;
		String extra = "";
		while(true){
			String s = (String)JOptionPane.showInputDialog(
                this,
                "Number of players:\n"+ extra,
                "Szia uram",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "2"
            );
	
			if ((s != null) && (s.length() > 0)) {
				try {
					numberOfPlayers = Integer.parseInt(s);
					break;
				} catch(NumberFormatException e)
				{
					extra = "Numbert write-olj te buta majom";
				}
			}
		}
		Random rnd = new Random();
		ArrayList<Field> modelFields = new ArrayList<>();
		// generate map
		
		//List that countains the position of at least 3 distinct labs
		//If these postitions are the ones in the loop, automatically
		//Generate a laboratory to ensure there are at least 3
		ArrayList<Integer> listOfLabPositions = new ArrayList<Integer>();
		while(listOfLabPositions.size() < 3) {
			int randomIndex = rnd.nextInt(mapwidth * mapwidth);
			if(!listOfLabPositions.contains(randomIndex)) {
				listOfLabPositions.add(randomIndex);
			}
		}
		
		//Numbers representing the ratios of fields (no shit xd)
		int fieldRatio = 80;
		int labRatio = 20;
		int shelterRatio = 20;
		int storageRatio = 20;
		int sumOfRatios = fieldRatio + labRatio + shelterRatio + storageRatio;
		
		for(int i = 0; i < mapwidth; i++)
		{
			for(int j = 0; j < mapwidth; j++)
			{
				boolean isPredeterminedLab = false;
				//SAJÁT BRÉ'SZTORMING
				int id = i * mapwidth + j;
				int type = 0;
				//If current position definitely contains a laboratory
				if(listOfLabPositions.contains(id)) {
					type = 1;
					isPredeterminedLab = true;
				} else {
				//Else, random generation
					int randomNumber = rnd.nextInt(sumOfRatios);
					//About 50% chance for a normal field
					if(randomNumber <  fieldRatio) {
						type = 0;
					} else if(randomNumber < fieldRatio + labRatio ) {
						//Lab
						type = 1;
					} else if( randomNumber < fieldRatio + labRatio + storageRatio ) {
						//Storage
						type = 2;
					} else {
						//Shelter
						type = 3;
					}
				}
				//SAJÁT BRÉSTORM VÉGE
				
				// TODO balance filed distribution
				String imgName = "";
				if(type == 0)
				{
					iface.GUIcommand("field", id);
					imgName = JDrawable.IMG_FIELD;
				} else if(type == 1)
				{
					String genCode = "";
					//If it was a predetermined lab position, then give
					//a predetermined genCode, so all genCodes are definitely obtainable
					if(isPredeterminedLab) {
						switch(listOfLabPositions.indexOf(id)) {
						case 0:
							genCode = "stun";
							break;
						case 1:
							genCode = "confusion";
							break;						
						case 2:
							genCode = "amnesia";
							break;
						}
					} else { //Else, randomize
						int r = rnd.nextInt(3);
						switch(r) {
						case 0:
							genCode = "stun";
							break;
						case 1:
							genCode = "confusion";
							break;
						case 2:
							genCode = "amnesia";
							break;
						}
					}
					String infected = rnd.nextFloat() < 0.1 ? "i": "n";
					iface.GUIcommand("lab", genCode, infected, id);
					imgName = JDrawable.IMG_LAB;
				} else if(type == 2)
				{
					int amino = rnd.nextInt(50);
					int nucleo = rnd.nextInt(50);
					iface.GUIcommand("storage", amino, nucleo, id);
					imgName = JDrawable.IMG_STORAGE;
				} else if(type == 3)
				{
					String eq = "";
					String img = "";
					int eqtype = rnd.nextInt(5);
					if(eqtype == 0)
					{
						eq = "cape";
						img = JDrawable.IMG_CAPE;
					} else if(eqtype == 1)
					{
						eq = "glove";
						img = JDrawable.IMG_GLOVE;
					} else if(eqtype == 2)
					{
						eq = "cape";
						img = JDrawable.IMG_CAPE;
					} else if(eqtype == 3)
					{
						eq = "axe";
						img = JDrawable.IMG_AXE;
					} else if(eqtype == 4)
					{
						eq = "bag";
						img = JDrawable.IMG_BAG;
					} 
					
					iface.GUIcommand("shelter", eq, id, equipments.size());
					imgName = JDrawable.IMG_SHELTER;
					/*
					JEquipment jeq = eq.equals("axe") ? new JAxe(this, iface, inventoryPanel, null, img) : new JEquipment(iface, inventoryPanel, null, img);
					equipments.add(jeq);
					*/
				} 
				ArrayList<Field> list = iface.game.GetTown().GetFields();
				
				// neighbouring
				if(j > 0)
					iface.GUIcommand("neighbour", id, i * mapwidth + (j - 1));
				if(i > 0)
					iface.GUIcommand("neighbour", id, (i - 1) * mapwidth + j);
				
				JField f = new JField(
					this,
					list.get(list.size() - 1),
					imgName, 
					i, j
				);
				fields.add(f);
				map.add(f);
			}
		}
		
		// add monke
		for(int i = 0; i < numberOfPlayers; i++)
		{
			int fieldid = rnd.nextInt(mapwidth * mapwidth);
			iface.GUIcommand("virologist", fieldid, i);
			Virologist v = iface.game.GetVirologists().get(iface.game.GetVirologists().size() - 1);
			JVirologist jv = new JVirologist(this, iface, v);
			
			fields.get(fieldid).add(jv);// EZ MI A FASZOM?!?!?!!?! Hozzaadom a jfieldhez a jvirologust te majom
			
			/////////////////////////////////////////////////////////////////////////////////////innentÅ‘l Ã�dÃ¡m Ã­rta
			virologists.add(jv);
		}
		for(JVirologist jv : virologists)
		{
			jv.ChangePicture();
		}
		
		inventoryPanel = new JInventory(iface, this);
		inventoryPanel.updateContent(getCurrentVirologist());
		add(inventoryPanel, BorderLayout.LINE_END);
		
		setVisible(true);
	}
	/**
	 * Kirajzolja a felÃ¼letet
	 * */
	protected void paintComponent(Graphics g) {
		
	}
	
	/**
	 * A soron kÃ¶vetkezÅ‘ virolÃ³gusra Ã¡llÃ­tja a CurrentVirologist vÃ¡ltozÃ³t. 
	 **/
	public void nextVirologist() {
		
	}
	
	/**
	 * Befejezi az aktuÃ¡lis virolÃ³gus kÃ¶rÃ©t, megvÃ¡ltoztatja a kÃ©pÃ©t 
	 * az eddigi Ã©s mostani virolÃ³gusnak, Ã©s ennek megfelelÅ‘en frissÃ­ti a kÃ©pernyÅ‘t.
	 * */
	public void onEndTurn() {
		this.getCurrentVirologist().EndTurn();
		iface.GUIcommand("end");
		for(JVirologist jv : virologists)
		{
			jv.ChangePicture();
		}
		inventoryPanel.updateContent(getCurrentVirologist());
		onStateUpdate(ViewState.none);
		repaint();
	}
	
	/**
	 * Visszaadja az Ã©ppen soron lÃ©vÅ‘ virolÃ³gust
	 * */
	public Virologist getCurrentVirologist() {
		return iface.game.GetActualVirologist();
	}
	
	public void onMoveClick() {
		onStateUpdate(state == ViewState.fieldSelect ? ViewState.none : ViewState.fieldSelect);
	}
	public void onVirusClick(Agent agent) {
		onStateUpdate(ViewState.virologistSelectToVirus);
		selectedAgent = agent;
		inventoryPanel.updateContent(null);
		inventoryPanel.setOptionButtons(null);
	}
	public void onAxeUseClick(Axe axe) {
		onStateUpdate(ViewState.virologistSelectToKill);
		selectedAxe = axe;
	}
	public void onLootClick() {
		onStateUpdate(ViewState.virologistSelectToLoot);
	}
	public void onStateUpdate(ViewState s)
	{
		// remove temp options 
		inventoryPanel.setOptionButtons(null);
		state = s;
		if(state == ViewState.fieldSelect)
		{
			for(JField jf : fields)
			{
				if(jf.getField().GetNeighbours().contains(virologists.get(iface.game.GetActualVirologist().Id).getVirologist().getField()))
						jf.setHighlight(true);
				else
					jf.setHighlight(false);
			}
		} else {
			for(JField jf : fields)
			{
				jf.setHighlight(false);
			}
		}
		if(state == ViewState.virologistSelectToVirus || state == ViewState.virologistSelectToKill)
		{
			for(JVirologist v : virologists)
			{
				if(v.getVirologist() != getCurrentVirologist() && getCurrentVirologist().getField() == v.getVirologist().getField())
				{
					v.setHighlight(true);
					v.setListener(true);
				} else
				{
					v.setHighlight(false);
					v.setListener(false);	
				}
			}
		} else if(state == ViewState.virologistSelectToLoot)
		{
			for(JVirologist v : virologists)
			{
				if(v.getVirologist().Stunned())
				{
					if(v.getVirologist() != getCurrentVirologist() && getCurrentVirologist().getField() == v.getVirologist().getField())
					{
						v.setHighlight(true);
						v.setListener(true);
					} else
					{
						v.setHighlight(false);
						v.setListener(false);	
					}
				}
			}
		} else {
			for(JVirologist v : virologists)
			{
				v.setListener(false);
				v.setHighlight(false);
			}
		}
		
		inventoryPanel.updateContent(getCurrentVirologist());
	}
	public void onFieldClick(Field f) {
		if(state == ViewState.fieldSelect)
		{
			iface.GUIcommand("move", f.Id);
			// replace visually
			JVirologist v = virologists.get(iface.game.GetActualVirologist().Id);
			for(JField field : fields)
			{
				field.remove(v);
			}
			fields.get(v.getVirologist().getField().Id).add(v);
			onStateUpdate(ViewState.none);
			inventoryPanel.updateContent(v.getVirologist());
		}
		repaint();
	}
	public void onVirologistClick(Virologist v) {
		if(state == ViewState.virologistSelectToVirus)
		{
			if(v != getCurrentVirologist())
			{
				iface.GUIcommand("usevirus", selectedAgent.Id, v.Id);
				onStateUpdate(ViewState.none);
			}
		}
		if(state == ViewState.virologistSelectToKill)
		{
			if(v != getCurrentVirologist())
			{
				iface.GUIcommand("kill", selectedAxe.Id, v.Id);
				onStateUpdate(ViewState.none);
			}
		}if(state == ViewState.virologistSelectToLoot)
		{
			if(v != getCurrentVirologist() && v.Stunned())
			{
				selectedVirologist = v;
				JButton [] buttons = new JButton[2 + v.getEquipmentInventory().size() + v.getEquippedEquipments().size()];
				buttons[0] = bStealAmino;
				buttons[1] = bStealNucleo;
				int i, j;
				for(i = 2, j = 0; j < v.getEquipmentInventory().size(); i++, j++)
				{
					Equipment eq = v.getEquipmentInventory().get(j);
					buttons[i] = new JEqiupmentButton("Steal " + eq.getType(), eq.Id);
					buttons[i].addMouseListener(this);
				}
				for(j = 0; j < v.getEquippedEquipments().size(); i++, j++)
				{
					Equipment eq = v.getEquippedEquipments().get(j);
					buttons[i] = new JEqiupmentButton("Steal " + eq.getType(), eq.Id);
					buttons[i].addMouseListener(this);
				}
				onStateUpdate(ViewState.none);
				inventoryPanel.setOptionButtons(buttons);
			}
		}
		inventoryPanel.updateContent(getCurrentVirologist());
		repaint();
		revalidate();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(selectedVirologist != null)
		{
			if(e.getSource() == bStealAmino)
			{
				iface.GUIcommand("steal", "a", selectedVirologist.Id);
			} else if(e.getSource() == bStealNucleo)
			{
				iface.GUIcommand("steal", "n", selectedVirologist.Id);
			} else 
			{
				try
				{
					JEqiupmentButton b = (JEqiupmentButton) e.getSource();
					iface.GUIcommand("steal", "e", selectedVirologist.Id, b.Id);
					inventoryPanel.updateContent(null);
				} catch(ClassCastException ex)
				{
					return;
				}
			}
			
			selectedVirologist = null;	
			inventoryPanel.setOptionButtons(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
