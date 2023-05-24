import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Az effekteket reprezentáló JDrawable osztályok ősosztálya.
 * */
public class JEffect extends JDrawable {
	
	private AppliedEffect effect;
	
	public JEffect(String imgName, AppliedEffect e) {
		super(imgName);
		this.effect = e;
	}
	
	/**
	 * Kirajzolja az effekt ikonját es az idot
	 * */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		if(effect.GetTime() > -1)
		{
			g.setColor(Color.red);
			FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(effect.GetTime() + "", g);
            g.setColor(Color.black);
            g.fillRect(4, 14 - fm.getAscent(),
                       (int) rect.getWidth(),
                       (int) rect.getHeight());
            g.setColor(Color.white);
			g.drawString(effect.GetTime() + "", 4, 14);
		}
		// DO NOT!!!!!!!!! cast to see if its individual
		if (effect.GetEffect().isIndividual()){
			g.setColor(Color.blue);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10));
			g2.drawRect(0, 0, getWidth(), getHeight());
		}
	}
}
