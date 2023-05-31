package beatalbumshop.dao;

import beatalbumshop.config.Account;
import beatalbumshop.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    String projectId = Account.FIREBASE_PROJECT_ID;
    
    @Override
    public User validateUser(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User getByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int updatePasswordByEmail(String password, String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(User user) {
        Firestore db = Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("users");

        try {
            // Add
            ApiFuture<WriteResult> result = colRef.document(user.getID()+ "").set(user);

            System.out.println("Update time : " + result.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean updateByID(User user) {
//        try {
//            Firestore db = (Firestore) Firebase.getFirestore(projectId);
//            CollectionReference colRef = db.collection("users");
//            DocumentReference docRef = colRef.document(user.getUserID()+ "");
//
//            // (async) Update one field
//            ApiFuture<WriteResult> result = docRef.set(new User(user.getUserID(), user.getEmail(), user.getPassword(), user.getDateCreated(), user.getRole()));
//            
//            System.out.println("Update time : " + result.get().getUpdateTime());
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        return false;
    }

    @Override
    public boolean deleteByID(String userID) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("users");

        try {
            ApiFuture<WriteResult> writeResult = colRef.document(userID).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public User getByID(String userID) {
//        Firestore db = (Firestore) Firebase.getFirestore(projectId);
//        DocumentReference docRef = db.collection("users").document(userID);
//        User user;
//        
//        try {
//            DocumentSnapshot documentSnapshot = docRef.get().get();
//            
//            if (documentSnapshot.exists()) {
//                user = new User(
//                        documentSnapshot.getLong("userID").intValue(),
//                        documentSnapshot.getString("email"),
//                        documentSnapshot.getString("password"),
//                        documentSnapshot.getString("dateCreated"),
//                        documentSnapshot.getLong("role").intValue()
//                );
//                return user;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        return null;
    }

    @Override
    public List<User> getAll() {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("users");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();

            List<User> lUser = new ArrayList<>();
            
            for (QueryDocumentSnapshot user : users) {
                User u = new User(
                    user.getLong("role"),
                    user.getLong("id"),
                    user.getString("email"),
                    user.getString("password"),
                    user.getString("dateCreated")
                );
                
                lUser.add(u);
            }
            
            return lUser;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean checkExitByEmail(String email) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("users");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
            
            for (QueryDocumentSnapshot user : users) {
                if(user.getString("email").equalsIgnoreCase(email)) {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

    
    
    @Override
    public User authentication(String email, String password) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("users");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
            
            for (QueryDocumentSnapshot user : users) {
                if(user.getString("email").equalsIgnoreCase(email) && user.getString("password").equals(password)) {
                    return new User(
                        user.getLong("role"),
                        user.getLong("id"), 
                        user.getString("email"),
                        user.getString("password"),
                        user.getString("dateCreated")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}
