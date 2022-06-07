package utility;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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

    public static ObservableList getAllTypes(){

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

    public static ObservableList setAllMonths(){

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

        return months;
    }

    public static void clearTypeList(){
        types.clear();
    }

    public static ObservableList<String> getAllMonths(){
        return months;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static void clearCustomerList(){

        allCustomers.clear();
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

    public static void addContact(Contact contact){
        allContacts.add(contact);
    }

    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }

    public static void addCountry(Country country){
        allCountries.add(country);
    }

    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    public static void clearCountryList(){
        allCountries.clear();
    }

    public static ObservableList<User> getAllusers() {
        return allUsers;
    }

    public static void addUser(User user){
        allUsers.add(user);
    }

    public static ObservableList<Division> getAlldivisions() {
        return allDivisions;
    }

    public static void addDivision(Division division){
        allDivisions.add(division);
    }

    public static void clearDivisionList(){
        allDivisions.clear();
    }

    public static void clearContactList(){
        allContacts.clear();
    }

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

    public static void appointmentResult() throws SQLException {

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

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            Appointment testAppointment = new Appointment(id,title,description,location,type, localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime(),customerId,userId,contactId);

            addAppointment(testAppointment);
       }
    }

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

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            Appointment testAppointment = new Appointment(id,title,description,location,type, localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime(),customerId,userId,contactId);

            addAppointment(testAppointment);
        }
    }

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

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            Appointment testAppointment = new Appointment(id,title,description,location,type, localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime(),customerId,userId,contactId);

            addAppointment(testAppointment);
        }
    }

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

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            Appointment testAppointment = new Appointment(id,title,description,location,type, localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime(),customerId,userId,contactId);

            addAscAppointment(testAppointment);
        }
    }

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

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            Appointment testAppointment = new Appointment(id,title,description,location,type, localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime(),customerId,userId,contactId);

            addAppointment(testAppointment);
        }
    }

    public static Appointment upcomingAppointment(Instant instant) throws SQLException {

        // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
        ResultSet rs2 = AppointmentQuery.relatedAppointment(instant);

        Appointment testAppointment = null;

        while (rs2.next()) {
            int id = rs2.getInt("Appointment_ID");
            Timestamp start = rs2.getTimestamp("Start");
            Timestamp end = rs2.getTimestamp("End");

            ZoneId utcZone = ZoneId.of("UTC");

            ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
            ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
            ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

            // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

            testAppointment = new Appointment(id,localStartTime.toLocalDateTime(),localEndTime.toLocalDateTime());
        }
        return testAppointment;
    }

    public static Integer numberOfAppointments(String month, String type) throws SQLException {

        ResultSet rs2 = AppointmentQuery.numberOfAppointments(month, type);

        int count = 0;

        while (rs2.next()) {
            count = rs2.getInt("quantity");
        }
        return count;
    }

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

            Customer testCustomer = new Customer(id, name, address, zip, phone,fld,associatedAppointments);

            addCustomer(testCustomer);
        }
    }

    public static Integer numberOfCustomers(int fldID) throws SQLException {

        ResultSet rs2 = CustomerQuery.numberOfCustomers(fldID);

        int count = 0;

        while (rs2.next()) {
            count = rs2.getInt("quantity");
        }
        return count;
    }

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

                ZonedDateTime utcStart = start.toLocalDateTime().atZone(utcZone);
                ZonedDateTime utcEnd = end.toLocalDateTime().atZone(utcZone);

                ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

                ZonedDateTime localStartTime = utcStart.withZoneSameInstant(localZone);
                ZonedDateTime localEndTime = utcEnd.withZoneSameInstant(localZone);

                // convert timestamp to instant, instant to ZoneDateTime, apply current zone id, then display in localdatetime

                Appointment testAppointment = new Appointment(id, title, description, type, localStartTime.toLocalDateTime(), localEndTime.toLocalDateTime(), customerId);

                addAppointment(testAppointment);
            }
        }
    }

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

    public static void countryResult() throws SQLException {

        ResultSet rs2 = CountryQuery.getAllCountries();

        while (rs2.next()) {

            int id = rs2.getInt("Country_ID");
            String name = rs2.getString("Country");

            Country newCountry = new Country(id, name);

            addCountry(newCountry);
        }
    }

    public static void userResult() throws SQLException {

        ResultSet rs2 = UserQuery.getAllUsers();

        while (rs2.next()) {

            int id = rs2.getInt("User_ID");
            String name = rs2.getString("User_Name");

            User newUser = new User(id, name);

            addUser(newUser);
        }
    }

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
