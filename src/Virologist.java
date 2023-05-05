import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Virologist.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 * 
 * Ez az osztÃ¡ly reprezentÃ¡lja magÃ¡t a jÃ¡tÃ©kos Ã¡ltal irÃ¡nyÃ­tott karaktert. 
 * Ezt mozgatja a mezÅ‘kÃ¶n, hozzÃ¡ kerÃ¼lnek a felvett tÃ¡rgyak, 
 * elkÃ©szÃ­tett Ã¡gensek. 
 * Vele tud interaktÃ¡lni a mezÅ‘kkel, 
 * mÃ¡s jÃ¡tÃ©kosokkal
 *
 */

public class Virologist {
	private static final int DefaultMovementPoints = 3;
	//lÃ©pÃ©s pontok
	private int MovementPoints = DefaultMovementPoints;
	//A virolÃ³gusnÃ¡l lÃ©vÅ‘ vÃ©dÅ‘felszerelÃ©sek..
	private ArrayList<Equipment> EquipmentInventory = new ArrayList<>();
	//A virolÃ³gus ismeretÃ©ben lÃ©vÅ‘ genetikai kÃ³dok.
	private ArrayList<GeneticCode> CodeList = new ArrayList<>();
	//A virolÃ³gusnÃ¡l lÃ©vÅ‘ Ã¡gensek.
	private ArrayList<Agent> AgentInventory = new ArrayList<>();
	//A mezÅ‘, amin a virolÃ³gus tartÃ³zkodik
	Field Place;
	//A virolÃ³gusnÃ¡l lÃ©vÅ‘ anyagok.
	private ArrayList<Material> MaterialList  = new ArrayList<>();
	//A virolÃ³gusra hatÃ¡st kifejtÅ‘ effektek
	//private ArrayList<AppliedEffect> ae = new ArrayList<>();
	//private Timer Virologists;
	//A virolÃ³gusnÃ¡l lÃ©vÅ‘ vÃ©dÅ‘felszerelÃ©sek
	private ArrayList<Equipment> EquippedEquipments = new ArrayList<>();
	//A virolÃ³gusra hatÃ¡st kifejtÅ‘ effektek.
	private ArrayList<AppliedEffect> AppliedEffects = new ArrayList<>();
	
	static HashMap<Integer, Virologist> list = new HashMap<Integer, Virologist>();
	int Id = 0;
	private boolean won = false;
	public Virologist(int id)
	{
		while(list.containsKey(id)) 
			id++; 
		this.Id = id;
		list.put(Id, this);
		MaterialList.add(new AminoAcid());
		MaterialList.add(new Nucleotid());
	}
	
	/**
	 * A virolÃ³gus a paramÃ©terkÃ©nt kapott mezÅ‘re kÃ­sÃ©rel meg lÃ©pni. 
	 * Ha van bÃ¡rmilyen effekt, ami a lÃ©pÃ©seket befolyÃ¡solja, 
	 * akkor a jÃ¡tÃ©kos nem tudja irÃ¡nyÃ­tani, hogy hova lÃ©p. 
	 * Ha nincs ilyen, akkor Ã©rtesÃ­ti a mezÅ‘t, 
	 * hogy rÃ¡ szeretne lÃ©pni.
	 * 
	 */
	public void Move(Field f) {
		if(Stunned()) {
			return;
		}
		//if there are no more movementpoints left, do nothing
		if(this.getMovementPoints() <= 0)
			return;
		
		for (AppliedEffect ae : AppliedEffects){
			//If there is an effect that modifies the move, move randomly
			//Implemented in ModMove
			if (ae.GetEffect().ModMove(this)) {
				return;
			}
		}
		
		// Ha a fogadÃ³ mezÅ‘ elfogdaja normÃ¡lisan lÃ©p
		if(f.Accept(this)) {
			if(Place != null)
				Place.Remove(this);
			this.setMovementPoints(this.getMovementPoints() - 1);
			SetField(f);
		}
	}
/**
 * A virolÃ³gus rÃ¡keni a paramÃ©terÃ¼l kapott Ã¡genst v-re. 
 * Ha van olyan effekt v-n, ami bÃ¡rmilyen mÃ³don befolyÃ¡solja a kenÃ©st, 
 * az a forgatÃ³kÃ¶nyv lÃ©p Ã©letbe 
 * (pl.: KesztyÅ± effektje visszakeni az Ã¡genst)
 */
	public void UseAgent(Agent a, Virologist v) {
		if(Stunned()) {
			return;
		}
		v.ReceivedAgent(a, this);
		this.RemoveAgent(a);
	}
	
