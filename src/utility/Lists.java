package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

public class Lists {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

}
