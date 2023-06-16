package beatalbumshop.componment;

import beatalbumshop.utils.ImageHelper;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * The MyImagePanel class is a custom JPanel component that displays an image.
 * It provides functionality to load and display images with specified dimensions.
 */
public class MyImagePanel extends JPanel {
    private Image img;

    /**
     * Creates a new instance of the MyImagePanel class with the specified image path and dimensions.
     *
     * @param img the path to the image file
     * @param w   the desired width of the panel
     * @param h   the desired height of the panel
     */
    public MyImagePanel(String img, int w, int h) {
//        this(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)).getImage(), w, h);
        this(ImageHelper.resizing(img, w, h).getImage(), w, h);
    }

    /**
     * Creates a new instance of the MyImagePanel class with the specified image and dimensions.
     *
     * @param img the image to be displayed
     * @param w   the desired width of the panel
     * @param h   the desired height of the panel
     */
    public MyImagePanel(Image img, int w, int h) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(this), img.getHeight(this));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    /**
     * Overrides the paintComponent method to paint the image on the panel.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    /**
     * Returns the current image displayed on the panel.
     *
     * @return the current image
     */
    public Image getImg() {
        return img;
    }

    /**
     * Sets the image to be displayed on the panel.
     * Resizes the image to fit the current dimensions of the panel.
     *
     * @param img the new image to be displayed
     */
    public void setImg(Image img) {
        this.img = ImageHelper.resizing(img, this.getWidth(), this.getHeight()).getImage();
    }
    
    /**
     * Sets the image to be displayed on the panel.
     * Resizes the image to fit the current dimensions of the panel.
     *
     * @param img the path to the new image file
     */
    public void setImg(String img) {
        this.img = ImageHelper.resizing(img, this.getWidth(), this.getHeight()).getImage();
    }
}