	/**
	 *  A virolÃ³gus megkÃ­sÃ©rli v felvÃ©telÃ©t. 
	 *  Ha hÃ¡romnÃ¡l kevesebb vÃ©dÅ‘felszerelÃ©s van rajta, akkor felveszi, 
	 *  egyÃ©bkÃ©nt nem tÃ¶rtÃ©nik semmi.
	 */

	public void Equip(Equipment eq) {
		if(Stunned()) {
			return;
		}
		if(EquippedEquipments.size() < 3) {
			EquippedEquipments.add(eq);
			EquipmentInventory.remove(eq);
			eq.Equipped(this);
		}
	}
	
	/**
	 * A virolÃ³gus leveszi v-t, aminek Ã­gy az Ã¶sszes effektje eltÅ±nik a virolÃ³gusrÃ³l.
	 */

	public void UnEquip(Equipment eq) {
		if(this.EquippedEquipments.contains(eq)) {
			EquippedEquipments.remove(eq);
			EquipmentInventory.add(eq);
			eq.UnEquipped(this);
		}
	}

	/**
	 * A virolÃ³gus a paramÃ©terkÃ©nt kapott genetikai kÃ³d alapjÃ¡n Ã¡genst akar elÅ‘Ã¡llÃ­tani. 
	 * Ha van elÃ©g anyag az elkÃ©szÃ­tÃ©sÃ©hez, 
	 * akkor az Ã¡gens anyagkÃ¶ltsÃ©ge levonÃ³dik a virolÃ³gus anyagai kÃ¶zÃ¼l Ã©s megkapja a kÃ©szÃ­tett Ã¡genst. 
	 * EgyÃ©bkÃ©nt nem tÃ¶rtÃ©nik semmi.
	 */
	public void MakeVirus(GeneticCode g, int id) {
		if(Stunned()) {
			return;
		}
		g.UsedForVirus(this, id);
	}
	public void MakeVaccine(GeneticCode g, int id) {
		if(Stunned()) {
			return;
		}
		g.UsedForVaccine(this, id);
	}
	/**
	 * A virolÃ³gus a paramÃ©terkÃ©nt kapott mezÅ‘vel interaktÃ¡l. 
	 * MezÅ‘tÃ­pusonkÃ©nt mÃ¡s tÃ¶rtÃ©nik 
	 * (pl.: ha a mezÅ‘ egy raktÃ¡r, akkor azt kifosztja. 
	 * Ezeket a mezÅ‘tÃ­pusok sajÃ¡t Interacted metÃ³dusa rÃ©szletezi.)
	 * 
	 * TODO: If all codes are acquired, win the game
	 */
	public void Interact(Field f) {
		if(Stunned()) {
			return;
		}
		f.Interacted(this);
	}

	/**
	 * A jÃ¡tÃ©kos vÃ©get vet a kÃ¶rÃ©nek. 
	 * Ez utÃ¡n a kÃ¶vetkezÅ‘ kÃ¶rÃ©ig nem tud semmit csinÃ¡lni.
	 */
	public void EndTurn() {
		Decr();
	}
/**
 * CsÃ¶kkenti a LÃ©pÃ©sszÃ¡m attribÃºtumot eggyel.
 */
	public void Decr() {
		ArrayList<AppliedEffect> aeRemover  = new ArrayList<>();
		for (AppliedEffect ae : AppliedEffects) {
			ae.DecrTime(this);
			if (ae.GetTime() == 0)
				aeRemover.add(ae);
		}
		for (AppliedEffect aer : aeRemover)
			RemoveEffect(aer.GetEffect());
		
		ArrayList<Agent> agentRemover  = new ArrayList<>();
		for (Agent a : AgentInventory) {
			a.DecrTime(this);
			if (a.getTime() == 0)
				agentRemover.add(a);
		}
		for (Agent ar : agentRemover)
			RemoveAgent(ar);
	}
	/**
	 * A virolÃ³gus elveszi vf-et a paramÃ©terkÃ©nt kapott, 
	 * lebÃ©nult virolÃ³gustÃ³l 
	 * (vf egy, v-nek az inventoryjÃ¡ban talÃ¡lhatÃ³ vÃ©dÅ‘felszerelÃ©s).
	 */
	public void StealEq(Virologist v, Equipment eq) {
		if(Stunned()) {
			return;
		}
		v.RemoveEquipment(eq);
		this.AddEquipment(eq);
	}

