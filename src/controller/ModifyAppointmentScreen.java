package controller;

import database.AppointmentQuery;
import database.ContactQuery;
import database.CustomerQuery;
import database.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;
import utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ModifyAppointmentScreen implements Initializable {

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private TextField appIdTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<User> userCombo;

    @FXML
    private DatePicker startCalendar;

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    @FXML
    private ComboBox<LocalTime> endTimeCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Lists.clearCustomerList();

        try {
            Lists.contactResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        contactCombo.setItems(Lists.getAllContacts());
        contactCombo.setVisibleRowCount(5);

        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22,0);

        ZonedDateTime businessHrsStart = ZonedDateTime.of(LocalDate.now(), start, estZone);
        ZonedDateTime businessHrsEnd = ZonedDateTime.of(LocalDate.now(), end, estZone);

        ZonedDateTime localStart = businessHrsStart.withZoneSameInstant(localZone);
        ZonedDateTime localEnd = businessHrsEnd.withZoneSameInstant(localZone);

        LocalTime startToLocal = localStart.toLocalTime();

        // While loop used to populate time in the comboboxes. Modeled from Software 2 webinar
        while(startToLocal .isBefore(localEnd.toLocalTime().plusSeconds(1))){
            startTimeCombo.getItems().add(startToLocal );
            endTimeCombo.getItems().add(startToLocal );
            startToLocal  = startToLocal.plusMinutes(30);
        }

        try {
            Lists.customerResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerCombo.setItems(Lists.getAllCustomers());
        customerCombo.setVisibleRowCount(5);

        try {
            Lists.userResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        userCombo.setItems(Lists.getAllusers());
        userCombo.setVisibleRowCount(5);
    }

    public void setFields(int id, String title, String location, String type, String description, LocalDate date, LocalTime start, LocalTime end, int customerId,
                          int contactId, int userId) throws SQLException {

        Lists.clearAscAppointmentList();

        appIdTxt.setText(Integer.toString(id));
        titleTxt.setText(title);
        locationTxt.setText(location);
        typeTxt.setText(type);
        descriptionTxt.setText(description);
        startCalendar.setValue(date);
        startTimeCombo.setValue(start);
        endTimeCombo.setValue(end);

        ResultSet rs1 = CustomerQuery.getCustomer(customerId);
        ResultSet rs2 = ContactQuery.getContact(contactId);
        ResultSet rs3 = UserQuery.getUser(userId);
        Lists.ascAppointmentResults(customerId);

        Customer customer = null;
        Contact contact = null;
        User user = null;

        while(rs1.next()){
            customer = new Customer(rs1.getInt("Customer_ID"), rs1.getString("Customer_Name"), rs1.getString("Address"),
                    rs1.getString("Address"), rs1.getString("Phone"), rs1.getInt("Division_ID"), Lists.getAllAscAppointments());
        }

        while(rs2.next()){
            contact = new Contact(rs2.getInt("Contact_ID"), rs2.getString("Contact_Name"), rs2.getString("Email"));
        }

        while(rs3.next()){
            user = new User(rs3.getInt("User_ID"), rs3.getString("User_Name"));
        }

        customerCombo.setValue(customer);
        contactCombo.setValue(contact);
        userCombo.setValue(user);
    }

    public void saveAppointment(ActionEvent actionEvent) throws IOException {
        try {
            String title;
            String description;
            String location;
            String type;
            LocalDate date;
            LocalTime startTime;
            LocalTime endTime;
            ZonedDateTime lastUpdated;
            int customerId;
            int userId;
            int contactId;
            int appId;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(titleTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a title in the Title field.");
            }else{
                title = titleTxt.getText();
            }

            if (descriptionTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a description in the Description field.");
            }else {
                description = descriptionTxt.getText();
            }

            if (locationTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a location in the Location field.");
            }else {
                location = locationTxt.getText();
            }

            if (typeTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a type in the Type field.");
            }else {
                type = typeTxt.getText();
            }

            if (startCalendar.getValue() == null) {
                throw new NullPointerException("Please select a date.");
            }else {
                date =  startCalendar.getValue();
            }

            endTime = endTimeCombo.getValue();

            if(startTimeCombo.getValue().isAfter(endTime)){
                throw new IllegalArgumentException("Start time is after end time.");
            }else if(startTimeCombo.getValue().equals(endTime)){
                throw new IllegalArgumentException("Start time is equal to end time.");
            }else{
                startTime = startTimeCombo.getValue();
            }

            appId = Integer.parseInt(appIdTxt.getText());
            lastUpdated = ZonedDateTime.now();
            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime appointmentStart = ZonedDateTime.of(date, startTime,localZone);
            ZonedDateTime appointmentEnd = ZonedDateTime.of(date, endTime, localZone);

            customerId = customerCombo.getSelectionModel().getSelectedItem().getId();
            contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
            userId = userCombo.getSelectionModel().getSelectedItem().getUserId();

            AppointmentQuery.update(title, description, location, type, Instant.from(appointmentStart), Instant.from(appointmentEnd), Timestamp.from(Instant.from(lastUpdated)), MainScreen.currentUser,
                    customerId, userId, contactId, appId);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1156, 752);
            stage.setScene(scene);
            stage.show();

        } catch(NullPointerException | IllegalArgumentException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    public void cancelAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1156, 752);
        stage.setScene(scene);
        stage.show();
    }
}
