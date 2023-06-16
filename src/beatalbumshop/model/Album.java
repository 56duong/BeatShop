package beatalbumshop.model;

import java.util.ArrayList;

/**
 * The Album class represents a music album in the Beat Album Shop.
 */
public class Album {
    private String albumID;
    private String albumName;
    private String artist;
    private String releaseDate;
    private ArrayList<Track> lTrack;
    private Double price;
    private long inStock;
    private String image;

    /**
     * Default constructor for the Album class.
     */
    public Album() {
    }

    /**
     * Constructor for the Album class with album ID, album name, artist, release date, track list, price, and stock count parameters.
     *
     * @param albumID     the ID of the album
     * @param albumName   the name of the album
     * @param artist      the artist of the album
     * @param releaseDate the release date of the album
     * @param lTrack      the list of tracks in the album
     * @param price       the price of the album
     * @param inStock     the stock count of the album
     */
    public Album(String albumID, String albumName, String artist, String releaseDate, ArrayList<Track> lTrack, Double price, long inStock) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.lTrack = lTrack;
        this.price = price;
        this.inStock = inStock;
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
     * Returns the name of the album.
     *
     * @return the name of the album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Sets the name of the album.
     *
     * @param albumName the name to set
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Returns the artist of the album.
     *
     * @return the artist of the album
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist of the album.
     *
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Returns the release date of the album.
     *
     * @return the release date of the album
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the album.
     *
     * @param releaseDate the release date to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the list of tracks in the album.
     *
     * @return the list of tracks in the album
     */
    public ArrayList<Track> getlTrack() {
        return lTrack;
    }

    /**
     * Sets the list of tracks in the album.
     *
     * @param lTrack the list of tracks to set
     */
    public void setlTrack(ArrayList<Track> lTrack) {
        this.lTrack = lTrack;
    }

    /**
     * Returns the price of the album.
     *
     * @return the price of the album
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the album.
     *
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns the stock count of the album.
     *
     * @return the stock count of the album
     */
    public long getInStock() {
        return inStock;
    }

    /**
     * Sets the stock count of the album.
     *
     * @param inStock the stock count to set
     */
    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    /**
     * Returns the image of the album.
     *
     * @return the image of the album
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image of the album.
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

}