	/**
	 * Nukleotid lopÃ¡s
	 * Elvesz egy adott mennyisÃ©gÅ± Nukleotidot egy mÃ¡sik virolÃ³gustÃ³l
	 */
	public void StealNucleotid(Virologist v, int i) {
		if(Stunned()) {
			return;
		}
		int victimNucleotid = 0, virologistNucleotid = 0;
		
		for(Material vic : v.getMaterialList())
		{
			int n = vic.getNucleotid();
			if(n > 0)
				victimNucleotid = n;
		}
		
		for(Material m : getMaterialList())
		{
			int n = m.getMaxNucleotid() - m.getNucleotid();
			if(n > 0)
				virologistNucleotid = n;
		}
		int nucleo = Math.min(victimNucleotid, virologistNucleotid);
		
		for(Material m : v.getMaterialList())
		{
			m.SubtractNucleotid(nucleo);
		}
		for(Material m : MaterialList)
		{
			m.AddNucleotid(nucleo);
		}
		/*
		int v1Space = Tester.AskForInteger("Mennyi nukleotidot vehet mï¿½g fel v1? ");
		int v2Nucleotid = Tester.AskForInteger("Mennyi nukleotid van v2-nï¿½l? ");
		
		//Calculate how much space V1 has
		for(Material m: this.MaterialList) {
			int tmp = 0;
			if((tmp = m.getNucleotid()) > 0) {
				v1Space = m.getMax() - tmp;
				break;
			}
		}
		//If there was no nucleotid found, set maximum value
		if(v1Space == 0) {
			//Assuming maximum value of NucleotidMax and AminoAcidMax is equal
			v1Space = this.MaterialList.get(0).getMax();
		}
		
		//Calculate how much nucleotid V2 has
		for(Material m: v2.MaterialList) {
			int tmp = 0;
			if((tmp = m.getNucleotid()) > 0) {
				v2Nucleotid = tmp;
				break;
			}
		}
		
		if(v1Space < i || v2Nucleotid < i)
		{	
			if(v1Space < v2Nucleotid)
			{
				for(Material v1material : v2.getMaterialList())
					v1material.SubtractNucleotid(v1Space);
				for(Material v2material : MaterialList)
					v2material.AddNucleotid(v1Space);
			} else {
				for(Material v1material : v2.getMaterialList())
					v1material.SubtractNucleotid(v2Nucleotid);
				for(Material v2material : MaterialList)
					v2material.AddNucleotid(v2Nucleotid);
			}
		} else 
		{
			for(Material v1material : v2.getMaterialList())
				v1material.SubtractNucleotid(i);
			for(Material v2material : MaterialList)
				v2material.AddNucleotid(i);
		}*/
		/*
		v.getMaterialList().forEach((mat) -> {
			if (
					mat.SubtractNucleotid(i)
			)
				this.getMaterialList().forEach((a) -> 
					a.AddNucleotid(i)
				);
		});*/
	}
	/**
	 * AminÃ³sav lopÃ¡s
	 * Elvesz egy adott mennyisÃ©gÅ± AminÃ³sav egy mÃ¡sik virolÃ³gustÃ³l
	 */
	public void StealAminoAcid(Virologist v, int i) {
		if(Stunned()) {
			return;
		}
		int victimAminoAcid = 0, virologistAminoAcid = 0;
		
		for(Material vic : v.getMaterialList())
		{
			int a = vic.getAminoAcid();
			if(a > 0)
				victimAminoAcid = a;
			
		}
		
		for(Material m : getMaterialList())
		{
			int a = m.getMaxAminoAcid() - m.getAminoAcid();
			if(a > 0)
				virologistAminoAcid = a;
		}
		int amino = Math.min(victimAminoAcid, virologistAminoAcid);
		
		for(Material m : v.getMaterialList())
		{
			m.SubtractAminoAcid(amino);
		}
		for(Material m : MaterialList)
		{
			m.AddAminoAcid(amino);
		}
		
		/*
		int v1Space = Tester.AskForInteger("Mennyi aminosavat vehet mï¿½g fel v1? ");
		int v2AminoAcid = Tester.AskForInteger("Mennyi aminosav van v2-nï¿½l? ");
		
		//Calculate how much space V1 has
		for(Material m: this.MaterialList) {
			int tmp = 0;
			if((tmp = m.getAminoAcid()) > 0) {
				v1Space = m.getMax() - tmp;
				break;
			}
		}
		//If there was no AminoAcid found, set maximum value
		if(v1Space == 0) {
			//Assuming maximum value of NucleotidMax and AminoAcidMax is equal
			v1Space = this.MaterialList.get(0).getMax();
		}
				
		//Calculate how much nucleotid V2 has
		for(Material m: v2.MaterialList) {
			int tmp = 0;
			if((tmp = m.getAminoAcid()) > 0) {
				v2AminoAcid = tmp;
				break;
			}
		}
		
		if(v1Space < i || v2AminoAcid < i)
		{	
			if(v1Space < v2AminoAcid)
			{
				for(Material v1material : v2.getMaterialList())
					v1material.SubtractAminoAcid(v1Space);
				for(Material v2material : MaterialList)
					v2material.AddAminoAcid(v1Space);
			} else {
				for(Material v1material : v2.getMaterialList())
					v1material.SubtractAminoAcid(v2AminoAcid);
				for(Material v2material : MaterialList)
					v2material.AddAminoAcid(v2AminoAcid);
			}
		} else 
		{
			for(Material v1material : v2.getMaterialList())
				v1material.SubtractAminoAcid(i);
			for(Material v2material : MaterialList)
				v2material.AddAminoAcid(i);
		}
		*/
		/*
		v.getMaterialList().forEach((mat) -> {
			if (
					mat.SubtractAminoAcid(i)
				)
				this.getMaterialList().forEach((a) -> 
					a.AddAminoAcid(i)
				);
		});*/
	}
	
