package beatalbumshop.model;

import java.util.HashMap;

public class AlbumSpotify extends Album {
    private String artist;
    private String releaseDate;
    private HashMap<String, String> mTracks;

    public AlbumSpotify() {
    }

    public AlbumSpotify(String artist, String releaseDate, HashMap<String, String> mTracks) {
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.mTracks = mTracks;
    }

    public AlbumSpotify(String artist, String releaseDate, HashMap<String, String> mTracks, long albumID, String albumName, Double albumPrice, long inStock) {
        super(albumID, albumName, albumPrice, inStock);
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.mTracks = mTracks;
    }

    public AlbumSpotify(String artist, String releaseDate, HashMap<String, String> mTracks, String albumName, Double albumPrice, long inStock) {
        super(albumName, albumPrice, inStock);
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.mTracks = mTracks;
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

    public HashMap<String, String> getmTracks() {
        return mTracks;
    }

    public void setmTracks(HashMap<String, String> mTracks) {
        this.mTracks = mTracks;
    }
    
}
