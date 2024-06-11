package enshu10;

import java.awt.Graphics;

public class Circle extends Figure{
	 int radius;
	public Circle(){
		this.radius = 0;
		setName("円");
	}
	@Override public void paint(Graphics g) {
		int r=(int)Math.sqrt((double)(w*w+h*h));
		g.setColor(getColor());
		g.drawOval(x-r,y-r,r*2,r*2);
	}
}