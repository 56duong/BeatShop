package beatalbumshop.componment;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class MyPasswordField extends JPasswordField {
    private Shape shape;
    private int radius = 20;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public MyPasswordField() {
        setOpaque(false);
        setFont(new Font("Open Sans", 0, 16));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }
    
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         super.paintComponent(g);
    }
    
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
    
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
         }
         return shape.contains(x, y);
    }
}

