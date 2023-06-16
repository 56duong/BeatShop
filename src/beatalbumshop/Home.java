package beatalbumshop;

import beatalbumshop.componment.MyLabel;
import beatalbumshop.componment.MyScrollPane;
import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.model.Album;
import beatalbumshop.utils.ImageHelper;
import beatalbumshop.utils.ImageResizing;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 * Represents the Home panel of the Beat Album Shop application.
 */
public class Home extends javax.swing.JPanel {

    AlbumDAO albumDAO = new AlbumDAOImpl();
    ArrayList<Album> lAlbum = new ArrayList<>();
    int count = 0;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();

        // Configure panel layouts
        pnlListAlbum2.setLayout(new GridLayout(0, 4, 20, 20)); // 1028
        pnlListAlbum2.setBorder(new EmptyBorder(5, 20, 20, 20));
        
        pnlListAlbum4.setLayout(new GridLayout(0, 4, 20, 20)); // 1028
        pnlListAlbum4.setBorder(new EmptyBorder(5, 20, 20, 20));
        
        // Get all albums from the DAO
        lAlbum = (ArrayList<Album>) albumDAO.getAll();
        
        // Radom album
        Collections.shuffle(lAlbum);
        int count1 = 4;
        for (int i = 0; i < count1 && i < lAlbum.size(); i++) {
            Album album = lAlbum.get(i);
            if(album.getInStock() > 0) {
                showAlbumCard2(album);
            }
            else {
                count1++;
            }
        }
        
        // Newest albums
        sortByReleaseDate(lAlbum);
        int count2 = 4;
        for (int i = 0; i < count2 && i < lAlbum.size(); i++) {
            Album album = lAlbum.get(i);
            if(album.getInStock() > 0) {
                showAlbumCard(album);
            }
            else {
                count2++;
            }
        }
    }
    
    
    
    /**
     * Sorts the albums by release date in descending order.
     * @param lAlbum The list of albums to be sorted.
     */
    public static void sortByReleaseDate(ArrayList<Album> lAlbum) {
        Collections.sort(lAlbum, new Comparator<Album>() {
            @Override
            public int compare(Album album1, Album album2) {
                return album2.getReleaseDate().compareTo(album1.getReleaseDate());
            }
        });
    }
    
    
    
    /**
     * Displays an album card for newest albums.
     * @param album The album to be displayed.
     */
    public void showAlbumCard(Album album) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(Color.WHITE);
        pnl.setSize(new Dimension(227, 300));
        pnl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImage = new JLabel();
        try {
            URL url = new URL("https://firebasestorage.googleapis.com/v0/b/beat-75a88.appspot.com/o/albums%2F" + album.getAlbumID() + ".png?alt=media");
            Image image = ImageIO.read(url);
            lblImage.setIcon(ImageHelper.resizing(image, 227, 227));
        } catch (Exception ex) {
            lblImage.setIcon(null);
            ex.printStackTrace();
        }
        pnl.add(lblImage);

        MyLabel lblName = new MyLabel("<html>" + album.getAlbumName() + "</html>");
        lblName.setPreferredSize(new Dimension(227, 50));
        lblName.setFont(new Font("Open Sans", 0, 18));
        pnl.add(lblName);

        MyLabel lblArtist = new MyLabel("<html>" + album.getArtist() + "</html>");
        lblArtist.setFont(new Font("Open Sans", 0, 14));
        pnl.add(lblArtist);

//        MyLabel lblPrice = new MyLabel("$" + album.getPrice());
//        lblPrice.setFont(new Font("Open Sans", 0, 14));
//        pnl.add(lblPrice);
        pnl.setName(album.getAlbumID() + "");
        pnl.addMouseListener(showInfo);

        pnlListAlbum4.add(pnl);
    }
    
    
    
    MouseAdapter showInfo = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            JPanel s = (JPanel) e.getSource();
            String id = s.getName();

//            new AlbumDetail(id).setVisible(true);
            JPanel pnlTabContent = (JPanel) getParent();
            pnlTabContent.add(new AlbumDetail(id), "albumdetailpanel");
            
            Main.showTab("albumdetailpanel");
        }
    };
    
    
    
    /**
     * Displays an album card for random albums.
     * @param album The album to be displayed.
     */
    public void showAlbumCard2(Album album) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(Color.WHITE);
        pnl.setSize(new Dimension(227, 300));
        pnl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImage = new JLabel();
        try {
            URL url = new URL("https://firebasestorage.googleapis.com/v0/b/beat-75a88.appspot.com/o/albums%2F" + album.getAlbumID() + ".png?alt=media");
            Image image = ImageIO.read(url);
            lblImage.setIcon(ImageHelper.resizing(image, 227, 227));
        } catch (Exception ex) {
            lblImage.setIcon(null);
            ex.printStackTrace();
        }
        pnl.add(lblImage);

        MyLabel lblName = new MyLabel("<html>" + album.getAlbumName() + "</html>");
        lblName.setPreferredSize(new Dimension(227, 50));
        lblName.setFont(new Font("Open Sans", 0, 18));
        pnl.add(lblName);

        MyLabel lblArtist = new MyLabel("<html>" + album.getArtist() + "</html>");
        lblArtist.setFont(new Font("Open Sans", 0, 14));
        pnl.add(lblArtist);

