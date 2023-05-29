package iet.hf.monkesoft.electric.boogaloo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : iet.hf.monkesoft.electric.boogaloo.InventoryIncrease.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//

/**
 * Az anyaggyűjtő képesség növelésére szolgáló effekt singleton osztálya.
 */
public class InventoryIncrease extends Effect {
	
	InventoryIncrease() {
		super("inventoryincrease");
	}

	/**
	 * Megduplázza a paraméterként kapott virológus anyaggyűjtő képességét,
	 * és létrehoz egy AppliedEffektet, ami hozzáadódik a virológushoz.
	 */
	@Override
	public void Apply(Virologist v) {
		v.getMaterialList().forEach( (m) -> m.IncreaseMax(m.getMax()));
		new AppliedEffect(v, this, -1);
	}
	
/**
 * Felezi a paraméterként kapott virológus anyaggyűjtő képességét és eltávolít egy AppliedEffektet, 
 * ami tárolja őt a virológus AppliedEffekt tárolójából.
 */
	@Override
	public void Die(Virologist v) {
		v.RemoveEffect(this);
		for (int i = 0; i < v.getMaterialList().size(); i++) {
			v.getMaterialList().forEach( (m) -> m.DecreaseMax(m.getMax()));
		}
	}
}