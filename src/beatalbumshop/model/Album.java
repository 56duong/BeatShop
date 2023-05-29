package beatalbumshop.model;

public class Album {
    private long albumID;
    private String albumName;
    private Double albumPrice;
    private long inStock;

    public Album() {
    }

    public Album(long albumID, String albumName, Double albumPrice, long inStock) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.albumPrice = albumPrice;
        this.inStock = inStock;
    }
    
    public Album(String albumName, Double albumPrice, long inStock) {
        this.albumName = albumName;
        this.albumPrice = albumPrice;
        this.inStock = inStock;
    }

    public long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Double getAlbumPrice() {
        return albumPrice;
    }

    public void setAlbumPrice(Double albumPrice) {
        this.albumPrice = albumPrice;
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }
}
