//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Nucleotid.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 * A vírusok készítéséhez szükséges anyagot megvalósító osztály.
 *
 */

public class Nucleotid extends Material {
	
	public Nucleotid(int amount, int max) {
		super(amount, max);
	}
	public Nucleotid(int amount) {
		super(amount,amount);
	}
	public Nucleotid() {
		super(0, MAX);
	}
	/**
	 * Az Anyag osztályból származtatott metódus felüldefiniált verziója.
     *Ha van legalább i Mennyiség, csökkenti a Mennyiség attribútum értékét i-vel és visszatér true-val.
     *Ha nincs elég, nem történik semmi és visszatér false-al.
     *Ellenkező esetben visszatér true-val.
     *A visszatérési érték megmondja, hogy az anyag mennyisége megengedi-e az ágens létrehozását.
	 */
	@Override
	public boolean SubtractNucleotid(int i) {
		if(i > this.getAmount()) {
			return false;
		}
		else {
			setAmount(getAmount()-i);
			return true;
		}
	}
/**
 * Az objektum Mennyiség attribútumához hozzáadja a paraméterként kapott számot.
 */
	@Override
	public void AddNucleotid(int i) {
		setAmount(getAmount()+i);

	}

	@Override
	public int getNucleotid() {return getAmount();}
	
	@Override
	public int getMaxNucleotid() {return getMax();}
	
}
