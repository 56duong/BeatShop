package beatalbumshop.dao;

import beatalbumshop.config.Account;
import beatalbumshop.model.Album;
import beatalbumshop.model.Item;
import beatalbumshop.model.Order;
import beatalbumshop.model.Track;
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
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {
    String projectId = Account.FIREBASE_PROJECT_ID;

    @Override
    public boolean deleteByID(String orderID) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("orders");

        try {
            ApiFuture<WriteResult> writeResult = colRef.document(orderID).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    
    
    @Override
    public List<Order> getAll() {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("orders");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> orders = querySnapshot.getDocuments();

            List<Order> lOrder = new ArrayList<>();
            
            for (QueryDocumentSnapshot order : orders) {
                DocumentSnapshot orderSnapshot = order;
                // Convert the document snapshot to a custom class
                Order orderData = orderSnapshot.toObject(Order.class);
                // Extract the lTrack field from the custom class
                ArrayList<Item> lOrderItem = orderData.getlOrderItem();

                Order o = new Order(
                    orderData.getOrderID(),
                    orderData.getFullName(),
                    orderData.getAddress(),
                    orderData.getPhoneNumber(),
                    orderData.getMessage(),
                    orderData.getShipping(),
                    orderData.getStatus(),
                    lOrderItem,
                    orderData.getCustomerID(),
                    orderData.getStaffID()
                );

                lOrder.add(o);
            }
            
            return lOrder;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    
    
    @Override
    public boolean add(Order order) {
        Firestore db = Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("orders");

        try {
            // Add
            ApiFuture<WriteResult> result = colRef.document(order.getOrderID()+ "").set(order);

            System.out.println("Update time : " + result.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean updateByID(Order order) {
//        try {
//            Firestore db = (Firestore) Firebase.getFirestore(projectId);
//            CollectionReference colRef = db.collection("album");
//            DocumentReference docRef = colRef.document(album.getAlbumID() + "");
//
//            // (async) Update one field
//            ApiFuture<WriteResult> result = docRef.set(new Album(album.getAlbumID(), album.getAlbumName(), album.getAlbumPrice(), album.getInStock()));
//            
//            System.out.println("Update time : " + result.get().getUpdateTime());
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        return false;
    }

    
    
    @Override
    public Order getByID(String orderID) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        DocumentReference docRef = db.collection("orders").document(orderID);
        Order order;
        
        try {
            DocumentSnapshot documentSnapshot = docRef.get().get();
            
            if (documentSnapshot.exists()) {
                // Convert the document snapshot to a custom class
                Order orderData = documentSnapshot.toObject(Order.class);
                // Extract the lTrack field from the custom class
                ArrayList<Item> lOrderItem = orderData.getlOrderItem();
                
                order = new Order(
                        documentSnapshot.getLong("orderID"),
                        documentSnapshot.getString("fullName"),
                        documentSnapshot.getString("address"),
                        documentSnapshot.getString("phoneNumber"),
                        documentSnapshot.getString("message"),
                        documentSnapshot.getDouble("shipping"),
                        documentSnapshot.getLong("status"),
                        lOrderItem,
                        documentSnapshot.getLong("customerID"),
                        documentSnapshot.getLong("staffID")
                );
                return order;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}
