import java.util.ArrayList;

public class Storage extends Field {
	/**
	 * A list containing a nucleotid and an aminoacid, representing the amount of
	 * stuff in the storage
	 * 
	 */
	
	private ArrayList<Material> MaterialList = new ArrayList<Material>();
	
	public Storage(int amino, int nucleo, int id)
	{
		super(id);
		MaterialList.add(new AminoAcid(amino));
		MaterialList.add(new Nucleotid(nucleo));
	}
	/*
	 * If the accepted Virologist is under BearConfusion,
	 * Destroy all materials
	 */
	
	@Override
	public void ModInteract(Virologist v) {
		//Makes the MaterialList's elements empty
		for(Material m: this.MaterialList) {
			m.SubtractAminoAcid(m.getAminoAcid());
			m.SubtractNucleotid(m.getNucleotid());
		}
	}
	
	@Override
	public void Interacted(Virologist v) {
		
		int storageAminoAcid = 0, storageNucleotid = 0, virologistAminoAcid = 0, virologistNucleotid = 0;
		
		for(Material m : MaterialList)
		{
			int a = m.getAminoAcid();
			if(a > 0)
				storageAminoAcid = a;
			
			int n = m.getNucleotid();
			if(n > 0)
				storageNucleotid = n;
		}
		
		for(Material m : v.getMaterialList())
		{
			int a = m.getMaxAminoAcid() - m.getAminoAcid();
			if(a > 0)
				virologistAminoAcid = a;
			
			int n = m.getMaxNucleotid() - m.getNucleotid();
			if(n > 0)
				virologistNucleotid = n;
		}
		int amino = Math.min(storageAminoAcid, virologistAminoAcid);
		int nucleo = Math.min(storageNucleotid, virologistNucleotid);
		
		for(Material m : MaterialList)
		{
			m.SubtractAminoAcid(amino);
			m.SubtractNucleotid(nucleo);
		}
		for(Material m : v.getMaterialList())
		{
			m.AddAminoAcid(amino);
			m.AddNucleotid(nucleo);
		}
		
	}
	@Override
	public String toString() {
		int amino = 0, nucleo = 0;
		for(Material m : MaterialList)
		{
			amino += m.getAminoAcid();
			nucleo += m.getNucleotid();
		}
		return "\t(" + Id + ") Storage\n"
			+ "\t\tMaterials:\n"
			+ "\t\t\tAmino acid: " + amino + "\n"
			+ "\t\t\tNucleotid: " + nucleo
		;
	}
}
