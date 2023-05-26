//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Material.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//

/**
 * Ă�gensek kĂ©szĂ­tĂ©sĂ©hez szĂĽksĂ©ges anyagok absztrakt Ĺ‘sosztĂˇlya. Az anyag tĂˇrolĂłjĂˇt reprezentĂˇlja. 
 * Csak adott mennyisĂ©get lehet egy virolĂłgusnak belĹ‘le tĂˇrolni egyszerre.
 * Ă�gensek kĂ©szĂ­tĂ©sekor az adott Ăˇgenshez szĂĽksĂ©ges anyagmennyisĂ©g elhasznĂˇlĂłdik.
 */



public abstract class Material {
	public static final int MAX = 25;
	private int Amount;
	private int MaxAmount;
	
	protected Material(int amount, int max) {
		this.Amount = amount;
		this.MaxAmount = max;
	}
	/**
	 * MegnĂ¶veli a MaxAmount attribĂştum Ă©rtĂ©kĂ©t i-vel.
	 *Az objektum Ăˇltal reprezentĂˇlhatĂł maximĂˇlis anyagmennyisĂ©g valamilyen okbĂłl nĹ‘tt (tipikusan akkor, ha egy VirolĂłgus objektumra felkerĂĽlt valamilyen effekt, ami a maximĂˇlis anyaggyĹ±jtĹ‘ kĂ©pessĂ©get nĂ¶veli).
	 */
	public void IncreaseMax(int i) {
		MaxAmount=(MaxAmount+i);
	}
	
	/**
	 * CsĂ¶kkenti a MaxAmount attribĂştum Ă©rtĂ©kĂ©t i-vel.
	 *Az objektum Ăˇltal reprezentĂˇlhatĂł maximĂˇlis anyagmennyisĂ©g valamilyen okbĂłl csĂ¶kkent (tipikusan akkor, ha egy VirolĂłgus objektumrĂłl lekerĂĽlt valamilyen effekt, ami a maximĂˇlis anyaggyĹ±jtĹ‘ kĂ©pessĂ©get nĂ¶veli).
	 */
	public void DecreaseMax(int i) {
		MaxAmount=(MaxAmount-i);
	}

	/**
	 * A funkciĂłja a megfelelĹ‘ leszĂˇrmazottak MennyisĂ©g attribĂştumĂˇnak megnĂ¶velĂ©se. Az Aminosav osztĂˇly override-olja. 
	 * Az Anyag osztĂˇly leszĂˇrmazottait tĂˇrolĂł heterogĂ©n kollekciĂłkon valĂł iterĂˇlĂˇst kĂ¶nnyĂ­ti meg. 
	 * Alapesetben nem csinĂˇl semmit.
	 */
	public void AddAminoAcid(int i) {
	}
	
	/**
	 * A funkciĂłja a megfelelĹ‘ leszĂˇrmazottak MennyisĂ©g attribĂştumĂˇnak megnĂ¶velĂ©se.  
	 * A Nukleotid osztĂˇly override-olja.
	 * Az Anyag osztĂˇly leszĂˇrmazottait tĂˇrolĂł heterogĂ©n kollekciĂłkon valĂł iterĂˇlĂˇst kĂ¶nnyĂ­ti meg.
	 */
	public void AddNucleotid(int i) {
	}
	
	/**
	 *Akkor hĂ­vĂłdik meg, ha vakcinĂˇt szeretnĂ©nk gyĂˇrtani.
	 *Alapesetben visszatĂ©r true-val. 
	 *A vĂ­rus kĂ©szĂ­tĂ©sĂ©hez szĂĽksĂ©ges leszĂˇrmazottak override-oljĂˇk. 
	 *
	 * @param i
	 * @return
	 */

	public boolean SubtractAminoAcid(int i) {
		return true;

	}
	
	/**
	 *Akkor hĂ­vĂłdik meg, ha vĂ­rust szeretnĂ©nk gyĂˇrtani.
     *Alapesetben visszatĂ©r true-val. 
	 *A vĂ­rus kĂ©szĂ­tĂ©sĂ©hez szĂĽksĂ©ges leszĂˇrmazottak override-oljĂˇk.
	 * 
	 * @param i
	 * @return
	 */

	public boolean SubtractNucleotid(int i) {
		return true;

	}
	
	public int getAmount() {return Amount;}
	
	public int getAminoAcid() {return 0;}
	public int getNucleotid() {return 0;}
	
	public int getMaxAminoAcid() {return 0;}
	public int getMaxNucleotid() {return 0;}
	
	public void setAmount(int i) {
		if(i>MaxAmount) {
			Amount=MaxAmount;
		}
		else {
			Amount=i;
		}
	}
	
	public void setMax(int x) {
		this.MaxAmount = x;
	}
	
	public int getMax() {
		return MaxAmount;
	}
	
	
}
