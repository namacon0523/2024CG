package enshu10;

import java.awt.Graphics;

public class Dot extends Figure{
	int size;
	public Dot(){
		size=10;
		setName("丸");
	}
	@Override public void paint(Graphics g) {
		g.setColor(getColor());
		g.fillOval(x-size/2,y-size/2,size,size);
	}
}