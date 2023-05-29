package beatalbumshop.utils;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The `ImageResizing` class provides utility methods for resizing images.
 */
public class ImageResizing {
    /**
     * Resizes the specified image file to the specified width and height.
     *
     * @param imagePath the path to the image file to resize
     * @param width the desired width of the resized image
     * @param height the desired height of the resized image
     * @return an `ImageIcon` object representing the resized image
     */
    public static ImageIcon ImageResizing(String imagePath, int width, int height) {
        Image image = new ImageIcon(imagePath).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
    
    /**
     * Resizes the specified image to the specified width and height.
     *
     * @param img the image to resize
     * @param width the desired width of the resized image
     * @param height the desired height of the resized image
     * @return an `ImageIcon` object representing the resized image
     */
    public static ImageIcon ImageResizing(Image img, int width, int height) {
        Image image = new ImageIcon(img).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }

}
