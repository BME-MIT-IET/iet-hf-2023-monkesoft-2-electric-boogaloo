package iet.hf.monkesoft.electric.boogaloo;

import java.util.ArrayList;

public class BearConfusion extends Effect{
	public BearConfusion() {
		super("bearconfusion");
		this.Time = -1;
	}
	/**
	 * Modifies the movement of v similarly to
	 * iet.hf.monkesoft.electric.boogaloo.Confusion, but if the randomly selected field is
	 * a storage, then every material is destroyed there.
	 * Also, if a virologist is standing on the randomly
	 * selected field, then this effect will spread on them.
	 */
	@Override
	public boolean ModMove(Virologist v) {
		//virologus mezoje
		Field Current=v.getField();
		ArrayList<Field> neighbours = 
				Current.GetNeighbours();
		//kivalaszt egy random szomszed mezot
		Field selected = neighbours.get(Interface.Random.nextInt(0, neighbours.size()-1));
		
		//atteszi a kivalsztott mezore
		if(selected.Accept(v)){
			if(v.Place != null)
				v.Place.Remove(v);
			v.setMovementPoints(v.getMovementPoints() - 1);
			v.SetField(selected);
			//Tomi itt kell egy mod interact 
			v.Place.ModInteract(v);
		}
		
		//Get virologists already standing on the 
		//selected field before the move
		ArrayList<Virologist> virologists = v.getField().GetVirologists();
	
		for(Virologist vir: virologists) {
			if (vir != v) {
				Virus virus = new Virus(this);
				vir.ReceivedAgent(virus, null);
			}
		}
		
		return true;
	}
}
