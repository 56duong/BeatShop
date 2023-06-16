package beatalbumshop.model;

/**
 * The Item class represents an item in an order.
 */
public class Item {
    private String albumID;
    private int quantity;

    /**
     * Default constructor for the Item class.
     */
    public Item() {
    }

    /**
     * Constructor for the Item class with album ID and quantity parameters.
     *
     * @param albumID   the ID of the album
     * @param quantity  the quantity of the album
     */
    public Item(String albumID, int quantity) {
        this.albumID = albumID;
        this.quantity = quantity;
    }

    /**
     * Returns the ID of the album.
     *
     * @return the ID of the album
     */
    public String getAlbumID() {
        return albumID;
    }

    /**
     * Sets the ID of the album.
     *
     * @param albumID the ID to set
     */
    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    /**
     * Returns the quantity of the album.
     *
     * @return the quantity of the album
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the album.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