	/**
	 * Visszaadja, hogy van-e a virolÃ³guson a paramÃ©terkÃ©nt kapott effekt. Ha van, akkor a visszatÃ©rÃ©si Ã©rtÃ©k true, egyÃ©bkÃ©nt false.
	 */

	public boolean HasEffect(Effect e) {
		
		for (int i = 0; i < AppliedEffects.size(); i++)
			if (AppliedEffects.get(i).GetEffect().equals(e)) {
				return true;
			}
				
		return false;
	}

	/**
	 * A virolÃ³gus meg lett kenve egy mÃ¡sik virolÃ³gus Ã¡ltal a paramÃ©terÃ¼l kapott Ã¡genssel. 
	 * Ha a virolÃ³guson nincsen az Ã¡gens effektje elleni vÃ©delem, 
	 * akkor az effekt kifejti a hatÃ¡sÃ¡t rÃ¡. 
	 * Ha van, akkor az Ã¡gens hatÃ¡stalan.
	 */
	public void ReceivedAgent(Agent a, Virologist v2) {
		a.Activate(this, v2);
	}
	/*
	 * use an equipment on an other virologist at the same place
	 */
	public void UseEquipment(Equipment eq, Virologist v) {
		if(Stunned()) {
			return;
		}
		if(v.getField() == getField())
			eq.Use(v);
	}
	
	/**
	 * Kills the virologist
	 * and makes the axe unusable
	 * @param axe
	 * 
	 * TODO: properly kill the virologist, by also removing it from Timer
	 */
	public void Kill(Axe axe) {
		if(Stunned()) {
			return;
		}
		//Remove from field
		this.getField().Remove(this);
		//Make the axe blunt
		axe.Blunt();
		
	}
	