//        MyLabel lblPrice = new MyLabel("$" + album.getPrice());
//        lblPrice.setFont(new Font("Open Sans", 0, 14));
//        pnl.add(lblPrice);
        pnl.setName(album.getAlbumID() + "");
        pnl.addMouseListener(showInfo);

        pnlListAlbum2.add(pnl);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myScrollPaneFull = new MyScrollPane();
        pnlListAlbumfull = new JPanel();
        lblRadom = new MyLabel();
        myScrollPane3 = new MyScrollPane();
        pnlListAlbum4 = new JPanel();
        lblNewAlbum = new MyLabel();
        myScrollPane4 = new MyScrollPane();
        pnlListAlbum2 = new JPanel();

        setBackground(new Color(255, 255, 255));
        setMaximumSize(new Dimension(1030, 658));
        setMinimumSize(new Dimension(1030, 658));
        setPreferredSize(new Dimension(1030, 658));
        setLayout(new BorderLayout());

        myScrollPaneFull.setBackground(null);
        myScrollPaneFull.setBorder(null);
        myScrollPaneFull.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlListAlbumfull.setBackground(new Color(255, 255, 255));
        pnlListAlbumfull.setPreferredSize(new Dimension(1030, 758));

        lblRadom.setText("Newest Albums");
        lblRadom.setToolTipText("");
        lblRadom.setFont(new Font("Open Sans", 1, 18)); // NOI18N

        myScrollPane3.setBackground(null);
        myScrollPane3.setBorder(null);
        myScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlListAlbum4.setBackground(new Color(255, 255, 255));
        pnlListAlbum4.setPreferredSize(new Dimension(1030, 302));

        GroupLayout pnlListAlbum4Layout = new GroupLayout(pnlListAlbum4);
        pnlListAlbum4.setLayout(pnlListAlbum4Layout);
        pnlListAlbum4Layout.setHorizontalGroup(pnlListAlbum4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        pnlListAlbum4Layout.setVerticalGroup(pnlListAlbum4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        myScrollPane3.setViewportView(pnlListAlbum4);

        lblNewAlbum.setText("Random Albums ");
        lblNewAlbum.setFont(new Font("Open Sans", 1, 18)); // NOI18N

        myScrollPane4.setBackground(null);
        myScrollPane4.setBorder(null);
        myScrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlListAlbum2.setBackground(new Color(255, 255, 255));
        pnlListAlbum2.setPreferredSize(new Dimension(1030, 302));

        GroupLayout pnlListAlbum2Layout = new GroupLayout(pnlListAlbum2);
        pnlListAlbum2.setLayout(pnlListAlbum2Layout);
        pnlListAlbum2Layout.setHorizontalGroup(pnlListAlbum2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        pnlListAlbum2Layout.setVerticalGroup(pnlListAlbum2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        myScrollPane4.setViewportView(pnlListAlbum2);

        GroupLayout pnlListAlbumfullLayout = new GroupLayout(pnlListAlbumfull);
        pnlListAlbumfull.setLayout(pnlListAlbumfullLayout);
        pnlListAlbumfullLayout.setHorizontalGroup(pnlListAlbumfullLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(myScrollPane3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(myScrollPane4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlListAlbumfullLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlListAlbumfullLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblRadom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNewAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlListAlbumfullLayout.setVerticalGroup(pnlListAlbumfullLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pnlListAlbumfullLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblRadom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myScrollPane3, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblNewAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(myScrollPane4, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE))
        );

        myScrollPaneFull.setViewportView(pnlListAlbumfull);

        add(myScrollPaneFull, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private MyLabel lblNewAlbum;
    private MyLabel lblRadom;
    private MyScrollPane myScrollPane3;
    private MyScrollPane myScrollPane4;
    private MyScrollPane myScrollPaneFull;
    private JPanel pnlListAlbum2;
    private JPanel pnlListAlbum4;
    private JPanel pnlListAlbumfull;
    // End of variables declaration//GEN-END:variables
}
