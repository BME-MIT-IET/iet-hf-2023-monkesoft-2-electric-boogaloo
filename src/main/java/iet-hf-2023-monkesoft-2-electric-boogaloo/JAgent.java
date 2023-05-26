import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;


/**
 * A JDrawable ágensek ősosztálya.
 * */
public class JAgent extends JDrawable implements MouseListener{
	private Agent agent;
	private View view;
	
	public JAgent(View view, Agent agent, String effectImage) {
		super(effectImage);
		this.agent = agent;
		this.view = view;
		addMouseListener(this);
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		if(agent.getTime() > -1)
		{
			g.setColor(Color.red);
			FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(agent.getTime() + "", g);
            g.setColor(Color.black);
            g.fillRect(4, 14 - fm.getAscent(),
                       (int) rect.getWidth(),
                       (int) rect.getHeight());
            g.setColor(Color.white);
			g.drawString(agent.getTime() + "", 4, 14);
		}
	}
	
	/**
	 * Az ágensre klikkeltünk, így elhasználhatjuk azt. 
	 * Bizonyos mennyiségű bUseAgent gombot rajzol a képernyőre. 
	 * A leszármazottak a funkciójuknak megfelelően override-olják.
	 * */
	protected void onClick() {
		
	}
	
	
	/**
	 * Megnyomtuk a bUseAgent tömb valamelyik tagját, így a kiválasztott gombhoz kötött virológusra használjuk az ágenst
	 * */
	public void onUseAgentClick(Virologist v) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(agent.getClass() == Virus.class)
		{
			view.onVirusClick(agent);
		} else 
		{
			view.iface.GUIcommand("usevaccine", agent.Id);
			view.inventoryPanel.updateContent(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
