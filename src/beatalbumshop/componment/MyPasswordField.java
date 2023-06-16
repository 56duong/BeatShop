package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 * A custom password field with rounded corners.
 */
public class MyPasswordField extends JPasswordField {
    private Shape shape;
    private int radius = 20;

    /**
     * Gets the radius of the password field's rounded corners.
     *
     * @return the radius of the rounded corners
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the password field's rounded corners.
     *
     * @param radius the radius of the rounded corners
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    /**
     * Creates a new instance of the MyPasswordField class.
     * Sets the password field to be transparent, sets the font to "Open Sans" with size 16,
     * and changes the cursor to a text cursor.
     */
    public MyPasswordField() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }
    
    /**
     * Overrides the paintComponent method to fill the password field with a rounded rectangle shape.
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
     * Overrides the paintBorder method to draw the border of the password field as a rounded rectangle shape.
     * If the border is a LineBorder or a CompoundBorder, the rounded rectangle shape is drawn accordingly.
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
        else if (border instanceof CompoundBorder) {
            LineBorder lineBorder = (LineBorder) ((CompoundBorder) border).getOutsideBorder();
            g.setColor(lineBorder.getLineColor());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }
    
    /**
     * Overrides the contains method to check if the specified coordinates are contained within the password field's shape.
     *
     * @param x the x-coordinate to be tested
     * @param y the y-coordinate to be tested
     * @return true if the specified coordinates are contained within the password field's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
}

