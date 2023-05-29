package iet.hf.monkesoft.electric.boogaloo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * A kirajzolható objektumok ősosztálya.
 * */
public class JDrawable extends JComponent {
	//
	public static final String IMG_FIELD = "field.png";
	public static final String IMG_LAB = "lab.png";
	public static final String IMG_SHELTER = "shelter.png";
	public static final String IMG_STORAGE = "warehouse.png";
	
	public static final String IMG_VIROLOGIST = "big sad monke.png";
	public static final String IMG_VIROLOGIST_ACTUAL = "happy monke.png";

	public static final String IMG_CAPE = "cape.png";
	public static final String IMG_GLOVE = "glove.png";
	public static final String IMG_BAG = "bag.png";
	public static final String IMG_AXE = "axe.png";
	
	public static final String IMG_GENCODE = "gencode.png";
	public static final String IMG_STUN = "stun.png";
	public static final String IMG_AMNESIA = "amnesia.png";
	public static final String IMG_BEAR = "bear.png";
	public static final String IMG_REFLECT = "reflect.png";
	public static final String IMG_CONFUSION = "confusin.png";
	public static final String IMG_INVENTORYINCREASE = "invincr.png";
	public static final String IMG_GENERAL = "general.png";
	
	private JButton selectButton;
	private BufferedImage img;
	
	public JDrawable(String imgName) {
		setImage(imgName);
		this.setBackground(Color.DARK_GRAY);
		this.setVisible(true);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(50,50));
	}
	protected void setImage(String imgName)
	{
		try {
		    this.img = ImageIO.read(new File("./src/main/resources/" + imgName));
		} catch (IOException e) {
			System.out.println("Not found: " + imgName);
		    e.printStackTrace();
		}
	}
	/**
	 * Kirajzolja a iet.hf.monkesoft.electric.boogaloo.JDrawable objektumot
	 * */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
	
	/**
	 * Minden leszármazott a megfelelő logikát és a szükséges 
	 * objektumok kirajzolását hajtja végre, ennek megfelelően override-olják.
	 * */
	protected void onClick() {
		
	}
	public JButton getSelectButton() {
		return selectButton;
	}
	public void setSelectButton(JButton selectButton) {
		this.selectButton = selectButton;
	}
}
