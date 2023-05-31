package beatalbumshop.dao;

import beatalbumshop.config.Account;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Customer;
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

public class CustomerDAOImpl implements CustomerDAO {
    String projectId = Account.FIREBASE_PROJECT_ID;

    @Override
    public boolean add(Customer customer) {
        Firestore db = Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("customers");

        try {
            // Add
            ApiFuture<WriteResult> result = colRef.document(customer.getID()+ "").set(customer);

            System.out.println("Update time : " + result.get().getUpdateTime());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    
    
    @Override
    public boolean updateByID(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteByID(String entityID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Customer getByID(String entityID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Customer> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    @Override
    public boolean checkExitByEmail(String email) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("customers");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> customers = querySnapshot.getDocuments();
            
            for (QueryDocumentSnapshot customer : customers) {
                if(customer.getString("email").equalsIgnoreCase(email)) {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return true;
    }
    
    
    
    @Override
    public Customer authentication(String email, String password) {
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        CollectionReference colRef = db.collection("customers");
        
        ApiFuture<QuerySnapshot> query = colRef.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> customers = querySnapshot.getDocuments();
            
            for (QueryDocumentSnapshot customer : customers) {
                if(customer.getString("email").equalsIgnoreCase(email) && customer.getString("password").equals(password)) {
                    DocumentSnapshot albumSnapshot = customer;
                    // Convert the document snapshot to a custom class
                    Customer albumData = albumSnapshot.toObject(Customer.class);
                    // Extract the lTrack field from the custom class
                    ArrayList<AddressBook> lAddressBook = albumData.getlAddressBook();
                    
                    return new Customer(
                        lAddressBook,
                        customer.getLong("id"),
                        customer.getString("email"),
                        customer.getString("password")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

}
