package beatalbumshop.model;

public class BagItem {
    private String albumID;
    private int quantity;

    public BagItem() {
    }

    public BagItem(String albumID, int quantity) {
        this.albumID = albumID;
        this.quantity = quantity;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
