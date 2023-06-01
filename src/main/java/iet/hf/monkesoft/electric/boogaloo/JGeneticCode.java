package iet.hf.monkesoft.electric.boogaloo;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A genetikai kódok megjelenítéséért felelős grafikus osztály. 
 * A genetikai kódokból vírusokat, illetve vakcinákat lehet előállítani, megfelelő anyagmennyiség birtokában. 
 * */
public class JGeneticCode extends JDrawable implements MouseListener{
	private GeneticCode geneticCode;

	JButton bVirus = new JButton("Make virus");
	JButton bVaccine = new JButton("Make vaccine");
	
	JInventory inventory;
	Interface iface;
	
	public JGeneticCode(Interface iface, JInventory inventory, GeneticCode geneticCode, String img) {
		super(img);
		this.inventory = inventory;
		this.geneticCode = geneticCode;
		this.iface = iface;
		addMouseListener(this);
		bVirus.addMouseListener(this);
		bVaccine.addMouseListener(this);
		bVirus.setAlignmentX(CENTER_ALIGNMENT);
		bVaccine.setAlignmentX(CENTER_ALIGNMENT);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == this)
		{
			inventory.setOptionButtons(new JButton[] {bVirus, bVaccine});
		}
		if(e.getSource() == bVirus)
		{
			iface.GUIcommand("make", "virus", geneticCode.getName());
			inventory.updateContent(null);
			inventory.setOptionButtons(null);
		}
		if(e.getSource() == bVaccine)
		{
			iface.GUIcommand("make", "vaccine", geneticCode.getName());
			inventory.updateContent(null);
			inventory.setOptionButtons(null);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
