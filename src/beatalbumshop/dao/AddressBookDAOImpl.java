/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beatalbumshop.dao;

import beatalbumshop.config.Account;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author conro
 */
public class AddressBookDAOImpl implements AddressBookDAO {

    String projectId = Account.FIREBASE_PROJECT_ID;

    @Override
    public boolean add(AddressBook t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateByID(AddressBook t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteByID(String entityID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AddressBook getByID(String cusID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AddressBook> getAll() {
        Customer cus = (Customer) LoggedInUser.getCurrentUser();
        Firestore db = (Firestore) Firebase.getFirestore(projectId);
        // Tham chiếu đến collection "customers"
        CollectionReference customerCollection = db.collection("customers");
        // Lấy tất cả các document trong collection "customers" đưa vào list customersDocuments
        DocumentReference documentRef = customerCollection.document(String.valueOf(cus.getID()));
       
        ApiFuture<DocumentSnapshot> documentSnapshot = documentRef.get();
        List<AddressBook> lAddressBook = new ArrayList<>();
        try {
            DocumentSnapshot snapshot = documentSnapshot.get();
            if (snapshot.exists()) {

                // Lấy dữ liệu từ tài liệu
                Map<String, Object> addressbookData = snapshot.getData();
                if (addressbookData.containsKey("lAddressBook")) {
                    List<Map<String, Object>> lAddressBookData = (List<Map<String, Object>>) addressbookData.get("lAddressBook");

                    // Tạo đối tượng AddressBook từ dữ liệu lAddressBook và thêm vào danh sách lAddressBook
                    //List<AddressBook> lAddressBook = new ArrayList<>();
                    for (Map<String, Object> addressbook : lAddressBookData) {
                        Long addressBookID = cus.getID();
                        String addressType = (String) addressbook.get("addressType");
                        String fullName = (String) addressbook.get("fullName");
                        String address = (String) addressbook.get("address");
                        String phoneNumber = (String) addressbook.get("phoneNumber");

                        AddressBook a = new AddressBook(addressBookID, addressType, fullName, address, phoneNumber);
                        lAddressBook.add(a);
                        
                    }

                }
            }
            return lAddressBook;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
//        Firestore db = (Firestore) Firebase.getFirestore(projectId);
//        // Tham chiếu đến collection "customers"
//        CollectionReference customerCollection = db.collection("customers");
//        // Lấy tất cả các document trong collection "customers" đưa vào list customersDocuments
//        ApiFuture<QuerySnapshot> query = customerCollection.get();
//        try {
//            QuerySnapshot querySnapshot = query.get();
//            List<QueryDocumentSnapshot> customersDocuments = querySnapshot.getDocuments();// 1 2 3 4  
//
//            // Tạo list lưu trữ dữ liệu lAddressBook
//            List<AddressBook> lAddressBook = new ArrayList<>();
//            // Lặp qua từng tài liệu và lấy dữ liệu lAddressBook
//            for (QueryDocumentSnapshot document : customersDocuments) {
//                Map<String, Object> addressbookData = document.getData();
//
//                // Lấy dữ liệu lAddressBook từ tài liệu
//                if (addressbookData.containsKey("lAddressBook")) {
//                    List<Map<String, Object>> lAddressBookData = (List<Map<String, Object>>) addressbookData.get("lAddressBook");
//
//                    // Tạo đối tượng AddressBook từ dữ liệu lAddressBook và thêm vào danh sách lAddressBook
//                    for (Map<String, Object> addressbook : lAddressBookData) {
//                        Long addressBookID = Long.parseLong(document.getId());
//                        String addressType = (String) addressbook.get("addressType");
//                        String fullName = (String) addressbook.get("fullName");
//                        String address = (String) addressbook.get("address");
//                        String phoneNumber = (String) addressbook.get("phoneNumber");
//
//                        AddressBook a = new AddressBook(addressBookID, addressType, fullName, address, phoneNumber);
//                        lAddressBook.add(a);
//                    }
//                }
//
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
    }

}
