package beatalbumshop.dao;
    
import beatalbumshop.model.Album;
import java.io.InputStream;

/**
 * The AlbumDAO interface provides methods for accessing and manipulating album data.
 */
public interface AlbumDAO extends DAO<Album> {
    /**
     * Retrieves the detailed information of an album based on its ID.
     *
     * @param albumID the ID of the album
     * @return the Album object representing the detailed information of the album,
     *         or null if the album is not found
     */
    public Album getDetailByID(String albumID);
    
    /**
     * Retrieves the detailed information of an album based on its name and artist.
     *
     * @param name   the name of the album
     * @param artist the artist of the album
     * @return the Album object representing the detailed information of the album,
     *         or null if the album is not found
     */
    public Album getDetailByNameAndArtist(String name, String artist);
    
    /**
     * Uploads the image for an album identified by its ID.
     *
     * @param id    the ID of the album
     * @param image the InputStream representing the image data
     * @return true if the image upload is successful, false otherwise
     */
    public boolean uploadImage(String id, InputStream image);
    
    /**
     * Deletes the image associated with an album identified by its ID.
     *
     * @param id the ID of the album
     * @return true if the image deletion is successful, false otherwise
     */
    public boolean deleteImage(String id);
}

