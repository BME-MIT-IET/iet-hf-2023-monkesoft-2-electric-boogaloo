//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Reflect.java
//  @ Date : 2022. 03. 23.
//  @ Author :
//
//


/**
 * Olyan effekt singleton osztálya, amelytől a virológusra kent ágens visszakenődhet a kenést kezdeményező virológusra.
 */

public class Reflect extends Effect {
	Reflect()
	{
		super("reflect");
	}
	/**
	 * v megpróbálta az effekt viselőjét megkenni. Ha v-n nincsen Visszakenés effekt, akkor a kent ágens visszakenődik rá, ha va van, akkor pedig az ágens hatástalan.
	 *Mindig true-val tér vissza.
	 */
	@Override
	public boolean Protect(Agent a, Virologist v2, Virologist v) {
		
		//Check, if sender Virologist is null (laboratory can also spread viruses)
		if(v == null) { //Then check if sender virologist also has a glove equipped (reflect effect)
		} else if(v.HasEffect(this)) {
			for(AppliedEffect ae : v.getAe()) {
				if (ae.GetEffect() == this) {
					ae.DecrUses(v, this);
					break;
				}
			}
		}
		else { //If none of the above is true, send the virus back to the sender
			v.ReceivedAgent(a, null);
		}
		
		//Decrease the uses for a reflect effect (this makes the gloves breakable)
		for(AppliedEffect ae : v2.getAe()) {
			if (ae.GetEffect() == this) {
				ae.DecrUses(v2, this);
				break;
			}
		}
		
		return true;
	}

	public void Apply(Virologist v) {
		AppliedEffect ae = new AppliedEffect(v, this, -1);
		return;
	}

	public void Die(Virologist v) {
		v.RemoveEffect(this);
	}
}