package controller;

import database.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
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

public class AddAppointmentScreen implements Initializable {

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

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextArea descriptionTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearCustomerList();
        Lists.clearAppointmentList();

        try {
            Lists.contactResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        contactCombo.setItems(Lists.getAllContacts());
        contactCombo.setVisibleRowCount(5);
        contactCombo.getSelectionModel().selectFirst();

        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22,0);

        ZonedDateTime businessHrsStart = ZonedDateTime.of(LocalDate.now(), start, estZone);
        ZonedDateTime businessHrsEnd = ZonedDateTime.of(LocalDate.now(), end, estZone);

        ZonedDateTime localStart = businessHrsStart.withZoneSameInstant(localZone);
        ZonedDateTime localEnd = businessHrsEnd.withZoneSameInstant(localZone);

        LocalTime testStart = localStart.toLocalTime();

        while(testStart.isBefore(localEnd.toLocalTime().plusSeconds(1))){
            startTimeCombo.getItems().add(testStart);
            endTimeCombo.getItems().add(testStart);
            testStart = testStart.plusMinutes(30);
        }

        startTimeCombo.getSelectionModel().selectFirst();
        endTimeCombo.getSelectionModel().selectFirst();

        try {
            Lists.customerResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerCombo.setItems(Lists.getAllCustomers());
        customerCombo.setVisibleRowCount(5);
        customerCombo.getSelectionModel().selectFirst();

        try {
            Lists.userResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        userCombo.setItems(Lists.getAllusers());
        userCombo.setVisibleRowCount(5);
        userCombo.getSelectionModel().selectFirst();

    }

    public void saveAppointment(ActionEvent actionEvent) throws IOException {

        try {

            String title;
            String location;
            String type;
            LocalDate startDate;
            LocalTime startTime;
            LocalTime endTime;
            int contactId;
            int customerId;
            int userId;
            String description;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(titleTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a title in the Title field.");
            }else{
               title = titleTxt.getText();
            }

            if(descriptionTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a description in the Description field.");
            }else{
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
                startDate =  startCalendar.getValue();
            }

            endTime = endTimeCombo.getValue();

            if(startTimeCombo.getValue().isAfter(endTime)){
                throw new IllegalArgumentException("Start time is after end time.");
            }else if(startTimeCombo.getValue().equals(endTime)){
                throw new IllegalArgumentException("Start time is equal to end time.");
            }else{
                startTime = startTimeCombo.getValue();
        }

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime createDate = ZonedDateTime.now();

            ZonedDateTime appointmentStart = ZonedDateTime.of(startDate, startTime,localZone);
            ZonedDateTime appointmentEnd = ZonedDateTime.of(startDate, endTime, localZone);

            Lists.appTimeCompResult(Timestamp.from(Instant.from(appointmentStart)), Timestamp.from(Instant.from(appointmentEnd)));

            ObservableList testAppoint = Lists.getAllAppointments();

            if(testAppoint.isEmpty()){
                contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

                customerId = customerCombo.getSelectionModel().getSelectedItem().getId();

                userId = userCombo.getSelectionModel().getSelectedItem().getUserId();

                AppointmentQuery.insert(title, description, location, type,Instant.from(appointmentStart),
                        Instant.from(appointmentEnd),Instant.from(createDate), MainScreen.currentUser,Timestamp.from(Instant.from(createDate)),
                        MainScreen.currentUser, customerId, userId, contactId);

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1156, 752);
                stage.setScene(scene);
                stage.show();

            }else {
                throw new IllegalArgumentException("The appointment times conflict with an existing appointment.");

            }

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
