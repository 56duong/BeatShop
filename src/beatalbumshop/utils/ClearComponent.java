package beatalbumshop.utils;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
* The ClearComponent class provides a static method to clear the values of Swing components, including JLabels, JTextFields, JTexAreas, and ButtonGroups.
* This is useful when resetting a form or clearing input values.
* The method takes in a variable number of objects as parameters and iterates over each one, checking its class and performing the appropriate clear operation.
*/
public class ClearComponent {
    /**
    * Clears the values of Swing components passed in as parameters.
    * 
    * @param components the Swing components to be cleared
    */
    public static void clear(Object... components) {
        for (Object component : components) {
            if (component instanceof JLabel) {
                ((JLabel) component).setText("");
                ((JLabel) component).setIcon(null);
            }
            else if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
            else if (component instanceof JTextArea) {
                ((JTextArea) component).setText("");
            }
            else if (component instanceof ButtonGroup) {
                ((ButtonGroup) component).clearSelection();
            }
        }
    }
}