	/**
	 *  MostantÃ³l a paramÃ©terÃ¼l kapott AppliedEffekt hatÃ¡ssal van a virolÃ³gusra, 
	 *  Ã­gy hozzÃ¡adÃ³dik az effekteket tÃ¡rolÃ³ vÃ¡ltozÃ³hoz.
	 */
	public void AddEffect(AppliedEffect ae) {
		//Check whether the effect is a decaying effect.
		//If it is not, activate a new AppliedEffect
		if(ae.GetTime() < 0) {
			AppliedEffects.add(ae);
		} else { //If there is already an AppliedEffect on the virologist
				 //with the same effect as "ae", increase it's time, else
				 //create a new one
		if(!this.IncrEffect(5, ae.GetEffect()))
			AppliedEffects.add(ae);
		}
	}

	/**
	 * MostantÃ³l a paramÃ©terÃ¼l kapott effektet tÃ¡rolÃ³ AppliedEffekt nincs hatÃ¡ssal a virolÃ³gusra, 
	 * Ã­gy tÃ¶rlÅ‘dik az effekteket tÃ¡rolÃ³ vÃ¡ltozÃ³bÃ³l. 
	 * Mivel ugyan olyan effektet hordozÃ³ Ã¡gens Ã©s vÃ©dÅ‘felszerelÃ©s nincsen Ã©s a kÃ¼lÃ¶nbÃ¶zÅ‘ lejÃ¡rÃ³ idÅ‘tartamÃº effektekbÅ‘l egyszerre csak egy lehet, 
	 * ezÃ©rt elÃ©g az elsÅ‘ ilyen effektet tÃ¡rolÃ³ AppliedEffektet megszÅ±ntetni. 
	 * (LÃ¡sd: RemoveEffekt szekvencia)
	 */
	
	public void RemoveEffect(Effect e) {
		for (int i = 0; i < AppliedEffects.size(); i++) {
			if (AppliedEffects.get(i).GetEffect().equals(e)) {
				AppliedEffects.remove(i);
			}
		}
	}

	/**
	 * Megkeresi azt az AppliedEffektet, ami mÃ¡r hat a virolÃ³gusra Ã©s megnÃ¶veli a LejÃ¡rati_idÅ‘ attribÃºtumÃ¡t i-vel.
	 */
	public boolean IncrEffect(int i, Effect e) {
		for (AppliedEffect ae : AppliedEffects) {
			if (ae.GetEffect().equals(e))
			{
				ae.IncrTime(i);
				return true;
			}
		}
		return false;
	}

	/**
	 *  EltÃ¡volÃ­tja a paramÃ©terÃ¼l kapott Ã¡genst az Ã�gens_inventory-bÃ³l.
	 */
	public void RemoveAgent(Agent a) {
		for (int j = 0; j < AgentInventory.size(); j++)
			if (AgentInventory.get(j).equals(a)) {
				AgentInventory.remove(j);
			}
	}

	/**
	 *  a virolÃ³gus felvette egy mezÅ‘rÅ‘l, vagy eltulajdonÃ­totta f-et, Ã­gy azt hozzÃ¡adja a FelszerelÃ©s_inventory-hoz.
	 */
	public void AddEquipment(Equipment eq) {
		EquipmentInventory.add(eq);
	}
	
	/**
	 *  virolÃ³gustÃ³l eltulajdonÃ­tottÃ¡k f-et, Ã­gy az elkerÃ¼l a FelszerelÃ©s_inventory-bÃ³l. A viselt felszerelÃ©sek eltulajdonÃ­tÃ¡sakor az effektjÃ¼k nem hat tovÃ¡bb.
	 */
	public void RemoveEquipment(Equipment eq) {
		UnEquip(eq);
		EquipmentInventory.remove(eq);
	}
	
	public void DisCardEquipment(Equipment eq) {
		if(Stunned()) {
			return;
		}
		if (eq.GetEffect() == null) {
			UnEquip(eq);
			EquipmentInventory.remove(eq);
		} else
		eq.Discard(this);
	}
	

