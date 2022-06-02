package controller;

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

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22,0);

        while(start.isBefore(end.plusSeconds(1))){
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);
            start = start.plusMinutes(30);
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

        Customer testCustomer = null;
        Contact testContact = null;
        User testUser = null;

        while(rs1.next()){

            testCustomer = new Customer(rs1.getInt("Customer_ID"), rs1.getString("Customer_Name"), rs1.getString("Address"),
                    rs1.getString("Address"), rs1.getString("Phone"), rs1.getInt("Division_ID"), Lists.getAllAscAppointments());
        }

        while(rs2.next()){

            testContact = new Contact(rs2.getInt("Contact_ID"), rs2.getString("Contact_Name"), rs2.getString("Email"));

        }

        while(rs3.next()){

            testUser = new User(rs3.getInt("User_ID"), rs3.getString("User_Name"));

        }

        customerCombo.setValue(testCustomer);
        contactCombo.setValue(testContact);
        userCombo.setValue(testUser);

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
            Timestamp lastUpdated;
            int customerId;
            int userId;
            int contactId;
            int appId;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(titleTxt.getText().isEmpty()){
                throw new NullPointerException("Name field is empty");
            }else{
                title = titleTxt.getText();
            }

            if (descriptionTxt.getText().isEmpty()) {
                throw new NullPointerException("Address field is empty");
            }else {
                description = descriptionTxt.getText();
            }

            if (locationTxt.getText().isEmpty()) {
                throw new NullPointerException("Zip field is empty");
            }else {
                location = locationTxt.getText();
            }

            if (typeTxt.getText().isEmpty()) {
                throw new NullPointerException("Phone number field is empty");
            }else {
                type = typeTxt.getText();
            }

            appId = Integer.parseInt(appIdTxt.getText());
            Timestamp updateDate = Timestamp.valueOf(LocalDateTime.now());

            date =  startCalendar.getValue();

            startTime = startTimeCombo.getValue();
            endTime = endTimeCombo.getValue();

            LocalDateTime appointmentStart = LocalDateTime.of(date, startTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(date, endTime);

            customerId = customerCombo.getSelectionModel().getSelectedItem().getId();
            contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
            userId = userCombo.getSelectionModel().getSelectedItem().getUserId();

            AppointmentQuery.update(title, description, location, type, Timestamp.valueOf(appointmentStart), Timestamp.valueOf(appointmentEnd), updateDate, MainScreen.currentUser,
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
