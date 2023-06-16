package beatalbumshop.componment;

import java.awt.*;
import javax.swing.*;

/**
 * A custom radio button with a circular shape and customizable colors.
 */
public class MyRadioButton extends JRadioButton {

    private static final Color DEFAULT_COLOR = new Color(215, 215, 215);
    private static final Color SELECTED_COLOR = new Color(238, 84, 102); //[238,84,102]
    private static final Color SELECTED_CIRCLE_COLOR = Color.WHITE;
    private static final int CIRCLE_SIZE = 6;

    /**
     * Creates a new instance of the MyRadioButton class.
     * Sets the radio button to be transparent, sets the font to "Open Sans" with size 16,
     * changes the cursor to a hand cursor, and customizes the appearance with an icon.
     */
    public MyRadioButton() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setForeground(Color.BLACK);
        setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected()) {
                    g2.setColor(SELECTED_COLOR);
                    g2.fillOval(0, 0, getIconWidth(), getIconHeight());
                    g2.setColor(SELECTED_CIRCLE_COLOR);
                    g2.fillOval((getIconWidth() - CIRCLE_SIZE) / 2, (getIconHeight() - CIRCLE_SIZE) / 2, CIRCLE_SIZE, CIRCLE_SIZE);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillOval(0, 0, getIconWidth(), getIconHeight());
                    g2.setColor(DEFAULT_COLOR);
                    g2.drawOval(0, 0, getIconWidth() - 1, getIconHeight() - 1);
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        });
    }

    /**
     * Overrides the paintComponent method to customize the painting behavior of the radio button.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Overrides the contains method to check if the specified coordinates are contained within the radio button's shape.
     * In addition to the default behavior, this method considers the circular shape of the radio button.
     *
     * @param x the x-coordinate to be tested
     * @param y the y-coordinate to be tested
     * @return true if the specified coordinates are contained within the radio button's shape, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        if (super.contains(x, y)) {
            return true;
        }
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);
        return (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) < radius * radius;
    }
}
