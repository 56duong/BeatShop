package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * The MyLabel class is a custom JLabel component that provides additional features such as rounded corners and custom painting.
 */
public class MyLabel extends JLabel {
    private Shape shape;
    private int radius = 0;

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
     * Creates a new instance of the MyLabel class with default settings.
     * This constructor sets the label's properties, such as background color, font, and cursor.
     */
    public MyLabel() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Creates a new instance of the MyLabel class with the specified text and default settings.
     * This constructor sets the label's properties, such as text, background color, font, and cursor.
     *
     * @param text the text to be displayed on the label
     */
    public MyLabel(String text) {
        setText(text);
        setOpaque(false);
        setBackground(null);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Overrides the paintComponent method to paint the label.
     * This method fills the background of the label with the specified color and paints the rounded rectangle shape using the specified radius.
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
     * Overrides the paintBorder method to paint the label's border.
     * This method draws the rounded rectangle shape of the label's border using the specified radius.
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
     * Overrides the contains method to check if the specified point is inside the label's shape.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the specified point is inside the label's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
}

