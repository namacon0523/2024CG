package enshu10;

import java.awt.Graphics;

public class Triangle extends Figure {
    private int[] xPoints;
    private int[] yPoints;

    public Triangle() {
        super();
        setName("三角形");
        xPoints = new int[3];
        yPoints = new int[3];
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void moveto(int x, int y) {
        super.moveto(x, y);
        calculateTrianglePoints();
    }

    @Override
    public void setWH(int width, int height) {
        super.setWH(width, height);
        calculateTrianglePoints();
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    private void calculateTrianglePoints() {
        xPoints[0] = x;
        yPoints[0] = y;

        xPoints[1] = x + getWidth() / 2;
        yPoints[1] = y + getHeight();

        xPoints[2] = x - getWidth() / 2;
        yPoints[2] = y + getHeight();
    }
}