	/**
	 * hozzÃ¡adja a paramÃ©terÃ¼l kapott Ã¡genst az Ã�gens_inventory-hoz.
	 */
	public void AddAgent(Agent a) {
		AgentInventory.add(a);
	}

	/**
	 * A virolÃ³gus megtanulta a paramÃ©terkÃ©nt kapott genetikai kÃ³dot, 
	 * Ã­gy hozzÃ¡adja k-t a kÃ³dokhoz.
	 */
	public void AddGenCode(GeneticCode gc) {
		if(!CodeList.contains(gc)) {
			CodeList.add(gc);
		}	
		if (Game.CompareG(CodeList)) {
			//System.out.println("GG EZ");
			won = true;
		}
			
	}

	/**
	 * A virolÃ³gus valami miatt (pl.: AmnÃ©zia effektet kapott) elfelejti k-t, Ã­gy azt eltÃ¡volÃ­tja a kÃ³dok kÃ¶zÃ¼l.
	 */
	public void RemoveGenCode(GeneticCode gc) {
		for (int j = 0; j < CodeList.size(); j++)
			if (CodeList.get(j).equals(gc)) {
				CodeList.remove(j);
			}
	}

	/**
	 * A virolÃ³gus tudomÃ¡st szerez a szomszÃ©dos mezÅ‘krÅ‘l Ã©s a sajÃ¡t mezÅ‘jÃ©n Ã¡llÃ³ virolÃ³gusokrÃ³l. 
	 * A visszatÃ©rÃ©si Ã©rtÃ©ke true, ha van rajta kÃ¶rmÃ³dosÃ­tÃ³ effekt, egyÃ©bkÃ©nt false.
	 */
	public boolean Stunned() {
		for(AppliedEffect ae: this.AppliedEffects) {
			if(ae.GetEffect().ModRound(this) == true) {
				return true;
			}
		}
		//If no roundmodifiers were found
		return false;
	}

	public Field getField() {
		return Place;
	}

	//Getterek Ã©s setterek
	
	public ArrayList<Material> getMaterialList() {
		return MaterialList;
	}

	public void setMaterialList(ArrayList<Material> materialList) {
		MaterialList = materialList;
	}

	public ArrayList<AppliedEffect> getAe() {
		return AppliedEffects;
	}

	public void setAe(ArrayList<AppliedEffect> ae) {
		this.AppliedEffects = AppliedEffects;
	}

	public ArrayList<Agent> getAgentInventory() {
		return AgentInventory;
	}

	public ArrayList<GeneticCode> getCodeList() {
		return CodeList;
	}
	
	public void SetField(Field f)
	{
		Place = f;
	}

	public ArrayList<Equipment> getEquippedEquipments() {
		return EquippedEquipments;
	}
	public ArrayList<Equipment> getEquipmentInventory() {
		return EquipmentInventory;
	}
	public void YourTurn()
	{
		setMovementPoints(DefaultMovementPoints);
	}
	@Override
	public String toString() {
		boolean stunned = false;
		for(AppliedEffect ae : AppliedEffects)
			if(ae.GetEffect().ModRound(this))
				stunned = true;
		if(stunned)
		{
			int amino = 0, nucleo = 0;
			for(Material m : getMaterialList())
			{
				amino += m.getAminoAcid();
				nucleo += m.getNucleotid();
			}
			String res =  "\t\t(" + Id + ") Stunned Virologist\n"
					+ "\t\t\tAmino acid: " + amino + "\n"
					+ "\t\t\tNucleotid: " + nucleo + "";
			res += "\n\t\t\tEquipped:";
			for(Equipment eq : EquippedEquipments)
				res += "\n\t\t\t\t(" + eq.Id + ") " + eq.getType(); 
			res += "\n\t\t\tInventory:";
			for(Equipment eq : EquipmentInventory)
				res += "\n\t\t\t\t(" + eq.Id + ") " + eq.getType(); 
			return res;
		}
		else
			return "\t\t(" + Id + ") Virologist";
	}

	public boolean haveWon() {
		return won;
	}

	public int getMovementPoints() {
		return MovementPoints;
	}

	public void setMovementPoints(int movementPoints) {
		MovementPoints = movementPoints;
	}
	
}
