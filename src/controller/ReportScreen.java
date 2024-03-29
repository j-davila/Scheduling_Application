package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import utility.Lists;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class that shows the customer various reports. Shows the user the number of appointments per month and type.
 * Shows the user the number of customers per division. Shows the user the appointment schedule for all contacts.
 *
 * @author José L Dávila Montalvo
 * */
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
    private ComboBox<String> appMonthCombo;
    @FXML
    private ComboBox<String> appTypeCombo;
    @FXML
    private Label appTotalLbl;
    @FXML
    private ComboBox<Division> fldCombo;
    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private Label custTotalLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearAppointmentList();
        Lists.clearTypeList();
        Lists.clearCustomerList();

        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTbl"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTbl"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        Lists.setAllMonths();

        appMonthCombo.setItems(Lists.getAllMonths());
        appMonthCombo.setVisibleRowCount(5);
        appMonthCombo.getSelectionModel().selectFirst();

        appTypeCombo.setItems(Lists.getAllTypes());
        appTypeCombo.setVisibleRowCount(5);
        appTypeCombo.getSelectionModel().selectFirst();

        try {
            Lists.contactResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        contactCombo.setItems(Lists.getAllContacts());
        contactCombo.setVisibleRowCount(5);
        contactCombo.getSelectionModel().selectFirst();

        try {
            Lists.contactSchedule(contactCombo.getSelectionModel().getSelectedItem().getContactId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        contactCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> appointmentFilter.setPredicate(appointment -> {

            try {
                if (Lists.lookupContact(newValue).getContactId() == appointment.getContact()) {
                    return true;
                }
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
            return false;
        }));

        SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
        appointmentSort.comparatorProperty().bind(contactTable.comparatorProperty());
        contactTable.setItems(appointmentSort);

        try {
            int test1 = Lists.numberOfAppointments(appMonthCombo.getSelectionModel().getSelectedItem(),appTypeCombo.getSelectionModel().getSelectedItem());
            appTotalLbl.setText(String.valueOf(test1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Lists.countryResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        countryCombo.setItems(Lists.getAllCountries());
        countryCombo.setVisibleRowCount(5);
        countryCombo.getSelectionModel().selectFirst();

        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();

        try {
            Lists.divisionResultByCountry(selectedCountry.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        fldCombo.setItems(Lists.getAlldivisions());
        fldCombo.setVisibleRowCount(5);
        fldCombo.getSelectionModel().selectFirst();

        try {
            int numberOfCustomers = Lists.numberOfCustomers(fldCombo.getSelectionModel().getSelectedItem().getId());
            custTotalLbl.setText(String.valueOf(numberOfCustomers));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Updates tableview based on contact used
    public void changeContact(ActionEvent actionEvent) {
        Lists.clearAppointmentList();

        Contact test =  contactCombo.getSelectionModel().getSelectedItem();

        if(test != null){
            try {
                Lists.contactSchedule(test.getContactId());

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                contactCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> appointmentFilter.setPredicate(appointment -> {

                    try {
                        if (Objects.requireNonNull(Lists.lookupContact(newValue)).getContactId() == appointment.getContact()) {
                            return true;
                        }
                    } catch (NumberFormatException | NullPointerException e) {
                        return false;

                    }
                    return false;
                }));

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(contactTable.comparatorProperty());
                contactTable.setItems(appointmentSort);

            } catch (SQLException | NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Exits back to mainscreen
    public void exitScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1128, 793);
        stage.setScene(scene);
        stage.show();
    }

    // Updates the screen depending on month chosen
    public void changeMonth(ActionEvent actionEvent) {
        try {
            int numberOfAppointments = Lists.numberOfAppointments(appMonthCombo.getSelectionModel().getSelectedItem(),appTypeCombo.getSelectionModel().getSelectedItem());
            appTotalLbl.setText(String.valueOf(numberOfAppointments));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Updates the screen depending on appointment type chosen
    public void changeType(ActionEvent actionEvent) {
        try {
            int numberOfAppointments = Lists.numberOfAppointments(appMonthCombo.getSelectionModel().getSelectedItem(),appTypeCombo.getSelectionModel().getSelectedItem());
            appTotalLbl.setText(String.valueOf(numberOfAppointments));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Updates the screen depending on first-level division chosen
    public void changeFld(ActionEvent actionEvent) {
        Division testDivision = fldCombo.getSelectionModel().getSelectedItem();

        if(testDivision != null){
            try {
                int numberOfCustomers = Lists.numberOfCustomers(fldCombo.getSelectionModel().getSelectedItem().getId());
                custTotalLbl.setText(String.valueOf(numberOfCustomers));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Updates the screen depending on country chosen
    public void changeCountry(ActionEvent actionEvent) throws SQLException {
        Lists.clearDivisionList();

        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();

        Lists.divisionResultByCountry(selectedCountry.getId());

        fldCombo.setItems(Lists.getAlldivisions());
        fldCombo.setVisibleRowCount(5);
        fldCombo.getSelectionModel().selectFirst();
    }
}
