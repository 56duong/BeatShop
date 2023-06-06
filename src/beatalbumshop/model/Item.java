package beatalbumshop.model;

public class Item {
    private String albumID;
    private int quantity;

    public Item() {
    }

    public Item(String albumID, int quantity) {
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
