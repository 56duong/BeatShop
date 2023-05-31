package beatalbumshop.model;

import java.util.ArrayList;

public class Album {
    private String albumID;
    private String albumName;
    private String artist;
    private String releaseDate;
    private ArrayList<Track> lTrack;
    private Double price;
    private long inStock;
    private String image;

    public Album() {
    }

    public Album(String albumID, String albumName, String artist, String releaseDate, ArrayList<Track> lTrack, Double price, long inStock) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.lTrack = lTrack;
        this.price = price;
        this.inStock = inStock;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<Track> getlTrack() {
        return lTrack;
    }

    public void setlTrack(ArrayList<Track> lTrack) {
        this.lTrack = lTrack;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
