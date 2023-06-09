package iet.hf.monkesoft.electric.boogaloo;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : AppliedEffect.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 * Egy burkolĂł osztĂˇly, ami egy singleton effektrĹ‘l referenciĂˇt tĂˇrol, Ă­gy lehetĹ‘vĂ© teszi azok egyenlĹ‘sĂ©g szerinti egyezĂ©s szerinti vizsgĂˇlatĂˇt.
 *Ez az az osztĂˇly, aminek pĂ©ldĂˇnyait tĂˇrolja a virolĂłgus.
 */

public class AppliedEffect {
	private int Time;
	private Effect Eff;
	
	/**
	 * NOT YET IMPLEMENTED!!!
	 */
	private int numberOfUses;
	/**
	 * TODO: Implemetation of numberOfUses
	 */

/**
  * Konstruktor, ami eltĂˇrolja a kapott effektet, Ă©s a kapott virolĂłgushoz adja magĂˇt
  *
  * Ă–tlet: Ha nem akarjuk mindig megadni az effekt idĹ‘tartamĂˇt, harmadik paramĂ©ter
  * lehetne egy boolean.
  * Ha true, akkor pozitĂ­v x idĹ‘re ĂˇllĂ­tja a Time attribĂştumot,
  * ha false, akkor pedig -1-re. X ekkor elĹ‘re meghatĂˇrozott lenne
  * (MegjegyzĂ©s: megcsinĂˇltam, ha nem tetszik tĂ¶rĂ¶ljĂ©tek)
  */
	
	AppliedEffect(Virologist v, Effect e){
		this(v, e, e.getTime());   //XDDD ez jobb lenne konstruktorban
	}
	
	public AppliedEffect(Virologist v, Effect e, int time) 
	{
		Eff = e;
		this.Time = time;
		v.AddEffect(this);
		numberOfUses = 3;
	}

	/**
	 * nĂ¶veli a Time Ă©rtĂ©kĂ©t i-vel
	 */

	public void IncrTime(int i) {
		this.Time += i;
	}

	/**
	 * CsĂ¶kkenti a LejĂˇrati_idĹ‘ Ă©rtĂ©kĂ©t eggyel.
	 */
	
	public void DecrTime(Virologist v) {
		Time--;
	}
	
	public void DecrUses(Virologist v, Effect e) {
		numberOfUses--;
		if (numberOfUses == 0) {
			for (Equipment eq : v.getEquippedEquipments()) {
				if (eq.GetEffect() == e) {
					eq.Discard(v);
					break;
				}
			}
		}
	}
	
	public Effect GetEffect() {
		return Eff;
	}
	
	public int GetTime() {
		return this.Time;
	}
	@Override
	public String toString()
	{
		return Eff.toString() + (Time > -1 ? " - " + Time + " turn left" : "");
	}
}
