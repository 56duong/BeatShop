package beatalbumshop.utils;
    
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The ImageHelper class provides utility methods for handling and manipulating images.
 */
public class ImageHelper {
    
    /**
     * Converts an Icon to a BufferedImage.
     * 
     * @param icon the Icon to be converted
     * @return the converted BufferedImage
     */
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
    
    
    
    /**
     * Converts an Image to a BufferedImage.
     * 
     * @param image the Image to be converted
     * @return the converted BufferedImage
     */
    public static BufferedImage ImageToBufferedImage(Image image) {
        BufferedImage bimage = new BufferedImage(
                image.getWidth(null), 
                image.getHeight(null), 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        
        return bimage;
    }
    
    
    
    /**
     * Resizes an image from a file path to the specified dimensions.
     * 
     * @param imagePath the path of the image file
     * @param width the desired width of the resized image
     * @param height the desired height of the resized image
     * @return the resized ImageIcon
     */
    public static ImageIcon resizing(String imagePath, int width, int height) {
        Image image = new ImageIcon(imagePath).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
    
   
    
    /**
     * Resizes an image to the specified dimensions.
     * 
     * @param img the Image to be resized
     * @param width the desired width of the resized image
     * @param height the desired height of the resized image
     * @return the resized ImageIcon
     */
    public static ImageIcon resizing(Image img, int width, int height) {
        Image image = new ImageIcon(img).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
} 
