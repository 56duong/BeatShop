package beatalbumshop.utils;
    
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

public class ImageFeatures {
    public static BufferedImage IconToBufferedImage(Icon icon) {
        BufferedImage bi = new BufferedImage(
            icon.getIconWidth(),
            icon.getIconHeight(),
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        g.setColor(Color.WHITE);
        g.dispose();
        
        return bi;
    }
}
