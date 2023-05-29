package beatalbumshop;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestFireBaseUploadImage {

    public static void main(String[] args) {
        try {
            InputStream serviceAccount = new FileInputStream("src/beatalbumshop/config/serviceAccountKey.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setStorageBucket("beat-75a88.appspot.com")
                        .build();

            FirebaseApp fireApp = FirebaseApp.initializeApp(options);

            StorageClient storageClient = StorageClient.getInstance(fireApp);
                    InputStream testFile = new FileInputStream("C:\\Users\\PC\\OneDrive\\Desktop\\9.png");
                    String blobString = "albums/" + "10.png";        

            storageClient.bucket().create(blobString, testFile , Bucket.BlobWriteOption.userProject("beat-75a88"));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
