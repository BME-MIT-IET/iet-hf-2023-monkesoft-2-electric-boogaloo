//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Virus.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//

/**
 *Olyan ágens, amellyel másik virológuson fejthetünk ki negatív hatást.
 */


public class Virus extends Agent {
	
	public Virus(Agent a, int id) {
		super(a, id);
	}
	
	public Virus(Effect Eff) {
		super(Eff);
	}

	/**
	 *  A vírus el lett használva a paraméterként kapott virológusra.
	 *  Eltünteti a v inventoryjából magát a vírus és megkísérli rátenni az effektjét.
	 */

	@Override
	public void Activate(Virologist v, Virologist v2) {
		//The virus dies, no matter what
		v.RemoveAgent(this);
		
		//Check if v has effects that protect against this virus
		//if there are any, don't do anything
		for(AppliedEffect ae: v.getAe()) {
			if(ae.GetEffect().Protect(this, v, v2)) {
				return;
			}
		}
		//If an already applied effect's time
		//Needs to be increased
		if(v.HasEffect(this.getEff())) {
				v.IncrEffect(getEff().getTime(), getEff());
		} //If a new AppliedEffect has to be created
		else {
			this.getEff().Apply(v);
		}
		
		return;
	}
	
	/**
	 * A paraméterként kapott virológus megkísérli elkészíteni a vírust. 
	 * Ha van nála elég a szükséges anyagokból, 
	 * akkor az Anyag_inventory-jából levonja a vírus elkészítéséhez szükséges anyagmennyiséget és elkészíti a vírust.
	 * Ha nincs, akkor nem történik semmi.
	*/

	@Override
	public void Make(Virologist v) {
		boolean ok = true;
		for(Material m : v.getMaterialList())
			if (m.SubtractNucleotid(this.getCost()) == false)
				ok = false;
		if(ok)
			v.AddAgent(this);
	}
	@Override
	public String toString() {
		return super.toString() + Eff.name + " - " + Time + " turn left";
	}
}
