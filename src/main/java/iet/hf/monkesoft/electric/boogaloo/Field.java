package iet.hf.monkesoft.electric.boogaloo;

import java.util.ArrayList;
import java.util.HashMap;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Field.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//

/**
 * Ez az oszt�ly val�s�tja meg az alap mezot. Ososzt�lyul szolg�l a t�bbi mezot�pusnak.
 */


public class Field {
	private ArrayList<Virologist> Virologists = new ArrayList<>();
	private ArrayList<Field> Neighbours = new ArrayList<>();
	
	static HashMap<Integer, Field> list = new HashMap<>();
	int Id = 0;
	Field(int id)
	{
		while(list.containsKey(id)) 
			id++; 
		this.Id = id;
		list.put(Id, this);
	}
	/**
	 * Ez a met�dus hiv�dik meg, ha a mezovel interakt�lnak (tipikusan virol�gusok). 
	 * Ha az alap mezovel interakt�lnak, akkor nem t�rt�nik semmi.
 	 * A lesz�rmazottak a saj�t viselked�s�knek megfeleloen override-olj�k.
	 */
	
	public void Interacted(Virologist v) {
	}
	
	/**
	 * Modified interact, that is called if v has BearConfusion
	 * @param v
	 */
	public void ModInteract(Virologist v) {
	}

	/**
	 * Szomsz�d hoz�ad�sa
	 */
	public void AddNeighbour(Field f) {
		Neighbours.add(f);
	}
	
	/**
	  *A param�terk�nt kapott virol�gus az egyik szomsz�dos mezorol az adott mezore szeretne l�pni. Ha a mezo be tudja fogadni
      *a virol�gust, akkor megteszi �s visszat�r true-val, egy�bk�nt false-al.
      *
	  */
	public boolean Accept(Virologist v) {
		boolean found = false;
		for(Field f : Neighbours)
		{
			if(f.GetVirologists().contains(v))
			{
				Add(v);
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void Add(Virologist v) {
		Virologists.add(v);
	}
	/**
	 * A param�terk�nt kapott virol�gus ell�p a mezorol, �gy a mezo elt�vol�tja mag�r�l.
	 */
	public void Remove(Virologist v) {
		Virologists.remove(v);
	}

	/**
	 * getter a szomsz�dos mezokh�z
	 */
	public ArrayList<Field> GetNeighbours(){
		return this.Neighbours;
	}

	/**
	 * getter a mezon tartozkod� virol�gusokhoz.
	 */
	public ArrayList<Virologist> GetVirologists() {
		return this.Virologists;
	}
	
	@Override
	public String toString() {
		return "\t(" + Id + ") Field";
	}
}
