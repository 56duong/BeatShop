package beatalbumshop;

import beatalbumshop.componment.MyDialog;
import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.model.Album;
import beatalbumshop.model.Track;
import beatalbumshop.utils.ClearComponent;
import beatalbumshop.utils.ImageHelper;
import beatalbumshop.utils.TimeHelper;
import beatalbumshop.utils.Validator;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManagementAlbum extends javax.swing.JPanel {

    ArrayList<Album> lAlbum = new ArrayList<>(); //List Album
    ArrayList<Track> lTrack = new ArrayList<>(); //List Album
    DefaultTableModel albumModel;
    DefaultTableModel trackModel;
    AlbumDAO albumDAO = new AlbumDAOImpl();
    int index = -1;
    BufferedImage albumImage;
    
    public ManagementAlbum() {
        initComponents();
        
        //table
        //tao model
        albumModel = new DefaultTableModel();
        trackModel = new DefaultTableModel();

        // Set the table model to the tblAlbum table
        tblAlbum.setModel(albumModel);
        tblTrack.setModel(trackModel);
        
        //disable table editing
        tblAlbum.setDefaultEditor(Object.class, null); 
        tblTrack.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"ID", "Name", "Artist", "Release Date", "Price", "In Stock"};
        albumModel.setColumnIdentifiers(colNames);
        
        String [] colNames2 = {"ID", "Track Name", "Duration"};
        trackModel.setColumnIdentifiers(colNames2);

        //column width
        tblAlbum.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblAlbum.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblAlbum.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblAlbum.getColumnModel().getColumn(3).setPreferredWidth(130);
        tblAlbum.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblAlbum.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        tblTrack.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblTrack.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblTrack.getColumnModel().getColumn(2).setPreferredWidth(30);
        
        //table data
        fillToTable();
        
        tblAlbum.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM

    public void selectRow(int i) {
        if(i >= 0 && tblAlbum.getRowCount() > 0) {
            index = i;
            tblAlbum.setRowSelectionInterval(index, index);
            showDetail();
            //scroll toi dong duoc chon
            tblAlbum.scrollRectToVisible(new Rectangle(tblAlbum.getCellRect(index, 0, true)));
            albumImage = ImageHelper.IconToBufferedImage(lblImage.getIcon());
        }
    } 
    
    
    
    public void fillToTable() {
        lAlbum = (ArrayList<Album>) albumDAO.getAll();
        
        albumModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lAlbum != null) {
            for(Album album : lAlbum) {
                albumModel.addRow(new Object[] {album.getAlbumID(), album.getAlbumName(), album.getArtist(), album.getReleaseDate(), album.getPrice(), album.getInStock()});
            }
        }
    }
    
    
    
    public void fillToTrack(ArrayList<Track> list) {
        lTrack = list;
        
        trackModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lTrack != null) {
            for(Track track : lTrack) {
                String duration = TimeHelper.msToMinute(track.getDurationMS() + "");
                trackModel.addRow(new Object[] {track.getTrackID(), track.getTrackName(), duration});
            }
        }
    }
    
    
    
    public Integer findAlbumIndex(String albumID) {
        for(Album album : lAlbum) {
            if((album.getAlbumID()).equalsIgnoreCase(albumID)) {
                return lAlbum.indexOf(album);
            }
        }
        return -1;
    }
    
    
    
    public void showDetail() {
        Album album = new Album();
        
        //lay ID trong cot dau tien cua hang duoc chon
        String id = tblAlbum.getValueAt(tblAlbum.getSelectedRow(), 0).toString();
        
        //tim album co ID trong lAlbum
        album = lAlbum.get(findAlbumIndex(id));
        
        //do du lieu tu Album album len form
        lblID.setText(album.getAlbumID() + "");
        txtName.setText(album.getAlbumName());
        txtArtist.setText(album.getArtist());
        txtReleaseDate.setText(album.getReleaseDate());
        txtPrice.setText(album.getPrice()+ "");
        txtInStock.setText(album.getInStock() + "");
        fillToTrack(album.getlTrack());
        
//        lblImage.setIcon(ImageResizing.ImageResizing("src/beatalbumshop/resources/images/albums/" + album.getAlbumID() + ".png", lblImage.getWidth() - 1, lblImage.getHeight()));
        try {
            URL url = new URL("https://firebasestorage.googleapis.com/v0/b/beat-75a88.appspot.com/o/albums%2F" + album.getAlbumID() + ".png?alt=media");
            Image image = ImageIO.read(url);
            lblImage.setIcon(ImageHelper.resizing(image, lblImage.getWidth() - 1, lblImage.getHeight()));
        } catch(Exception ex) {
            lblImage.setIcon(null);
            ex.printStackTrace();
        }
    }
    
    
    
    public void clearForm() {
        index = -1;
        tblAlbum.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(lblImage, lblID, txtName, txtPrice, txtInStock);
        albumImage = null;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlbum = new beatalbumshop.componment.MyTable();
        lblID2 = new beatalbumshop.componment.MyLabel();
        lblName = new beatalbumshop.componment.MyLabel();
        txtName = new beatalbumshop.componment.MyTextField();
        lblPrice = new beatalbumshop.componment.MyLabel();
        txtPrice = new beatalbumshop.componment.MyTextField();
        lblInStock = new beatalbumshop.componment.MyLabel();
        txtInStock = new beatalbumshop.componment.MyTextField();
        btnDelete = new beatalbumshop.componment.MyButton();
        lblImage = new javax.swing.JLabel();
        lblID = new beatalbumshop.componment.MyLabel();
        btnSave = new beatalbumshop.componment.MyButton();
        lblArtist = new beatalbumshop.componment.MyLabel();
        txtArtist = new beatalbumshop.componment.MyTextField();
        lblName2 = new beatalbumshop.componment.MyLabel();
        txtReleaseDate = new beatalbumshop.componment.MyTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTrack = new beatalbumshop.componment.MyTable();
        txtAutoFill = new beatalbumshop.componment.MyButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));
        setPreferredSize(new java.awt.Dimension(1030, 658));

        tblAlbum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAlbumMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblAlbum);

        lblID2.setForeground(new java.awt.Color(80, 80, 80));
        lblID2.setText("ID");

        lblName.setForeground(new java.awt.Color(80, 80, 80));
        lblName.setText("Name");

        txtName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtName.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtName.setNextFocusableComponent(txtArtist);

        lblPrice.setForeground(new java.awt.Color(80, 80, 80));
        lblPrice.setText("Price");

        txtPrice.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtPrice.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtPrice.setNextFocusableComponent(txtInStock);

        lblInStock.setForeground(new java.awt.Color(80, 80, 80));
        lblInStock.setText("In Stock");

        txtInStock.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtInStock.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        btnDelete.setBackground(new java.awt.Color(215, 46, 46));
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 46, 46)));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblImageMousePressed(evt);
            }
        });

        lblID.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSave.setText("Save");
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblArtist.setForeground(new java.awt.Color(80, 80, 80));
        lblArtist.setText("Artist");

        txtArtist.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtArtist.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtArtist.setNextFocusableComponent(txtReleaseDate);

        lblName2.setForeground(new java.awt.Color(80, 80, 80));
        lblName2.setText("R.Date");

        txtReleaseDate.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtReleaseDate.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtReleaseDate.setNextFocusableComponent(txtPrice);

        jScrollPane2.setViewportView(tblTrack);

        txtAutoFill.setBackground(new java.awt.Color(0, 162, 47));
        txtAutoFill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 162, 47)));
        txtAutoFill.setForeground(new java.awt.Color(255, 255, 255));
        txtAutoFill.setText("Auto Fill");
        txtAutoFill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txtAutoFill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutoFillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(376, 376, 376))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtReleaseDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                    .addComponent(txtArtist, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(txtInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAutoFill, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAutoFill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReleaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblAlbumMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlbumMousePressed
        selectRow(tblAlbum.getSelectedRow());
    }//GEN-LAST:event_tblAlbumMousePressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String id = lblID.getText();
        String name = txtName.getText();
        String artist = txtArtist.getText();
        String releaseDate = txtReleaseDate.getText();
        String price = txtPrice.getText();
        String inStock = txtInStock.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

        if(tblTrack.getRowCount() == 0) errors.add("Không được để trống danh sách bài hát");
        errors.add(Validator.allowInteger((JTextField)txtInStock, "In Stock", inStock, false));
        if(Integer.parseInt(inStock) < -1) errors.add("In Stock phải >= -1\n(-1: Discontinued, 0: Out of stock)\n");
        errors.add(Validator.allowDouble((JTextField)txtPrice, "Price", price, false));
        errors.add((!Validator.isNotNull((JTextField)txtReleaseDate, releaseDate)) ? "Vui lòng nhập Release Date\n" : "");
        errors.add((!Validator.isNotNull((JTextField)txtArtist, artist)) ? "Vui lòng nhập Artist\n" : "");
        errors.add((!Validator.isNotNull((JTextField)txtName, name)) ? "Vui lòng nhập Name\n" : "");

        if(albumImage == null) errors.add("Vui lòng chọn ảnh\n");
        if(id == null) errors.add("Vui lòng nhập ID");
        
        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
          
        //add
        // save image to firebase
        if(albumImage != null) {
            try {
                //resize image to 200x200
                albumImage = ImageHelper.IconToBufferedImage(ImageHelper.resizing(albumImage, 200, 200));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(albumImage, "png", os);                          
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                albumDAO.uploadImage(id, is);
            } catch (Exception ex) {
                MyDialog.display(1, "Có lỗi xảy ra.");
                ex.printStackTrace();
                return;
            }
        }
        
        boolean result = albumDAO.add(new Album(id, name, artist, releaseDate, lTrack, Double.parseDouble(price), Integer.parseInt(inStock)));
        
        if(result) {
            //them thanh cong
            fillToTable();
            selectRow(findAlbumIndex(id));
        }
        else {
            //them that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void lblImageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMousePressed
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, "Open");
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                albumImage = ImageIO.read(selectedFile);
                lblImage.setIcon(ImageHelper.resizing(albumImage, lblImage.getWidth() - 1, lblImage.getHeight()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_lblImageMousePressed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //delete
        if(tblAlbum.getSelectedRow() < 0) {
            MyDialog.display(1, "Vui lòng chọn dòng cần xóa");
            return;
        }
        String id = tblAlbum.getValueAt(tblAlbum.getSelectedRow(), 0).toString();
        //delete image
//        File imageFile = new File("src/beatalbumshop/resources/images/albums/" + id + ".png");
//        if (imageFile.exists()) {
//            if (!imageFile.delete()) {
//                //delete that bai
//                MyDialog.display(1, "Có lỗi xảy ra.");
//                return;
//            }
//        }
//        boolean success = albumDAO.deleteImage(Long.parseLong(id));
//        if(!success) {
//            MyDialog.display(1, "Có lỗi xảy ra.");
//            return;
//        }
        
        boolean result = albumDAO.deleteByID(id);
        
        if(result) {
            //delete thanh cong
            fillToTable();
            
            //xoa 1 dong cuoi
            if(lAlbum.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lAlbum.size()) selectRow(index - 1);
            else selectRow(index);
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtAutoFillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutoFillActionPerformed
        Album album = albumDAO.getDetailByNameAndArtist(txtName.getText(), txtArtist.getText());
        if(album == null) {
            MyDialog.display(1, "Không tìm thấy album nào khớp với mô tả");
            txtName.requestFocus();
            return;
        }
        lblID.setText(album.getAlbumID());
        txtName.setText(album.getAlbumName());
        txtArtist.setText(album.getArtist());
        txtReleaseDate.setText(album.getReleaseDate());
        fillToTrack(album.getlTrack());
        
        try {
            //display image from url to jlabel
            URL url = new URL(album.getImage());
            Image image = ImageIO.read(url);
            lblImage.setIcon(ImageHelper.resizing(image, lblImage.getWidth() - 1, lblImage.getHeight()));
//            albumImage = album.getImage();
            albumImage = ImageHelper.ImageToBufferedImage(image);
        } catch(Exception ex) {
            lblImage.setIcon(null);
            ex.printStackTrace();
        }
        
        txtPrice.setText("");
        txtInStock.setText("");
        txtPrice.requestFocus();
        
        index = -1;
        tblAlbum.getSelectionModel().clearSelection();
    }//GEN-LAST:event_txtAutoFillActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnDelete;
    private beatalbumshop.componment.MyButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private beatalbumshop.componment.MyLabel lblArtist;
    private beatalbumshop.componment.MyLabel lblID;
    private beatalbumshop.componment.MyLabel lblID2;
    private javax.swing.JLabel lblImage;
    private beatalbumshop.componment.MyLabel lblInStock;
    private beatalbumshop.componment.MyLabel lblName;
    private beatalbumshop.componment.MyLabel lblName2;
    private beatalbumshop.componment.MyLabel lblPrice;
    private beatalbumshop.componment.MyTable tblAlbum;
    private beatalbumshop.componment.MyTable tblTrack;
    private beatalbumshop.componment.MyTextField txtArtist;
    private beatalbumshop.componment.MyButton txtAutoFill;
    private beatalbumshop.componment.MyTextField txtInStock;
    private beatalbumshop.componment.MyTextField txtName;
    private beatalbumshop.componment.MyTextField txtPrice;
    private beatalbumshop.componment.MyTextField txtReleaseDate;
    // End of variables declaration//GEN-END:variables
}
