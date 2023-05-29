package beatalbumshop.dao;

import beatalbumshop.model.Album;
import beatalbumshop.model.AlbumSpotify;

public interface AlbumDAO extends DAO<Album> {
    public AlbumSpotify getDetailByID(String albumID);
}

