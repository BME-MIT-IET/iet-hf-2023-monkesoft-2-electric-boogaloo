import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A virológust reprezentáló grafikus elem osztálya.
 * */
public class JVirologist extends JDrawable implements MouseListener{
	
	private Virologist virologist;
	public ArrayList<JDrawable> drawables;
	
	Interface iface; View view;
	
	boolean highlighted = false;
	
	public JVirologist(View view, Interface iface, Virologist v) {
		super(JDrawable.IMG_VIROLOGIST);
		this.iface = iface;
		this.view = view;
		this.setVirologist(v);
		
	}
	public void setListener(boolean b)
	{
		if(b)
			addMouseListener(this);
		else
			removeMouseListener(this);
	}
	
	/**
	 * Kirajzolja a virológust
	 * */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(highlighted)
		{
			g.setColor(Color.red);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5));
			g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		}
	}
	
	/**
	 * Megváltoztatja az objektum kirajzolt képét. Kör kezdetekor, illetve kör végén hívódik meg. 
	 * Ilyen értelemben két állapota van egy virológusnak: vagy az ő köre van, vagy nem. 
	 * Ennek megfelelő képre változtat.
	 * */
	public void ChangePicture() {
		setImage(view.getCurrentVirologist() == virologist ? IMG_VIROLOGIST_ACTUAL : IMG_VIROLOGIST);
	}


	public Virologist getVirologist() {
		return virologist;
	}


	public void setVirologist(Virologist virologist) {
		this.virologist = virologist;
	}


	public void setHighlight(boolean b) {
		this.highlighted = b;
	}
	
	public void mouseReleased(MouseEvent e) {
		System.out.println("DEBUG: virologist click");
		view.onVirologistClick(virologist);
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
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
}
