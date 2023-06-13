package beatalbumshop.utils;

import beatalbumshop.Main;
import beatalbumshop.MainAdmin;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
    
    
    
    /**
     * Exports the specified JPanel to a PNG image file.
     *
     * @param pnl the JPanel to be exported
     * @param fileName the file name of the exported image (without file extension)
     */
    public static void panelToImage(JPanel pnl, String fileName) {
        // Create an image of the panel
        BufferedImage image = new BufferedImage(pnl.getWidth(), pnl.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        pnl.print(g);

        // Save the image to a file
        try {
            JFileChooser save = new JFileChooser();
            save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            // disable the "All files" option
            save.setAcceptAllFileFilterUsed(false);

            int result = save.showSaveDialog(null); //gọi hộp thoại

            if(result == JFileChooser.APPROVE_OPTION){
                String path = save.getSelectedFile().getAbsolutePath();
                ImageIO.write(image, "PNG", new File(path + "/" + (fileName.isEmpty() ? "Image" : fileName) + ".png"));
                JOptionPane.showMessageDialog(null, "Export successful to " + (fileName.isEmpty() ? "Image" : fileName) + ".png");
            }   
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error exporting: " + ex.getMessage());
        }
    }
    
    
    
    /**
     * Exports the specified JTable to a PDF file.
     *
     * @param tbl the JTable to be exported
     * @param fileName the file name of the exported PDF (without file extension)
     */
    public static void tableToPDF(JTable tbl, String fileName) {
        try {
            boolean complete = tbl.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(fileName), new MessageFormat("Page - {0}"));
            if (complete) {
                JOptionPane.showMessageDialog(null, "Export successful");
            } else {
                JOptionPane.showMessageDialog(null, "Cancel");
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Error exporting: " + pe.getMessage());
        }
    }
}
