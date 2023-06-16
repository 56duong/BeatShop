package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 * A custom text field with rounded corners.
 */
public class MyTextField extends JTextField {
    private Shape shape;
    private int radius = 20;
    private Color disabledBackgroundColor = getBackground();

    /**
     * Get the radius of the rounded corners.
     *
     * @return The radius of the rounded corners.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set the radius of the rounded corners.
     *
     * @param radius The radius of the rounded corners.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    /**
     * Constructs a new MyTextField.
     * Sets the component's properties, such as opacity, font, and cursor.
     */
    public MyTextField() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }
    
    /**
     * Paint the component's content area.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
    }
    
    /**
     * Paint the component's border.
     *
     * @param g The Graphics object used for painting.
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
     * Check if the specified point is contained within the component's shape.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return {@code true} if the point is contained within the shape, {@code false} otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        return shape.contains(x, y);
    }
    
    /**
     * Get the background color of the component.
     * If the component is disabled, the disabledBackgroundColor will be returned instead.
     *
     * @return The background color of the component or the disabledBackgroundColor if the component is disabled.
     */
    @Override
    public Color getBackground() {
        if (isEnabled()) {
            return super.getBackground();
        } else {
            return disabledBackgroundColor;
        }
    }

    /**
     * Get the background color used when the component is disabled.
     *
     * @return The background color used when the component is disabled.
     */
    public Color getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }

    /**
     * Set the background color to be used when the component is disabled.
     *
     * @param disabledBackgroundColor The background color to be used when the component is disabled.
     */
    public void setDisabledBackgroundColor(Color disabledBackgroundColor) {
        this.disabledBackgroundColor = disabledBackgroundColor;
    }
    
}

