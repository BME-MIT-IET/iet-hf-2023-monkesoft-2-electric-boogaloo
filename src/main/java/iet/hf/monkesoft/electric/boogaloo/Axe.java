package iet.hf.monkesoft.electric.boogaloo;

public class Axe extends Equipment{
	
	private boolean isSharp;
	
	/**
	 * iet.hf.monkesoft.electric.boogaloo.Effect of axe is NULL
	 */
	public Axe(int id) {
		super(null, id);
		isSharp = true;
	}
	
	/**
	 * Kills v, then v Blunts the axe
	 */
	@Override
	public void Use(Virologist v) {
		if(isSharp) {
			v.Kill(this);
		}
	}
	
	/**
	 * makes isSharp false, thus making the iet.hf.monkesoft.electric.boogaloo.Axe unusable
	 */
	public void Blunt() {
		this.isSharp = false;
	}

	@Override
	public String getType() {
		return "axe";
	}
}
