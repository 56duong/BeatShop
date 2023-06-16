package beatalbumshop.componment;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 * A custom scroll pane with a Windows 11-style scroll bar and a modified layout behavior.
 */
public class MyScrollPane extends JScrollPane {

    /**
     * Creates a new instance of the MyScrollPane class.
     * Configures the scroll bars with the Windows 11-style UI, sets a custom layout,
     * and modifies the painting order of the components.
     */
    public MyScrollPane() {
        getVerticalScrollBar().setUI(new ScrollBarWin11UI());
        getHorizontalScrollBar().setUI(new ScrollBarWin11UI());
        setLayout(new ScrollLayout());
    }

    /**
     * Overrides the isOptimizedDrawingEnabled method to disable optimized drawing for the scroll pane.
     * This ensures that the custom scroll bar UI is properly rendered.
     *
     * @return false to disable optimized drawing for the scroll pane
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /**
     * Overrides the updateUI method to update the scroll pane's UI and modify the component painting order.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        EventQueue.invokeLater(() -> {
            setComponentZOrder(getVerticalScrollBar(), 0);
            setComponentZOrder(getHorizontalScrollBar(), 1);
            setComponentZOrder(getViewport(), 2);
            getVerticalScrollBar().setOpaque(false);
            getHorizontalScrollBar().setOpaque(false);
        });
    }

    
    
    /**
     * A custom layout manager for the scroll pane that modifies the layout behavior.
     */
    private class ScrollLayout extends ScrollPaneLayout {

        /**
         * Overrides the layoutContainer method to adjust the layout of the scroll pane's components.
         *
         * @param parent the parent container (scroll pane) to be laid out
         */
        @Override
        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);
            if (parent instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) parent;
                Rectangle rec = scroll.getViewport().getBounds();
                Insets insets = parent.getInsets();
                int rhHeight = 0;
                if (scroll.getColumnHeader() != null) {
                    Rectangle rh = scroll.getColumnHeader().getBounds();
                    rhHeight = rh.height;
                }
                rec.width = scroll.getBounds().width - (insets.left + insets.right);
                rec.height = scroll.getBounds().height - (insets.top + insets.bottom) - rhHeight;
                if (Objects.nonNull(viewport)) {
                    viewport.setBounds(rec);
                }
                if (!Objects.isNull(hsb)) {
                    Rectangle hrc = hsb.getBounds();
                    hrc.width = rec.width;
                    hsb.setBounds(hrc);
                }
            }
        }
    }
}
