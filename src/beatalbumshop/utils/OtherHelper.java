package beatalbumshop.utils;

import beatalbumshop.Main;
import beatalbumshop.MainAdmin;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;

public class OtherHelper {
    
    public static Main getMainFrame(Component c) {
        Main f = (Main) SwingUtilities.getWindowAncestor(c);
        return f;
    }
    
    
    
    public static MainAdmin getMainAdminFrame(Component c) {
        MainAdmin f = (MainAdmin) SwingUtilities.getWindowAncestor(c);
        return f;
    }

    
    
    public static String paymentOptionToString(long paymentOption) {
        String p = "";
        if(paymentOption == 0) p = "COD";
        
        return p;
    }
}
