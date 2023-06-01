package iet.hf.monkesoft.electric.boogaloo;

import java.util.ArrayList;

/**
 * 
 * A jĂˇtĂ©k cĂ©lja az ezen osztĂˇly Ăˇltal megvalĂłsĂ­tott jĂˇtĂ©kelemek megtalĂˇlĂˇsa. 
 * Labor tĂ­pusĂş mezĹ‘k Ă©s virolĂłgusok birtokolhatjĂˇk.
 *
 */


public class GeneticCode {
	//0 indexen vĂ­rust, 1 indexen vakcinĂˇt tĂˇroljon
	private ArrayList<Agent> Agents = new ArrayList<>();
	private String name;
	
	public GeneticCode(String name, Effect eff, Effect effgenprot) {
		this.name = name;
		Agents.add(new Virus(eff));
		Agents.add(new Vaccine(effgenprot));
	}
	/**
	 *A paramĂ©terkĂ©nt kapott virolĂłgus a genetikai kĂłd alapjĂˇn szeretne Ăˇgenst elĹ‘ĂˇllĂ­tani. 
	 *Ha a virolĂłgusnĂˇl van elĂ©g anyag az elkĂ©szĂ­tĂ©sĂ©hez, akkor a kĂ©sz Ăˇgens hozzĂˇadĂłdik az Ă�gens_inventoryjĂˇhoz, 
	 *egyĂ©bkĂ©nt nem tĂ¶rtĂ©nik semmi. 
	 *
	 */
	public void UsedForVirus(Virologist v, int id){
		Virus vi = new Virus(Agents.get(0), id);
		vi.Make(v);
	} 
	public void UsedForVaccine(Virologist v, int id){
		Vaccine va = new Vaccine(Agents.get(1), id);
		va.Make(v);
			
	}
	public Agent getVirus()
	{
		return Agents.get(0);
	}
	public Agent getVaccine()
	{
		return Agents.get(0);
	}
	public String getName()
	{
		return name;
	}
}
