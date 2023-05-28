package iet.hf.monkesoft.electric.boogaloo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A mezők megjelenítésért felelős osztály. 
 * 
 * Ha egy olyan mezőre kattintunk, amin nem állunk, megpróbálunk rálépni. 
 * Ha arra a mezőre kattintunk, amin állunk interaktálhatunk vele, 
 * vagy ha a saját mezőnkön egy lebénult virológus található akkor anyagot vagy felszerelést lophatunk tőle.
 * 
 * */
public class JField extends JDrawable implements MouseListener{
	private View view;
	private Field field;
	public JButton bInteract;
	public JButton bStealEq[];
	private JButton bStealMat[];
	
	private boolean highlighted = false;
	
	JField(View view, Field field, String imgName, int i, int j)
	{
		super(imgName);
		this.field = field;
		this.view = view;
		setLayout(new GridLayout(0, 2, 10, 10));
		addMouseListener(this);
	}
	/**
	 * Kirajzolja a mezőt 
	 * */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(highlighted)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(5));
			g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		}
	}
	public void setHighlight(boolean h)
	{
		this.highlighted = h;
		repaint();
	}
	public Field getField()
	{
		return field;
	}
	
	/**
	 *  A mezőre kattintás hatására fut le, kirajzolja a szükséges gombokat
	 * */
	protected void onClick() {
		
	}
	
	/**
	 *  Ha az interact gombra kattintunk, a mező fajtája szerint megtanulhatunk egy genetetikai kódot, 
	 *  gyűjthetünk anyagot vagy felvehetünk védőfelszerelést
	 * */
	public void onInteractClick() {
		
	}
	
	/**
	 *  A velünk egy mezőn lévő bénult virológustól lophatunk felszerelést 
	 * */
	public void onStealEqClick() {
		
	}
	
	/**
	 * A velünk egy mezőn lévő bénult virológustól lophatunk anyagot
	 * */
	public void onStealMatClick() {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		view.onFieldClick(field);
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
