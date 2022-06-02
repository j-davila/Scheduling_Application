package controller;

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
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        try {
            Lists.contactResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        contactCombo.setItems(Lists.getAllContacts());
        contactCombo.setVisibleRowCount(5);
        contactCombo.getSelectionModel().selectFirst();

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22,0);

        while(start.isBefore(end.plusSeconds(1))){
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);
            start = start.plusMinutes(30);
        }
        startTimeCombo.getSelectionModel().select(LocalTime.of(8,0));
        endTimeCombo.getSelectionModel().select(LocalTime.of(8,0));

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
                throw new NullPointerException("Please enter a part name in the Name field");
            }else{
               title = titleTxt.getText();
            }

            if(descriptionTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a part name in the Name field");
            }else{
                description = descriptionTxt.getText();
            }

            if (locationTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a part name in the Name field");
            }else {
                location = locationTxt.getText();
            }

            if (typeTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a part name in the Name field");
            }else {
                type = typeTxt.getText();
            }

            startDate =  startCalendar.getValue();

            startTime = startTimeCombo.getValue();
            endTime = endTimeCombo.getValue();

            LocalDateTime appointmentStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(startDate, endTime);

            contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

            customerId = customerCombo.getSelectionModel().getSelectedItem().getId();

            userId = userCombo.getSelectionModel().getSelectedItem().getUserId();


            // put together startdate/startTime/ and endDate/endTime and turn them into timestamp

            AppointmentQuery.insert(title, description, location, type,Timestamp.valueOf(appointmentStart),
                    Timestamp.valueOf(appointmentEnd),Timestamp.valueOf(LocalDateTime.now()), MainScreen.currentUser, Timestamp.valueOf(LocalDateTime.now()),
                    MainScreen.currentUser, customerId, userId, contactId);

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
