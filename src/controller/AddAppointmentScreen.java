package controller;

import database.AppointmentQuery;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller class that allows the user to add an appointment. In this screen the user can add an appointment.
 *
 * @author José L Dávila Montalvo
 * */
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
    private ComboBox<String> typeCombo;

    @FXML
    private TextArea descriptionTxt;

    /**
     * Initializes add screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Clears lists so the wrong objects dont get loaded or lists dont get duplicated
        Lists.clearTypeList();
        Lists.clearCustomerList();
        Lists.clearAppointmentList();
        Lists.clearContactList();

        typeCombo.setItems(Lists.getAllTypes());
        typeCombo.setVisibleRowCount(5);
        typeCombo.getSelectionModel().selectFirst();

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

        LocalTime startToLocal = localStart.toLocalTime();


        // While loop used to populate time in the comboboxes. Modeled from Software 2 webinar
        while(startToLocal.isBefore(localEnd.toLocalTime().plusSeconds(1))){
            startTimeCombo.getItems().add(startToLocal);
            endTimeCombo.getItems().add(startToLocal);
            startToLocal = startToLocal.plusMinutes(30);
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

    // When the user presses the button, the information is saved to the database using the insert query.

    /**
     * Saves a new appointment
     *
     * @param actionEvent
     * @throws IOException
     */
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

            type = typeCombo.getSelectionModel().getSelectedItem();

            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime createDate = ZonedDateTime.now();

            ZonedDateTime appointmentStart = ZonedDateTime.of(startDate, startTime,localZone);
            ZonedDateTime appointmentEnd = ZonedDateTime.of(startDate, endTime, localZone);

            Lists.appTimeCompResult(Instant.from(appointmentStart), Instant.from(appointmentEnd));

            ObservableList<Appointment> allAppointments = Lists.getAllAppointments();

            if(allAppointments.isEmpty()){
                contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

                customerId = customerCombo.getSelectionModel().getSelectedItem().getId();

                userId = userCombo.getSelectionModel().getSelectedItem().getUserId();

                AppointmentQuery.insert(title, description, location, type,Instant.from(appointmentStart),
                        Instant.from(appointmentEnd),Instant.from(createDate), MainScreen.currentUser,Timestamp.from(Instant.from(createDate)),
                        MainScreen.currentUser, customerId, userId, contactId);

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1128, 793);
                stage.setScene(scene);
                stage.show();

            }else {
                throw new IllegalArgumentException("The appointment times conflict with an existing appointment.");
            }
        }catch(NullPointerException | IllegalArgumentException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    // Takes user back to the mainscreen

    /**
     * Cancels adding an appointment
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancelAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1128, 793);
        stage.setScene(scene);
        stage.show();
    }
}
