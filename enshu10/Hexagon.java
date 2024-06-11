package enshu10;

import java.awt.Graphics;

public class Hexagon extends Figure {
    public Hexagon() {
        super();
        setName("六角形");
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI / 6 * i;
            xPoints[i] = (int) (x + getWidth() * Math.cos(angle));
            yPoints[i] = (int) (y + getHeight() * Math.sin(angle));
        }
        g.drawPolygon(xPoints, yPoints, 6);
    }
}