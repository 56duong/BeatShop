package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 * The MyComboBox class is a custom combo box component that extends the JComboBox class.
 * It provides combo boxes with rounded corners and customizable painting behavior.
 */
public class MyComboBox extends JComboBox {
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
     * Creates a new instance of the MyComboBox class with default settings.
     * This constructor sets the combo box's properties, such as opacity, font, and cursor.
     */
    public MyComboBox() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Overrides the paintComponent method to paint the combo box component.
     * This method fills the background of the combo box with the specified color and paints the rounded rectangle shape using the specified radius.
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
     * Overrides the paintBorder method to paint the combo box's border.
     * This method draws the rounded rectangle shape of the combo box's border using the specified radius.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border instanceof CompoundBorder) {
            CompoundBorder compoundBorder = (CompoundBorder) border;
            border = compoundBorder.getOutsideBorder();
        }
        if (border instanceof LineBorder) {
            LineBorder lineBorder = (LineBorder) border;
            g.setColor(lineBorder.getLineColor());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }
    
    /**
     * Overrides the contains method to check if the specified point is inside the combo box's shape.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the specified point is inside the combo box's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
    
    
    
    /**
     * The MyComboBoxRenderer class is a custom renderer for combo box items.
     * It extends the JLabel class and provides a custom rendering for the combo box items.
     */
    public static class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
        private String _title;

        /**
         * Creates a new instance of the MyComboBoxRenderer class with the specified title.
         *
         * @param title the title to be displayed when no item is selected
         */
        public MyComboBoxRenderer(String title) {
            _title = title;
        }

        /**
         * Overrides the getListCellRendererComponent method to provide custom rendering for combo box items.
         *
         * @param list         the JList being rendered
         * @param value        the value to be displayed
         * @param index        the index of the value in the list
         * @param isSelected   true if the specified index is selected, false otherwise
         * @param hasFocus     true if the specified index has focus, false otherwise
         * @return the component used for rendering the item
         */
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            if (index == -1 && value == null) setText(_title);
            else setText(value.toString());
            return this;
        }
    }
}

