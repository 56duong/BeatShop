package beatalbumshop.componment;

import java.awt.*;
import javax.swing.*;

public class MyRadioButton extends JRadioButton {

    private static final Color DEFAULT_COLOR = new Color(215, 215, 215);
    private static final Color SELECTED_COLOR = new Color(238, 84, 102); //[238,84,102]
    private static final Color SELECTED_CIRCLE_COLOR = Color.WHITE;
    private static final int CIRCLE_SIZE = 6;

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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

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
