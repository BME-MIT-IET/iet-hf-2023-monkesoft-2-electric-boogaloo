import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Individual.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//




public class Individual extends Protection {
	private Effect Eff;

	public Individual(Effect eff) {
		super("individual", 1);
		Eff=eff;
	}
	@Override
	public boolean Protect(Agent a, Virologist v, Virologist v2) {
		//boolean returnbool = Tester.Ask("Sikerüljön a védés?", List.of("I", "N")) == 0;
		if(a.getEff() == this.Eff) {
			return true;
		}
		return false;
		
		//return returnbool;
	}
	@Override
	public String toString()
	{
		return name + " against " + Eff.name;
	}
	public Effect getEffect()
	{
		return Eff;
	}
}
