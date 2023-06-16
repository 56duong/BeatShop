package beatalbumshop.componment;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * A custom panel with rounded corners.
 */
public class MyPanel extends JPanel {
    private Shape shape;
    private int radius = 20;

    /**
     * Gets the radius of the panel's rounded corners.
     *
     * @return the radius of the rounded corners
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the panel's rounded corners.
     *
     * @param radius the radius of the rounded corners
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    /**
     * Creates a new instance of the MyPanel class.
     * Sets the panel to be transparent and changes the cursor to a hand cursor.
     */
    public MyPanel() {
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Overrides the paintComponent method to fill the panel with a rounded rectangle shape.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         super.paintComponent(g);
    }
    
    /**
     * Overrides the paintBorder method to draw the border of the panel as a rounded rectangle shape.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border instanceof LineBorder) {
            LineBorder lineBorder = (LineBorder) border;
            g.setColor(lineBorder.getLineColor());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }
    
    /**
     * Overrides the contains method to check if the specified coordinates are contained within the panel's shape.
     *
     * @param x the x-coordinate to be tested
     * @param y the y-coordinate to be tested
     * @return true if the specified coordinates are contained within the panel's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
}

