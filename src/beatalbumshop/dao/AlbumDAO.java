package beatalbumshop.dao;
    
import beatalbumshop.model.Album;
import java.io.InputStream;

public interface AlbumDAO extends DAO<Album> {
    public Album getDetailByID(String albumID);
    public Album getDetailByNameAndArtist(String name, String artist);
    public boolean uploadImage(String id, InputStream image);
    public boolean deleteImage(long id);
}

