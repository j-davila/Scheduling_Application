package utility;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.TimeZone;

/**
 * List class that creates and manipulates observable array lists. This class populates observable array lists manually or with result sets from the database.
 * Class also has methods to clear lists or to search the lists.
 *
 * @author José L Dávila Montalvo
 */
public class Lists {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<String> types = FXCollections.observableArrayList();
    private static ObservableList<String> months = FXCollections.observableArrayList();

    /**
     * Method populates and returns types. This method populates and returns appointments types
     *
     * @return Appointment types
     * */
    public static ObservableList<String> getAllTypes(){
        types.add("Planning Session");
        types.add("De-Briefing");
        types.add("Brainstorming");
        types.add("One-on-One");
        types.add("Team-Building");
        types.add("Check-In");
        types.add("Quartely Planning");
        types.add("Sprint Planning");
        types.add("Daily Scrum");
        types.add("Sprint Review");

        return types;
    }

    /**
     * Method populates a list with all months. Method populates a list with all the names of months in a year.
     *
     * */
    public static void setAllMonths(){
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
    }

    /**
     * Method clears the list of types. Method clears the appointment types list.
     * */
    public static void clearTypeList(){
        types.clear();
    }

    /**
     * Method clears the list of divisions. Method clears the first-level division list.
     * */
    public static void clearDivisionList(){
        allDivisions.clear();
    }

    /**
     * Method clears the list of contacts. Method clears the contact list.
     * */
    public static void clearContactList(){
        allContacts.clear();
    }

    /**
     * Method clears the list of customers. Method clears the customer list.
     * */
    public static void clearCustomerList(){
        allCustomers.clear();
    }

    /**
     * Method clears the list of associated appointments. Method clears the associated appointments list.
     * */
    public static void clearAscAppointmentList(){
        associatedAppointments.clear();
    }

    /**
     * Method clears the list of appointments. Method clears the appointment list.
     * */
    public static void clearAppointmentList(){
        allAppointments.clear();
    }

    /**
     * Method clears the list of countries. Method clears the country list.
     * */
    public static void clearCountryList(){
        allCountries.clear();
    }

    /**
     * Method gets the list of months. Method returns the list that contains all months.
     *
     * @return All months
     * */
    public static ObservableList<String> getAllMonths(){
        return months;
    }

    /**
     * Method gets the list of customers. Method returns the list that contains all customers.
     *
     * @return All customers
     * */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Method gets the list of appointments. Method returns the list that contains all appointments.
     *
     * @return All appointments
     * */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Method gets the list of associated appointments. Method returns the list that contains all associated appointments.
     *
     * @return All appointments
     * */
    public static ObservableList<Appointment> getAllAscAppointments() {
        return associatedAppointments;
    }

