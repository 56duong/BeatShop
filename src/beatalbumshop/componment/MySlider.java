package beatalbumshop.componment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * A custom slider component with a modified UI appearance.
 */
public class MySlider extends JSlider {

    /**
     * Creates a new instance of the MySlider class.
     * Configures the slider with a transparent background, black foreground,
     * and a custom slider UI.
     */
    public MySlider() {
        setOpaque(false);
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);
        setUI(new JSliderUI(this));
    }

    
    
    /**
     * Custom slider UI class that overrides the painting methods for the thumb and track.
     */
    public class JSliderUI extends BasicSliderUI {

        /**
         * Creates a new instance of the JSliderUI class.
         *
         * @param slider the slider component associated with this UI
         */
        public JSliderUI(JSlider slider) {
            super(slider);
        }

        /**
         * Overrides the paintFocus method to disable painting the focus rectangle.
         *
         * @param grphcs the graphics context
         */
        @Override
        public void paintFocus(Graphics grphcs) {
        }

        /**
         * Overrides the getThumbSize method to specify the size of the thumb.
         *
         * @return the size of the thumb
         */
        @Override
        protected Dimension getThumbSize() {
            return new Dimension(14, 14);
        }

        /**
         * Overrides the paintThumb method to paint the thumb with a circular shape.
         *
         * @param grphcs the graphics context
         */
        @Override
        public void paintThumb(Graphics grphcs) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(slider.getForeground());
            g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        /**
         * Overrides the paintTrack method to paint the track with a rounded rectangle shape.
         *
         * @param grphcs the graphics context
         */
        @Override
        public void paintTrack(Graphics grphcs) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(slider.getBackground());
            if (slider.getOrientation() == JSlider.VERTICAL) {
                g2.fillRoundRect(slider.getWidth() / 2 - 2, 2, 4, slider.getHeight(), 1, 1);
            } else {
                g2.fillRoundRect(2, slider.getHeight() / 2 - 2, slider.getWidth() - 5, 4, 1, 1);
            }
        }
    }
}

