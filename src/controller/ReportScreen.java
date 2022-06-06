package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Customer;
import utility.Lists;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ReportScreen implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> descrColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerColumn;
    @FXML
    private TableView<Appointment> contactTable;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<Appointment> appMonthCombo;
    @FXML
    private ComboBox<Appointment> appTypeCombo;
    @FXML
    private Label appTotalLbl;
    @FXML
    private ComboBox<Appointment> custMonthCombo;
    @FXML
    private ComboBox<Customer> custCombo;
    @FXML
    private Label custTotalLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearAppointmentList();

        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));


        /**Remove selection from initilialization. Have combo load all contacts and get first selection. ***/

        int contactid = contactCombo.getSelectionModel().getSelectedItem().getContactId();

        try {
            Lists.contactSchedule(contactid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> filterTest = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest = new SortedList<>(filterTest);
        sortTest.comparatorProperty().bind(contactTable.comparatorProperty());
        contactTable.setItems(sortTest);
    }
}
