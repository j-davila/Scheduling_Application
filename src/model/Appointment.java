package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

/**
 * Appointment class that creates appointment objects. These objects are used in the List class to construct objects from a result set.
 *
 * @author José L Dávila Montalvo
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Instant startDate;
    private Instant endDate;
    private Timestamp startDateTbl;
    private Timestamp endDateTbl;
    private int customerID;
    private int userId;
    private int contact;

    /**
     * Constructor for appointment objects. This is the main constructor.
     *
     * @param id Appointment id
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param startDate Start datetime of the appointment
     * @param endDate End datetime of the appointment
     * @param customerID Id of the customer
     * @param userId Id of the user
     * @param contact Id of the contact
     *
     * */
    public Appointment(int id, String title, String description, String location,String type, Instant startDate,
                       Instant endDate, int customerID, int userId, int contact){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.userId = userId;
        this.contact = contact;
    }

    public Appointment(int id, String title, String description, String location, String type, Timestamp startDateTbl,
                       Timestamp endDateTbl, int customerID, int userId, int contact){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTbl = startDateTbl;
        this.endDateTbl = endDateTbl;
        this.customerID = customerID;
        this.userId = userId;
        this.contact = contact;
    }

    /**
     * Constructor for appointment objects. This object is used to find upcoming appointments.
     *
     * @param id Appointment id
     * @param startDate Appointment start datetime
     * @param endDate Appointment and datetime
     * */
    public Appointment(int id, Instant startDate, Instant endDate){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for appointment objects. This object is used to find appointments attached to a specific contact.
     *
     * @param id Appointment id
     * @param title Appointment title
     * @param description Appointment description
     * @param type Appointment type
     * @param startDate Appointment start datetime
     * @param endDate Appointment end datetime
     * @param customerID Customer id
     * */
    public Appointment(int id, String title, String description,String type, Instant startDate, Instant endDate, int customerID){
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
    }

    /**
     * Getter method for appointment id
     *
     * @return returns appointment id
     * */
    public int getId() {
        return id;
    }

    /**
     * Setter method for appointment id. Sets appointment id
     *
     * @param id Appointment id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for appointment title
     *
     * @return returns appointment title
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for appointment title. Sets appointment title
     *
     * @param title Appointment title
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for appointment description
     *
     * @return returns appointment description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for appointment description. Sets appointment description
     *
     * @param description Appointment description
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for appointment location
     *
     * @return returns appointment location
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for appointment location. Sets appointment location
     *
     * @param location Appointment location
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter method for appointment start
     *
     * @return returns appointment start
     * */
    public Instant getStartDate() {
        return startDate;
    }

    /**
     * Setter method for appointment start. Sets appointment start
     *
     * @param startDate Appointment location
     * */
    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for appointment end
     *
     * @return returns appointment end
     * */
    public Instant getEndDate() {
        return endDate;
    }

    /**
     * Setter method for appointment end. Sets appointment end
     *
     * @param endDate Appointment location
     * */
    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter method for customer id
     *
     * @return returns customer id
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter method for customer id. Sets customer id
     *
     * @param customerID Id of customer
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter method for user id
     *
     * @return returns user id
     * */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for user id. Sets user id
     *
     * @param userId Id of user
     * */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter method for contact id
     *
     * @return returns contact id
     * */
    public int getContact() {
        return contact;
    }

    /**
     * Setter method for contact. Sets contact id
     *
     * @param contact Id of contact
     * */
    public void setContact(int contact) {
        this.contact = contact;
    }

    /**
     * Getter method for type
     *
     * @return returns type
     * */
    public String getType() {
        return type;
    }

    /**
     * Setter method for type. Sets type
     *
     * @param type Type of appointment
     * */
    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartDateTbl() {
        return startDateTbl;
    }

    public void setStartDateTbl(Timestamp startDateTbl) {
        this.startDateTbl = startDateTbl;
    }

    public Timestamp getEndDateTbl() {
        return endDateTbl;
    }

    public void setEndDateTbl(Timestamp endDateTbl) {
        this.endDateTbl = endDateTbl;
    }
}
