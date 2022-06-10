package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Customer class that creates customer objects. These objects are used in the List class and ModifyAppointments controller to construct objects from a result set.
 *
 * @author José L Dávila Montalvo
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private ObservableList<Appointment> appointments;

    /**
     * Constructor for customer objects. This is the main constructor.
     *
     * @param id Customer id
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param divisionId Id of the first-level division where the customr is located at
     * @param appointments Appointments associated with a customer object
     * */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId, ObservableList appointments){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.appointments = FXCollections.observableArrayList(appointments);
    }

    /**
     * Getter method for customer id
     *
     * @return returns customer id
     * */
    public int getId() {
        return id;
    }

    /**
     * Setter method for customer id. Sets customer id
     *
     * @param id Customer id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for customer name
     *
     * @return returns customer name
     * */
    public String getName() {
        return name;
    }

    /**
     * Setter method for customer name. Sets customer name
     *
     * @param name Customer name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for customer address
     *
     * @return returns customer address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for customer address. Sets customer address
     *
     * @param address Customer address
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for customer postal code
     *
     * @return returns customer postal code
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter method for customer postal code. Sets postal code
     *
     * @param postalCode Customer postal code
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter method for customer phone number
     *
     * @return returns customer phone number
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for customer phone number. Sets phone number
     *
     * @param phone Customer phone number
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for customer division id
     *
     * @return returns customer division id
     * */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter method for customer division id. Sets division id
     *
     * @param divisionId Customer division id
     * */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter method for customer appointments
     *
     * @return returns customer appointments
     * */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Setter method for customer appointments. Sets customer appointments
     *
     * @param appointments Customer appointments
     * */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Override method used to display a customer's name in a combobox.
     *
     * @return Name of customer
     * */
    @Override
    public String toString(){
        return(name);
    }
}
