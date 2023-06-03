package beatalbumshop.componment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class MyNotification extends javax.swing.JDialog {
    public MyNotification(java.awt.Frame parent, boolean modal, int size, String message) {
        super(parent, modal);
        initComponents();
        
        setOpacity(0);
        
        MyLabel lbl = new MyLabel();
        lbl.setBorder(new EmptyBorder(10, 20, 10, 20));
        lbl.setRadius(40);
        
        lbl.setFont(lbl.getFont().deriveFont(14f));
        lbl.setText("<html><div style=\"width: " + size + "px; text-align: center\">" + message + "</div></html>");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(null);
        
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(lbl);
        
        pack();
        
        //rounded frame
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
        setSize(getWidth(), getHeight());
        
        setAlwaysOnTop(true);
        
        // set location 2/3 of the screen height and centered in width
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = dim.height / 2 - getSize().height / 2;
        int half = y / 2;
        setLocation(dim.width / 2 - getSize().width / 2, y + half);

        Thread tFadeIn = new Thread(rFadeIn);
        tFadeIn.start();
    }
    
    
    
    public MyNotification(java.awt.Frame parent, boolean modal, String message) {
        super(parent, modal);
        initComponents();
        
        setOpacity(0);
        
        MyLabel lbl = new MyLabel();
        lbl.setBorder(new EmptyBorder(10, 20, 10, 20));
        lbl.setRadius(40);
        
        lbl.setFont(lbl.getFont().deriveFont(14f));
        lbl.setText("<html><div style=\"width: 100px; text-align: center\">" + message + "</div></html>");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(null);
        
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(lbl);
        
        pack();
        
        //rounded frame
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
        setSize(getWidth(), getHeight());
        
        setAlwaysOnTop(true);
        
        // set location 2/3 of the screen height and centered in width
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = dim.height / 2 - getSize().height / 2;
        int half = y / 2;
        setLocation(dim.width / 2 - getSize().width / 2, y + half);

        Thread tFadeIn = new Thread(rFadeIn);
        tFadeIn.start();
    }
    
    
    
    public MyNotification(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        setOpacity(0);
        
        MyLabel lbl = new MyLabel();
        lbl.setBorder(new EmptyBorder(10, 20, 10, 20));
        lbl.setRadius(40);
        
        lbl.setFont(lbl.getFont().deriveFont(14f));
        lbl.setText("<html><div style=\"width: 100px; text-align: center\">This is  a notification</div></html>");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(null);
        
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(lbl);
        
        pack();
        
        //rounded frame
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
        setSize(getWidth(), getHeight());
        
        setAlwaysOnTop(true);
        
        // set location 2/3 of the screen height and centered in width
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = dim.height / 2 - getSize().height / 2;
        int half = y / 2;
        setLocation(dim.width / 2 - getSize().width / 2, y + half);

        Thread tFadeIn = new Thread(rFadeIn);
        tFadeIn.start();
    }
    
    
    
    Runnable rFadeIn = new Runnable() {
        double opacity = 0.0;

        @Override
        public void run() {
            while(opacity < 1) {
                try {
                    opacity += 0.1;
                    if (opacity > 1.0) {
                        // because opacity: 0.0 0.1 0.2 0.30000000000000004 0.4 0.5 0.6 0.7 0.7999999999999999 0.8999999999999999 0.9999999999999999
                        Thread.sleep(1000);
                        opacity = 1.0;
                        Thread tFadeOut = new Thread(rFadeOut);
                        tFadeOut.start();
                    }

                    Thread.sleep(30);
                    setOpacity(Float.parseFloat(opacity + ""));

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
    };
    
    
    
    Runnable rFadeOut = new Runnable() {
        double opacity = 1.0;

        @Override
        public void run() {
            while(opacity > 0) {
                try {
                    opacity -= 0.1;
                    if (opacity < 0) {
                        opacity = 0;
                        dispose();
                    }

                    Thread.sleep(30);
                    setOpacity(Float.parseFloat(opacity + ""));

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new beatalbumshop.componment.MyPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlMain.setBackground(new java.awt.Color(0, 0, 0));
        pnlMain.setForeground(new java.awt.Color(255, 255, 255));
        pnlMain.setRadius(40);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                MyNotification dialog = new MyNotification(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
