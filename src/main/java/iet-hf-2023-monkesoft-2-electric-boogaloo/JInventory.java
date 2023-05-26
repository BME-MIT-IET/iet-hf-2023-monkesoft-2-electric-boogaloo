

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JInventory extends JPanel implements MouseListener{
	
	Interface iface;
	View view;
	
	JPanel topPanel;
	JPanel equipped, effects, gencodes, vaccines, viruses, equipments;
	
	JLabel aminoLabel, nucleoLabel, moveLabel;

	JPanel actionPanel = new JPanel();
	JPanel optionPanel = new JPanel();
	JButton bInteract = new JButton("Interact");
	JButton bMove = new JButton("Move");
	JButton bEnd= new JButton("End turn");
	JButton bLoot = new JButton("Loot");
	JButton bConsoleLook = new JButton("Look");
	
	public JInventory(Interface iface, View view) 
	{
		this.iface = iface;
		this.view = view;
		setPreferredSize(new Dimension(285, getHeight()));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.darkGray);
		
		for (Component c : optionPanel.getComponents())
			((JComponent) c).setAlignmentX(CENTER_ALIGNMENT);
		
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.setBackground(Color.darkGray);
		
		// panel az akciógomboknak
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.setBackground(Color.darkGray);
		actionPanel.add(bInteract);
		actionPanel.add(bMove);
		actionPanel.add(bEnd);
		actionPanel.add(bLoot);
		
		for (Component c : actionPanel.getComponents())
			((JComponent) c).setAlignmentX(CENTER_ALIGNMENT);
		
		bInteract.addMouseListener(this);
		bMove.addMouseListener(this);
		bEnd.addMouseListener(this);
		bConsoleLook.addMouseListener(this);
		bLoot.addMouseListener(this);
		
		// a felso panel a majommal meg a felvett felszerelésekkel
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(Color.white);
		topPanel.setPreferredSize(new Dimension(300, 240));
		topPanel.setSize(getPreferredSize());
		BufferedImage tMonke = null;
		try {
			tMonke = ImageIO.read(new File("./src/main/resources/t.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel monke = new JLabel(new ImageIcon(tMonke));
		topPanel.add(monke, BorderLayout.LINE_START);
		
		// felvett felszerelések panelje
		equipped = new JPanel();
		equipped.setBackground(Color.lightGray);
		equipped.setPreferredSize(new Dimension(80, 240));
		GridLayout glo = new GridLayout(3,1);
		equipped.setLayout(glo);
		topPanel.add(equipped, BorderLayout.LINE_END);
		add(topPanel);
		
		// panel az anyagok és lépések kiírására
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new BorderLayout());
		numberPanel.setMaximumSize(new Dimension(300, 20));
		numberPanel.setSize(getPreferredSize());
		numberPanel.setBackground(Color.white);
		aminoLabel = new JLabel();
		nucleoLabel = new JLabel();
		moveLabel = new JLabel();
		numberPanel.add(aminoLabel, BorderLayout.LINE_START);
		numberPanel.add(nucleoLabel, BorderLayout.CENTER);
		numberPanel.add(moveLabel, BorderLayout.LINE_END);
		add(numberPanel);
		
		// panel az appliedeffekteknek
		JPanel effectPanel = new JPanel();
		effectPanel.setLayout(new BoxLayout(effectPanel, BoxLayout.PAGE_AXIS));
		effectPanel.setBackground(Color.darkGray);
		JLabel el = new JLabel("Effects:");
		el.setForeground(Color.white);
		effectPanel.add(el);
		effects = new JPanel();
		JScrollPane effectScroll = new JScrollPane(effects, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		effectScroll.setPreferredSize(new Dimension(300, 60));
		JPanel effScrPanel = new JPanel();
		effScrPanel.setBackground(Color.darkGray);
		effScrPanel.add(effectScroll);
		effectPanel.add(effScrPanel);
		add(effectPanel);
		
		// panel a genetikai kódoknak
		JPanel BIGgenCodePanel = new JPanel();
		BIGgenCodePanel.setLayout(new FlowLayout());
		BIGgenCodePanel.setBackground(Color.darkGray);
		BIGgenCodePanel.setMaximumSize(new Dimension(300, 80)); //Ez vamaiért mindegy mennyi, de itt kell lennie
		JDrawable gc = new JDrawable("gencode.png");
		gc.setPreferredSize(new Dimension(70,70));
		BIGgenCodePanel.add(gc);
		JPanel genCodePanel = new JPanel();
		genCodePanel.setLayout(new BoxLayout(genCodePanel, BoxLayout.PAGE_AXIS));
		JLabel gcl = new JLabel("Genetic Codes:");
		gcl.setForeground(Color.white);
		genCodePanel.add(gcl);
		genCodePanel.setBackground(Color.darkGray);
		gencodes = new JPanel();
		gencodes.setLayout(new FlowLayout());
		JScrollPane genCodeScroll = new JScrollPane(gencodes, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		genCodeScroll.setPreferredSize(new Dimension(200, 60));
		genCodePanel.add(genCodeScroll);
		BIGgenCodePanel.add(genCodePanel);
		add(BIGgenCodePanel);
		
		// vakcina panel
		JPanel BIGvaccinePanel = new JPanel();
		BIGvaccinePanel.setLayout(new FlowLayout());
		BIGvaccinePanel.setBackground(Color.darkGray);
		BIGvaccinePanel.setMaximumSize(new Dimension(300, 80)); //Ez vamaiért mindegy mennyi, de itt kell lennie
		JDrawable vaccine = new JDrawable("vaccine.png");
		vaccine.setPreferredSize(new Dimension(70,70));
		BIGvaccinePanel.add(vaccine);
		JPanel vaccinePanel = new JPanel();
		vaccinePanel.setLayout(new BoxLayout(vaccinePanel, BoxLayout.PAGE_AXIS));
		vaccinePanel.setBackground(Color.darkGray);
		JLabel vaccl = new JLabel("Vaccines:");
		vaccl.setForeground(Color.white);
		vaccinePanel.add(vaccl);
		vaccines = new JPanel();
		vaccines.setLayout(new FlowLayout());
		JScrollPane vaccineScroll = new JScrollPane(vaccines, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		vaccineScroll.setPreferredSize(new Dimension(200, 60));
		vaccinePanel.add(vaccineScroll);
		BIGvaccinePanel.add(vaccinePanel);
		add(BIGvaccinePanel);
		
		// panel a vírusoknak
		JPanel BIGvirusPanel = new JPanel();
		BIGvirusPanel.setLayout(new FlowLayout());
		BIGvirusPanel.setBackground(Color.darkGray);
		BIGvirusPanel.setMaximumSize(new Dimension(300, 80)); //Ez valamiért mindegy mennyi, de itt kell lennie
		JDrawable virus = new JDrawable("virus.png");
		virus.setPreferredSize(new Dimension(70,70));
		BIGvirusPanel.add(virus);
		JPanel virusPanel = new JPanel();
		virusPanel.setLayout(new BoxLayout(virusPanel, BoxLayout.PAGE_AXIS));
		virusPanel.setBackground(Color.darkGray);
		JLabel vl = new JLabel("Viruses:");
		vl.setForeground(Color.white);
		virusPanel.add(vl);
		viruses = new JPanel();
		viruses.setLayout(new FlowLayout());
		JScrollPane virusScroll = new JScrollPane(viruses, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		virusScroll.setPreferredSize(new Dimension(200, 60));
		virusPanel.add(virusScroll);
		BIGvirusPanel.add(virusPanel);
		add(BIGvirusPanel);
		
		// panel a fel nem vett felszereléseknek
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.PAGE_AXIS));
		inventoryPanel.setBackground(Color.darkGray);
		JLabel ei = new JLabel("Equipment Inventory:");
		ei.setForeground(Color.white);
		inventoryPanel.add(ei);
		equipments = new JPanel();
		equipments.setLayout(new FlowLayout());
		JScrollPane inventoryScroll = new JScrollPane(equipments, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inventoryScroll.setPreferredSize(new Dimension(300, 60));
		JPanel invScrPanel = new JPanel();
		invScrPanel.setBackground(Color.darkGray);
		invScrPanel.add(inventoryScroll);
		inventoryPanel.add(invScrPanel);
		add(inventoryPanel);
		
		add(actionPanel);
		add(optionPanel);
	}
	
	Virologist lastVirologist = null;
	
	private String getEffectImage(String name)
	{
		String img = null;
		if(name.equals("stun"))
			img = JDrawable.IMG_STUN;
		if(name.equals("amnesia"))
			img = JDrawable.IMG_AMNESIA;
		if(name.equals("bearconfusion"))
			img = JDrawable.IMG_BEAR;
		if(name.equals("reflect"))
			img = JDrawable.IMG_REFLECT;
		if(name.equals("confusion"))
			img = JDrawable.IMG_CONFUSION;
		if(name.equals("inventoryincrease"))
			img = JDrawable.IMG_INVENTORYINCREASE;
		if(name.equals("general"))
			img = JDrawable.IMG_GENERAL;

		if(img == null)
			System.out.println("Szar az effect name!!!!");
		return img;
	}
	
	private String getEquipmentImage(String name)
	{
		String img = null;
		if(name.equals("cape"))
			img = JDrawable.IMG_CAPE;
		if(name.equals("glove"))
			img = JDrawable.IMG_GLOVE;
		if(name.equals("bag"))
			img = JDrawable.IMG_BAG;
		if(name.equals("axe"))
			img = JDrawable.IMG_AXE;
		return img;
	}
	// save to remove at next call
	JButton optionButtons[] = null;
	public void setOptionButtons(JButton buttons[]) {
		
		if(optionButtons != null)
			for(JButton b : optionButtons)
				optionPanel.remove(b);
		if(buttons != null)
		{	
			for(JButton b : buttons)
				optionPanel.add(b);
		}
		optionButtons = buttons;
		revalidate();
	}
	// frissíti az inventorypanel tartalmát
	public void updateContent(Virologist virologist)
	{
		// if dunno, null was given
		if(virologist == null)
			virologist = lastVirologist;
		
		this.lastVirologist = virologist;
		// numbers
		aminoLabel.setText("  Amino Acid: " + String.valueOf(virologist.getMaterialList().get(0).getAmount()) + "         ");
		nucleoLabel.setText("Nucleotid: " + String.valueOf(virologist.getMaterialList().get(1).getAmount()));
		moveLabel.setText("Moves left: " + String.valueOf(virologist.getMovementPoints()) + "  ");
		
		// effects
		effects.removeAll();
		for(AppliedEffect ae : virologist.getAe())
		{
			String name = "";
			try
			{
				name = ((Individual) ae.GetEffect()).getEffect().name;
			} catch(ClassCastException e)
			{
				name = ae.GetEffect().name;
			}
			effects.add(new JEffect(getEffectImage(name), ae));
		}
		
		// gencodes
		gencodes.removeAll();
		for(GeneticCode code : virologist.getCodeList())
		{
			gencodes.add(new JGeneticCode(iface, this, code, getEffectImage(code.getName())));
		}
		
		// agents
		vaccines.removeAll();
		viruses.removeAll();
		for(Agent agent : virologist.getAgentInventory())
		{
			if(agent.getClass() == Vaccine.class)
				vaccines.add(new JAgent(view, agent, getEffectImage(agent.Eff.toString())));
			else
				viruses.add(new JAgent(view, agent, getEffectImage(agent.Eff.toString())));	
		}
		// equipped
		equipped.removeAll();
		for(Equipment eq : virologist.getEquippedEquipments())
		{
			equipped.add(new JEquipment(iface, this, eq, true, getEquipmentImage(eq.getType())));	
		}

		// equipment inventory
		equipments.removeAll();
		for(Equipment eq : virologist.getEquipmentInventory())
		{
			if(eq.getClass() == Axe.class)
				equipments.add(new JAxe(view, iface, this, (Axe) eq, false, getEquipmentImage(eq.getType())));
			else	
				equipments.add(new JEquipment(iface, this, eq, false, getEquipmentImage(eq.getType())));	
		}
		
		for (Component c : actionPanel.getComponents())
			((JComponent) c).setAlignmentX(CENTER_ALIGNMENT);
		for (Component c : optionPanel.getComponents())
			((JComponent) c).setAlignmentX(CENTER_ALIGNMENT);
		
		// loot button or idk
		boolean found = false;
		for(Virologist v2 : virologist.getField().GetVirologists())
		{
			if(v2.Stunned() && v2 != virologist)
			{
				found = true;
				break;
			}
		}
		if(found)
			actionPanel.add(bLoot);
		else
			actionPanel.remove(bLoot);
		
		revalidate();
	}
	private void onInteract()
	{
		iface.GUIcommand("interact");
		updateContent(lastVirologist);
		if (view.getCurrentVirologist().haveWon())
			view.EndGame();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == bInteract)
			onInteract();
		if(e.getSource() == bMove)
			view.onMoveClick();
		if(e.getSource() == bEnd)
			view.onEndTurn();
		if(e.getSource() == bConsoleLook)
			iface.GUIcommand("look");
		if(e.getSource() == bLoot)
			view.onLootClick();
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
