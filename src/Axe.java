
public class Axe extends Equipment{
	
	private boolean isSharp;
	
	/**
	 * Effect of axe is NULL
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
	 * makes isSharp false, thus making the Axe unusable
	 */
	public void Blunt() {
		this.isSharp = false;
	}

	@Override
	public String getType() {
		return "axe";
	}
}
