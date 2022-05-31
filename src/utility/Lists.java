package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Lists {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static void clearCustomerList(){

        allCustomers.clear();
    }

    public static void customerResult(){

        ResultSet rs;

        try {

            // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
            rs = CustomerQuery.getAllCustomers();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int fld = rs.getInt("Division_ID");

               Customer testCustomer = new Customer(id, name, address, zip, phone,fld,associatedAppointments);

                Lists.addCustomer(testCustomer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static ObservableList<Appointment> getAllAscAppointments() {
        return associatedAppointments;
    }

    public static void addAppointment(Appointment appointment){
        allAppointments.add(appointment);
    }

    public static void addAscAppointment(Appointment appointment){
        associatedAppointments.add(appointment);
    }

    public static void clearAppointmentList(){

        allAppointments.clear();
    }

    public static void clearAscAppointmentList(){

        associatedAppointments.clear();
    }

    public static void appointmentResult(){

        ResultSet rs2;

        try {

            // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
            rs2 = AppointmentQuery.getAllAppointments();

            while (rs2.next()) {
                int id = rs2.getInt("Appointment_ID");
                String title = rs2.getString("Title");
                String description = rs2.getString("Description");
                String location = rs2.getString("Location");
                String type = rs2.getString("Type");
                Timestamp start = rs2.getTimestamp("Start");
                Timestamp end = rs2.getTimestamp("End");
                int customerId = rs2.getInt("Customer_ID");
                int userId = rs2.getInt("User_ID");
                int contactId = rs2.getInt("Contact_ID");

                Appointment testAppointment = new Appointment(id,title,description,location,type,start,end,customerId,userId,contactId);

                Lists.addAppointment(testAppointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void ascAppointmentResults(int custId) throws SQLException {

        ResultSet rs2;

        try {

            // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
            rs2 = AppointmentQuery.relatedAppointments(custId);

            while (rs2.next()) {
                int id = rs2.getInt("Appointment_ID");
                String title = rs2.getString("Title");
                String description = rs2.getString("Description");
                String location = rs2.getString("Location");
                String type = rs2.getString("Type");
                Timestamp start = rs2.getTimestamp("Start");
                Timestamp end = rs2.getTimestamp("End");
                int customerId = rs2.getInt("Customer_ID");
                int userId = rs2.getInt("User_ID");
                int contactId = rs2.getInt("Contact_ID");

                Appointment testAppointment = new Appointment(id,title,description,location,type,start,end,customerId,userId,contactId);

                Lists.addAscAppointment(testAppointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
