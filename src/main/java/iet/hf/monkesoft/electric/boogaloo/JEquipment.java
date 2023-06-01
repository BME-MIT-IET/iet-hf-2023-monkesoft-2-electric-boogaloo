package iet.hf.monkesoft.electric.boogaloo;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * A virológusnál lévő felszerelések megjelenítéséért felelős osztály.
 * A virológus a felszereléseit több helyen is tarthatja.
 * Vagy hordja magán vagy az inventoryjában tárolja
 * A felszerelés eszerint a saját helyén rajzolódik ki. 
 * Viszont kiválasztásuk után tudjuk őket a két tároló között mozgatni 
 * */
public class JEquipment extends JDrawable implements MouseListener{
	

	private Equipment equipment;
	public JButton bDiscard = new JButton("Discard");
	public JButton bEquip = new JButton("Equip");
	public JButton bUnequip = new JButton("Unequip");
	
	JInventory inventory;
	Interface iface;
	boolean equipped;
	
	public JEquipment(Interface iface, JInventory inventory, Equipment equipment, boolean equipped, String imgName) {
		super(imgName);
		this.equipment = equipment;
		this.iface = iface;
		this.inventory = inventory;
		this.equipped = equipped;
		add(bDiscard);
		add(bEquip);
		add(bUnequip);
		bDiscard.addMouseListener(this);
		bEquip.addMouseListener(this);
		bUnequip.addMouseListener(this);
		
		addMouseListener(this);
	}

	/**
	 * Ha kiválasztunk egy felszerelést kiírja a szükséges gombokat
	 * */
	@Override
	protected void onClick() {
		if(equipped) 
			inventory.setOptionButtons(new JButton[] {bUnequip, bDiscard});
		else 
			inventory.setOptionButtons(new JButton[] {bEquip, bDiscard});
	}
	
	/**
	 * Ha megnyomjuk a discard gombot, eltűnik a virológustól a kiválasztott felszerelés
	 * */
	public void onDiscardClick() {
		iface.GUIcommand("discard", equipment.Id);
		inventory.setOptionButtons(null);
	}
	
	/**
	 * Ha megnyomjuk az equip gombot felveszi a virológus a kiválasztott felszerelést  
	 * */
	public void onEquipClick() {
		iface.GUIcommand("equip", equipment.Id);
		inventory.setOptionButtons(null);
	}
	
	/**
	 * Ha megnyomjuk az unequip gombot leveszi a virológus a kiválasztott felszerelést 
	 * */
	public void onUnEquipClick() {
		iface.GUIcommand("unequip", equipment.Id);
		inventory.setOptionButtons(null);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == this)
			onClick();
		if(e.getSource() == bEquip)
			onEquipClick();
		if(e.getSource() == bUnequip)
			onUnEquipClick();
		if(e.getSource() == bDiscard)
			onDiscardClick();
			
		inventory.updateContent(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
