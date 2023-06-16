package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * The MyButton class is a custom button component that extends the JButton class.
 * It provides buttons with rounded corners and customizable painting behavior.
 */
public class MyButton extends JButton {
    private Shape shape;
    private int radius = 20;

    /**
     * Returns the radius of the rounded corners.
     *
     * @return the radius of the rounded corners
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the rounded corners.
     *
     * @param radius the radius of the rounded corners
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    /**
     * Creates a new instance of the MyButton class with default settings.
     * This constructor sets the button's properties, such as opacity, font, cursor, and focus painting.
     */
    public MyButton() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
    }
    
    /**
     * Overrides the paintComponent method to paint the button component.
     * This method fills the background of the button with the specified color and paints the rounded rectangle shape using the specified radius.
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
     * Overrides the paintBorder method to paint the button's border.
     * This method draws the rounded rectangle shape of the button's border using the specified radius.
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
     * Overrides the contains method to check if the specified point is inside the button's shape.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the specified point is inside the button's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
}

