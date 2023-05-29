package beatalbumshop.dao;

import beatalbumshop.model.Album;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirebaseTestCustomObject {
    public static void main(String[] args) {
        String projectId = "beat-75a88";
        
//        Firestore db = (Firestore) Firebase.getFirestore(projectId);
//        CollectionReference colRef = db.collection("album");
        
        
        AlbumDAO a = new AlbumDAOImpl();
        
        
        ArrayList<Album> lAlbum = new ArrayList<>();
        lAlbum = (ArrayList<Album>) a.getAll();
        
        for(Album album : lAlbum) {
            System.out.println(album.getAlbumID() + " " + album.getAlbumName());
        }

//        if(a.add(new Album("d", 200.0, 2))) {
//            System.out.println("ok");
//        }
//        else {
//            System.out.println("sai");
//        }
//        // INSERT (If the document does not exist, it will be created. If the document does exist, its contents will be overwritten with the newly provided data, unless you specify that the data should be merged into the existing document)
//        for(int i = 1; i <= 10; i++) {
//            Album album = new Album("30", 300.0, 20);
//            int id = i; //AlbumID
//            ApiFuture<WriteResult> result = colRef.document(id + "").set(album);
//
//            try {
//                System.out.println("Update time : " + result.get().getUpdateTime());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
        
        
        
//        // READ
//        // asynchronously retrieve all users
//        ApiFuture<QuerySnapshot> query = db.collection("users").get();
//        try {
//            QuerySnapshot querySnapshot = query.get();
//            List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
//
//            for (QueryDocumentSnapshot user : users) {
//                System.out.println("UserID: " + user.getId());
//                System.out.println("Email: " + user.getString("email"));
//                System.out.println("Username: " + user.getString("username"));
//                System.out.println("Password: " + user.getString("password"));
//                System.out.println("LikedTrack: " + ((ArrayList<Integer>)user.get("likedTrack")));
//                System.out.println("DownloadTime: " + user.getLong("downloadTime"));
//                System.out.println("");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        
        
//        // UPDATE
//        // set Password: password9999 for UserID: 9
//        // Update an existing document
//        try {
//            DocumentReference docRef = colRef.document("9");
//
//            // (async) Update one field
//            ApiFuture<WriteResult> userUpdated = docRef.update("email", "duong123@gmail.com");
//            
//            WriteResult result = userUpdated.get();
//            System.out.println("Write result: " + result);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        
        
//        Firebase.closeFirestore(db);
        
    }
}
