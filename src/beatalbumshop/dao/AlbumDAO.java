package beatalbumshop.dao;

import beatalbumshop.model.Album;
import beatalbumshop.model.AlbumSpotify;
import java.io.InputStream;

public interface AlbumDAO extends DAO<Album> {
    public AlbumSpotify getDetailByID(String albumID);
    public boolean uploadImage(long id, InputStream image);
    public boolean deleteImage(long id);
}

