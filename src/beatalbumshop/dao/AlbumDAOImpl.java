package beatalbumshop.dao;

import beatalbumshop.config.Account;
import beatalbumshop.model.Album;
import beatalbumshop.model.AlbumSpotify;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumDAOImpl implements AlbumDAO {
    String projectId = Account.FIREBASE_PROJECT_ID;

    @Override
    public boolean deleteByID(String albumID) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("album");

        try {
            ApiFuture<WriteResult> writeResult = colRef.document(albumID).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public List<Album> getAll() {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("album");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> albums = querySnapshot.getDocuments();

            List<Album> lAlbum = new ArrayList<>();
            
            for (QueryDocumentSnapshot album : albums) {
                Album a = new Album(
                    album.getLong("albumID"),
                    album.getString("albumName"),
                    album.getDouble("albumPrice"),
                    album.getLong("inStock")
                );
                
                lAlbum.add(a);
            }
            
            return lAlbum;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean add(Album album) {
        Firestore db = Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("album");

        try {
            // Add
            ApiFuture<WriteResult> result = colRef.document(album.getAlbumID() + "").set(album);

            System.out.println("Update time : " + result.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean updateByID(Album album) {
        try {
            Firestore db = (Firestore) Firebase.getFirestore(projectId);
            CollectionReference colRef = db.collection("album");
            DocumentReference docRef = colRef.document(album.getAlbumID() + "");

            // (async) Update one field
            ApiFuture<WriteResult> result = docRef.set(new Album(album.getAlbumID(), album.getAlbumName(), album.getAlbumPrice(), album.getInStock()));
            
            System.out.println("Update time : " + result.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public Album getByID(String albumID) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        DocumentReference docRef = db.collection("album").document(albumID);
        Album album;
        
        try {
            DocumentSnapshot documentSnapshot = docRef.get().get();
            
            if (documentSnapshot.exists()) {
                album = new Album(
                        documentSnapshot.getLong("albumID"),
                        documentSnapshot.getString("albumName"),
                        documentSnapshot.getDouble("albumPrice"),
                        documentSnapshot.getLong("inStock")
                );
                return album;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    
    
    @Override
    public AlbumSpotify getDetailByID(String albumID) {
        try {
            // Set up API credentials
            String clientId = Account.SPOTIFY_CLIENT_ID;
            String clientSecret = Account.SPOTIFY_CLIENT_SECRET;
            
            AlbumSpotify albumSpotify = new AlbumSpotify();
            HashMap<String, String> mTracks = new HashMap<>();
            
            Album a = getByID(albumID);

            // Set up search query
            String albumNameSearch = a.getAlbumName().replaceAll(" ", "");

            // Get access token
            String accessToken = SpotifyApi.getAccessToken(clientId, clientSecret);

            // Search for albums
            String searchEndpoint = "https://api.spotify.com/v1/search?type=album&q=" + albumNameSearch;
            JsonObject searchResponse = SpotifyApi.makeRequest(searchEndpoint, accessToken);
            JsonObject albums = searchResponse.getAsJsonObject("albums");
            JsonArray items = albums.getAsJsonArray("items");

            if (items.size() > 0) {
                JsonObject album = items.get(0).getAsJsonObject(); // Get the first album
                String albumId = album.get("id").getAsString();

                // Get album details
                String albumEndpoint = "https://api.spotify.com/v1/albums/" + albumId;
                JsonObject albumResponse = SpotifyApi.makeRequest(albumEndpoint, accessToken);

                // Extract album information
                String artistName = albumResponse.getAsJsonArray("artists")
                        .get(0).getAsJsonObject().get("name").getAsString();
                String releaseDate = albumResponse.get("release_date").getAsString();
                
                // Get album tracks
                String tracksEndpoint = "https://api.spotify.com/v1/albums/" + albumId + "/tracks";
                JsonObject tracksResponse = SpotifyApi.makeRequest(tracksEndpoint, accessToken);
                JsonArray tracks = tracksResponse.getAsJsonArray("items");

                // Process the tracks
                for (int i = 0; i < tracks.size(); i++) {
                    JsonObject track = tracks.get(i).getAsJsonObject();
                    String trackName = track.get("name").getAsString();
                    long trackDuration = Long.parseLong(track.get("duration_ms").getAsString());
                    long minutes = (trackDuration / 1000) / 60;
                    long seconds = (trackDuration / 1000) % 60;
                    
                    mTracks.put(trackName, minutes + ":" + seconds);
                }
                albumSpotify = new AlbumSpotify(artistName, releaseDate, mTracks, a.getAlbumID(), a.getAlbumName(), a.getAlbumPrice(), a.getInStock());
                
                return albumSpotify;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    
    
    @Override
    public boolean uploadImage(long id, InputStream image) {
        try {
            FirebaseApp fireApp;
            
            if(FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = new FileInputStream("src/beatalbumshop/config/serviceAccountKey.json");
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .setStorageBucket("beat-75a88.appspot.com")
                            .build();

                fireApp = FirebaseApp.initializeApp(options);
            }
            else {
                fireApp = FirebaseApp.getInstance();
            }

            StorageClient storageClient = StorageClient.getInstance(fireApp);
            String blobString = "albums/" + id + ".png";        

            storageClient.bucket().create(blobString, image, "image/png", Bucket.BlobWriteOption.userProject(projectId));
            
            return true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    
    @Override
    public boolean deleteImage(long id) {
        try {
            FirebaseApp fireApp;
            
            if(FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = new FileInputStream("src/beatalbumshop/config/serviceAccountKey.json");
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .setStorageBucket("beat-75a88.appspot.com")
                            .build();

                fireApp = FirebaseApp.initializeApp(options);
            }
            else {
                fireApp = FirebaseApp.getInstance();
            }

            StorageClient storageClient = StorageClient.getInstance(fireApp);

            String imageFileName = "albums/" + id + ".png";   
            Blob blob = storageClient.bucket().get(imageFileName);

            if (blob != null) {
                // Delete the image
                blob.delete();
                
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
}
