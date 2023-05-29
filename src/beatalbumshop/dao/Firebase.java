package beatalbumshop.dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.InputStream;

public class Firebase {
    public static Firestore getFirestore(String projectId) {
        //Initialize on your own server
        try {
            // Use the application default credentials
            InputStream serviceAccount = new FileInputStream("src/beatalbumshop/config/serviceAccountKey.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            
            
            return FirestoreClient.getFirestore();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    
    
    public static void closeFirestore(Firestore firestore) {
        if (firestore != null) {
            try {
                firestore.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
