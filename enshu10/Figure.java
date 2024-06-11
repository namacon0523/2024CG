package enshu10;

import java.awt.Color;
import java.awt.Graphics;

public  class Figure extends Coord {
	private Color color = Color.black;
	int w,h;
	protected String name;
	Figure(){
		w=h=0;
	}
	public void paint(Graphics g) {}
	public void setWH(int w,int h) {
		this.w=w;
		this.h=h;
	}
	public String getName() {
		return name;
	}
    public void setName(String name) {
        this.name = name;
    }
    Color getColor() {
	        return  color;
	    }
	public void setColor(Color color) {
		this.color=color;
	}
    public int getWidth() {
    	return w;
    }
    public  int getHeight() {
    	return h;
    }
	@Override
	public String toString() {
	    return "Figure [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
	}
}