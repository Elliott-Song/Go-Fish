package cardgame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Card implements Comparable<Card>{
	public ImageIcon myImage, mySmallImage, myBack;
	public int x,y,wid, val; //pos
	public boolean isVisible;
        public String name;
	
	public Card(String symbol, int posX, int posY, String back, int value){
                val = value;
		x = posX;
		y = posY;
                wid=130;
                name = symbol.split("")[0];
		myImage = new ImageIcon("PNG/" + symbol + ".png");
		myImage = new ImageIcon(getScaledImage(myImage.getImage(),wid,wid*5/3));
                myBack = new ImageIcon("PNG/" + back);
		myBack = new ImageIcon(getScaledImage(myBack.getImage(),wid,wid*5/3));
                mySmallImage = new ImageIcon(getScaledImage(myImage.getImage(),wid/3,wid*5/9));
		System.out.println(symbol + value);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
        // compares the "value" of one card to another's and returns an integer based on that
        public int compareTo(Card other) {
            int r = 0;
            if (val > other.val) {
                r= 1;
            } else if (val < other.val) {
                r = -1;
            }
            return r;
        }
}
