package iet.hf.monkesoft.electric.boogaloo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Vaccine.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 *Olyan Ăˇgens, amellyel egy virolĂłgus pozitĂ­v hatĂˇst fejthet ki magĂˇn. 
 */

public class Vaccine extends Agent {
	
	public Vaccine(Agent a, int id) {
		super(a, id);
	}
	
	public Vaccine(Effect Eff) {
		super(Eff);
	}

/**
 *A paramĂ©terkĂ©nt kapott virolĂłgus elhasznĂˇlja a vakcinĂˇt.
 *Ha van mĂˇr rajta a vakcina Ăˇltal nyĂşjtott effekt, akkor meghosszabbĂ­tja annak az idejĂ©t.
 *Ha nincsen, akkor rĂˇrakja az effektet.
  */
	@Override
	public void Activate(Virologist v, Virologist v2) {
        AppliedEffect effectToKill = null;
        AppliedEffect effectToExtend = null;
        
		for(AppliedEffect ae: v.getAe()) {
			///If ae is an Individual protection that
			if(ae.GetEffect().Protect(this, v, null) && ae.GetTime() > 0) {			//////ITT VAN EGY GIGANTIKUS PROBLÉMA
				effectToExtend = ae;
				//if there is a protection already, then there will be no viruses applied
				break;
			}
			//if there is a virus previously applied
			if(ae.GetEffect() == this.getEff()) {
				effectToKill = ae;
				//if virus is found, then you can be sure
				//that there will be no other viruseffect like this
				//or a vaccineeffect that protects against it
				break;
				
			}
		}
		//If there is an ae that needs to be extended
		if(effectToExtend != null) {
			v.AddEffect(effectToExtend);
		} //If there is a viruseffect that needs to be removed 
		else if(effectToKill != null) {
			//Kill the viruseffect
			v.RemoveEffect(effectToKill.GetEffect());
			Individual ind = new Individual(this.getEff());
			new AppliedEffect(v, ind, 5);
		} //If there needs to be a completely new effect 
		else {
			//Constructor adds the effect automatically
			//Time value positive, because vaccine effects decay
			Individual ind = new Individual(this.getEff());
			new AppliedEffect(v, ind, 5);
		}
	}
	
/**
  *A paramĂ©terkĂ©nt kapott virolĂłgus megkĂ­sĂ©rel elkĂ©szĂ­teni egy VakcinĂˇt.
  *Ha van nĂˇla elĂ©g anyag, akkor levonja a vakcina elkĂ©szĂ­tĂ©sĂ©hez szĂĽksĂ©ges mennyisĂ©get Ă©s hozzĂˇadja a vakcinĂˇt a virolĂłgus Ă�gens_inventoryjĂˇhoz.
  *Ha nincs nĂˇla elĂ©g anyag, akkor nem tĂ¶rtĂ©nik semmi
  */

	@Override
	public void Make(Virologist v) {
		boolean ok = true;
		for(Material m : v.getMaterialList())
			if (!m.SubtractAminoAcid(this.getCost()))
				ok = false;
		if(ok)
			v.AddAgent(this);
	}
	@Override
	public String toString() {
		return super.toString() + Eff.name + " protection - " + Time + " turn left";
	}
}
