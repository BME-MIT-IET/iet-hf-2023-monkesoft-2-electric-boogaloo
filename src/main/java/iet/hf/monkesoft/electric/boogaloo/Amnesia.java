package iet.hf.monkesoft.electric.boogaloo;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : iet.hf.monkesoft.electric.boogaloo.Amnesia.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 * Singleton osztály. Olyan nulla időtartamú effekt, melytől a virológus elfelejti a már megtanult genetikai
 *kódokat.
 *
 */

/**
 * Törli a paraméterként kapott virológus objektumban tárolt összes genetikai kódot. 
 */

public class Amnesia extends Effect {
	public Amnesia() {
		super("amnesia");
	}
	@Override
	public void Apply(Virologist v) {
		v.getCodeList().clear();
	}
}