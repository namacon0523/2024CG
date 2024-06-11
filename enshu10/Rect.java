package enshu10;

import java.awt.Graphics;

public class Rect extends Figure{
	public Rect(){
		super();
		setName("四角形");
	}
	@Override public void paint(Graphics g) {
		g.setColor(getColor());
		int rectX = x;
	    int rectY = y;
	    int rectWidth = w;
	    int rectHeight = h;
	    if (w < 0) {
	        rectX = x + w;
	        rectWidth = -w;
	}
	    if (h < 0) {
	        rectY = y + h;
	        rectHeight = -h;
	    } 
	    g.drawRect(rectX, rectY, rectWidth, rectHeight);
	}
}