    /**
     * Method gets the list of contacts. Method returns the list that contains all contacts.
     *
     * @return All contacts
     * */
    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }

    /**
     * Method gets the list of countries. Method returns the list that contains all countries.
     *
     * @return All countries
     * */
    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    /**
     * Method gets the list of users. Method returns the list that contains all users.
     *
     * @return All users
     * */
    public static ObservableList<User> getAllusers() {
        return allUsers;
    }

    /**
     * Method gets the list of divisions. Method returns the list that contains all divisions.
     *
     * @return All divisions
     * */
    public static ObservableList<Division> getAlldivisions() {
        return allDivisions;
    }

    /**
     * Method adds a customer to a list. Method adds a customer to the customer list.
     *
     * @param customer Customer to be added to the list
     * */
    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    /**
     * Method adds a appointment to a list. Method adds a appointment to the appointment list.
     *
     * @param appointment Appointment to be added to the list
     * */
    public static void addAppointment(Appointment appointment){
        allAppointments.add(appointment);
    }

    /**
     * Method adds a associated appointment to a list. Method adds a appointment to the associated appointment list.
     *
     * @param appointment Appointment to be added to the list
     * */
    public static void addAscAppointment(Appointment appointment){
        associatedAppointments.add(appointment);
    }

    /**
     * Method adds a contact to a list. Method adds a contact to the contact list.
     *
     * @param contact Contact to be added to the list
     * */
    public static void addContact(Contact contact){
        allContacts.add(contact);
    }

    /**
     * Method adds a country to a list. Method adds a country to the country list.
     *
     * @param country Country to be added to the list
     * */
    public static void addCountry(Country country){
        allCountries.add(country);
    }

    /**
     * Method adds a user to a list. Method adds a user to the user list.
     *
     * @param user User to be added to the list
     * */
    public static void addUser(User user){
        allUsers.add(user);
    }

    /**
     * Method adds a division to a list. Method adds a first-level division to the division list.
     *
     * @param division Division to be added to the list
     * */
    public static void addDivision(Division division){
        allDivisions.add(division);
    }

    /**
     * Method searches the customer list for a specific customer. Method uses customer id to find the customer.
     *
     * @return Returns the customer if found or null if nothing is found.
     * */
    public static Customer lookupCustomer(int id) throws NumberFormatException, NullPointerException{
        ObservableList<Customer> tempCustomer = getAllCustomers();
        for(int i = 0; i < tempCustomer.size(); i++){
            Customer thisCustomer = tempCustomer.get(i);
            if(thisCustomer.getId() == id){
                return thisCustomer;
            }
        }
        return null;
    }

    /**
     * Method searches the customer list for a specific customer. Method uses customer name to find the customer.
     *
     * @return Returns a customer list with any customers found
     * */
    public static ObservableList<Customer> lookupCustomer(String customerName){
        ObservableList<Customer> tempAllCustomers = getAllCustomers();
        ObservableList<Customer> returnList = FXCollections.observableArrayList();
        for(Customer thisCustomer: tempAllCustomers){
            if(thisCustomer.getName().toLowerCase(Locale.ROOT).contains(customerName)){
                returnList.add(thisCustomer);
            }
        }
        return FXCollections.observableArrayList(returnList);
    }

    /**
     * Method searches the contact list for a specific contact. Method uses contact id getter method to find the contact.
     *
     * @return Returns the contact if found or null if nothing is found.
     * */
    public static Contact lookupContact(Contact contact) throws NumberFormatException, NullPointerException{
        ObservableList<Contact> tempContact = getAllContacts();
        for(int i = 0; i < tempContact.size(); i++){
            Contact thisContact = tempContact.get(i);
            if(thisContact.getContactId() == contact.getContactId()){
                return thisContact;
            }
        }
        return null;
    }

    /**
     * Void method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains all appointments.
     * */
    public static void appointmentResultTable() throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.getAllAppointments();

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAppointment(appointment);
        }
    }

    /**
     * Void method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments for the current month.
     * */
    public static void appointmentResultMonth() throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.getMonthAppointments();

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAppointment(appointment);
        }
    }

    /**
     * Overloaded void method that adds an associated appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments for the current month for a specific customer.
     * */
    public static void appointmentResultMonth(int custId) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.getMonthAppointments(custId);

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAscAppointment(appointment);
        }
    }

    /**
     * Void method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments for the current week.
     * */
    public static void appointmentResultWeek() throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.getWeekAppointments();

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAppointment(appointment);
        }
    }

    /**
     * Overloaded void method that adds an associated appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments for the current week for a specific customer.
     * */
    public static void appointmentResultWeek(int custId) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.getWeekAppointments(custId);

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAscAppointment(appointment);
        }
    }

    /**
     * Method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments associated to a customer.
     *
     * @param custId Id of customer
     * */
    public static void ascAppointmentResults(int custId) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.relatedAppointments(custId);

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

            Appointment appointment = new Appointment(id,title,description,location,type, start,end,
                    customerId,userId,contactId);

            addAscAppointment(appointment);
        }
    }

    /**
     * Method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments that overlap.
     *
     * @param startTime Start time of an appointment
     * @param endTime End time of an appointment
     * */
    public static void appTimeCompResult(Instant startTime, Instant endTime) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.timeOverlap(startTime, endTime);

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

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toInstant().atZone(utcZone);
            ZonedDateTime utcEnd = end.toInstant().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            Appointment appointment = new Appointment(id,title,description,location,type, Instant.from(localStartTime),Instant.from(localEndTime),
                    customerId,userId,contactId);

            addAppointment(appointment);
        }
    }

    /**
     * Method that returns an appointment. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains an upcoming appointment.
     *
     * @param instant Start time of an appointment
     *
     * @return Returns an appointment that will begin in 15 minutes of the saved start time.
     * */
    public static Appointment upcomingAppointment(Instant instant) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.relatedAppointment(instant);

        Appointment appointment = null;

        while (rs2.next()) {
            int id = rs2.getInt("Appointment_ID");
            Timestamp start = rs2.getTimestamp("Start");
            Timestamp end = rs2.getTimestamp("End");

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toInstant().atZone(utcZone);
            ZonedDateTime utcEnd = end.toInstant().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            appointment = new Appointment(id,Instant.from(localStartTime),Instant.from(localEndTime));
        }
        return appointment;
    }

    /**
     * Method that returns an integer. Method gets a result set from a query and returns a number.
     * The result set for this method contains the number of appointments for the indicated month and type.
     *
     * @param month Month the appointment is scheduled for
     * @param type Type of appointment
     *
     * @return Returns the number of appointments for the month and type
     * */
    public static Integer numberOfAppointments(String month, String type) throws SQLException {
        ResultSet rs2 = AppointmentQuery.numberOfAppointments(month, type);

        int count = 0;

        while (rs2.next()) {
            count = rs2.getInt("quantity");
        }
        return count;
    }

    /**
     * Void method that adds a customer to the list. Method gets a result set from a query and populates a customer object with the result set data.
     * The result set for this method contains all customers.
     * */
    public static void customerResult() throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs = CustomerQuery.getAllCustomers();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String zip = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int fld = rs.getInt("Division_ID");

            Customer customer = new Customer(id, name, address, zip, phone,fld,associatedAppointments);

            addCustomer(customer);
        }
    }

    /**
     * Method that returns an integer. Method gets a result set from a query and returns a number.
     * The result set for this method contains the number of customers for the indicated first-level division.
     *
     * @param fldID First-level division the customer resides in.
     *
     * @return Returns the number of customers that reside in the division.
     * */
    public static Integer numberOfCustomers(int fldID) throws SQLException {
        ResultSet rs2 = CustomerQuery.numberOfCustomers(fldID);

        int count = 0;

        while (rs2.next()) {
            count = rs2.getInt("quantity");
        }
        return count;
    }

    /**
     * Method that adds an appointment to the list. Method gets a result set from a query and populates an appointment object with the result set data.
     * The result set for this method contains appointments that match with a contact.
     *
     * @param contactId Id of contact
     * */
    public static void contactSchedule(int contactId) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        try (ResultSet rs2 = AppointmentQuery.contactAppointments(contactId)) {

            while (rs2.next()) {
                int id = rs2.getInt("Appointment_ID");
                String title = rs2.getString("Title");
                String description = rs2.getString("Description");
                String type = rs2.getString("Type");
                Timestamp start = rs2.getTimestamp("Start");
                Timestamp end = rs2.getTimestamp("End");
                int customerId = rs2.getInt("Customer_ID");

                ZoneId utcZone = ZoneId.of("UTC");

                ZonedDateTime utcStart = start.toInstant().atZone(utcZone);
                ZonedDateTime utcEnd = end.toInstant().atZone(utcZone);

                ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

                ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
                ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

                // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

                Appointment appointment = new Appointment(id, title, description, type, Instant.from(localStartTime),Instant.from(localEndTime), customerId);

                addAppointment(appointment);
            }
        }
    }

    /**
     * Void method that adds a contact to the list. Method gets a result set from a query and populates an contact object with the result set data.
     * The result set for this method contains all contacts.
     * */
    public static void contactResult() throws SQLException {
       ResultSet rs2 = ContactQuery.getAllContacts();

        while (rs2.next()) {

            int id = rs2.getInt("Contact_ID");
            String name = rs2.getString("Contact_Name");
            String email = rs2.getString("Email");

            Contact newContact = new Contact(id, name, email);

            addContact(newContact);
        }
    }

    /**
     * Void method that adds a country to the list. Method gets a result set from a query and populates an country object with the result set data.
     * The result set for this method contains all countries.
     * */
    public static void countryResult() throws SQLException {
        ResultSet rs2 = CountryQuery.getAllCountries();

        while (rs2.next()) {

            int id = rs2.getInt("Country_ID");
            String name = rs2.getString("Country");

            Country newCountry = new Country(id, name);

            addCountry(newCountry);
        }
    }

    /**
     * Void method that adds a user to the list. Method gets a result set from a query and populates a user object with the result set data.
     * The result set for this method contains all users.
     * */
    public static void userResult() throws SQLException {
        ResultSet rs2 = UserQuery.getAllUsers();

        while (rs2.next()) {

            int id = rs2.getInt("User_ID");
            String name = rs2.getString("User_Name");

            User newUser = new User(id, name);

            addUser(newUser);
        }
    }

    /**
     * Method that adds a division to the list. Method gets a result set from a query and populates a division object with the result set data.
     * The result set for this method contains divisions that match with a coumtry id.
     *
     * @param countryId Id of a country
     * */
    public static void divisionResultByCountry(int countryId) throws SQLException {
        ResultSet rs = FirstLevelDivQuery.getDivisionByCountry(countryId);

        while (rs.next()) {

            int divId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int id = rs.getInt("Country_ID");

            Division newDivision = new Division(divId, division, id);

            addDivision(newDivision);
        }
    }
}
