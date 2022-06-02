package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.Appointment;
import utility.AppointmentQuery;
import utility.CustomerQuery;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.JDBC;
import model.Customer;
import utility.Lists;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    @FXML
    private Tab weekTab;
    @FXML
    private Tab monthTab;

    @FXML
    private TableView<Appointment> monthTable;

    @FXML
    private TableColumn<Appointment, Integer> monthAppIdColumn;

    @FXML
    private TableColumn<Appointment, String> monthTitleColumn;

    @FXML
    private TableColumn<Appointment, String> monthDescrColumn;

    @FXML
    private TableColumn<Appointment, String> monthLocColumn;

    @FXML
    private TableColumn<Appointment, Integer> monthCntColumn;

    @FXML
    private TableColumn<Appointment, String> monthTypeColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> monthStartColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> monthEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> monthCustIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> monthUserIdColumn;

    @FXML
    private TableView<Appointment> weekTable;

    @FXML
    private TableColumn<Appointment, Integer> weekAppIdColumn;

    @FXML
    private TableColumn<Appointment, String> weekTitleColumn;

    @FXML
    private TableColumn<Appointment, String> weekDescrColumn;

    @FXML
    private TableColumn<Appointment, String> weekLocColumn;

    @FXML
    private TableColumn<Appointment, Integer> weekContactColumn;

    @FXML
    private TableColumn<Appointment, String> weekTypeColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> weekStartColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> weekEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> weekCustIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> weekUserIdColumn;

    @FXML
    private TextField customerSearch;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer,String> nameColumn;

    @FXML
    private TableColumn<Customer,String> addressColumn;

    @FXML
    private TableColumn<Customer, String> zipColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer,Integer> fldColumn;

    @FXML
    private TableView<Customer> customerTable;

    public static String currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearCustomerList();
        Lists.clearAppointmentList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        fldColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        monthAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthDescrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        monthEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        monthCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        monthUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        monthCntColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        weekAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        weekTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        weekDescrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weekLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        weekTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        weekEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        weekCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        weekUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        weekContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));


        try {
            Lists.customerResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Customer> filterTest = new FilteredList<>(Lists.getAllCustomers(), b -> true);

        // The logic inside the predicate filters the content of the tableview using ID or part name.
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> filterTest.setPredicate(customer -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

                try {
                    if (Lists.lookupCustomer(Integer.parseInt(newValue)).getId() == customer.getId()) {
                        return true;
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    return false;
                } finally {
                    try{
                        String lowerNewValue = newValue.toLowerCase();
                        if (Lists.lookupCustomer(lowerNewValue).contains(customer)){
                            return true;
                        }
                    }catch (NullPointerException e){
                        return false;
                    }
                }
            return false;
        }));

        SortedList<Customer> sortTest = new SortedList<>(filterTest);
        sortTest.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortTest);

        /*************************************************************************/

        try {
            Lists.appointmentResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
        monthTable.setItems(sortTest2);

    }

    public void getCurrentUser(String user){
        currentUser = user;
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomerScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 519, 469);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {

        try{

            int id = customerTable.getSelectionModel().getSelectedItem().getId();
            String name = customerTable.getSelectionModel().getSelectedItem().getName();
            String address = customerTable.getSelectionModel().getSelectedItem().getAddress();
            String zip = customerTable.getSelectionModel().getSelectedItem().getPostalCode();
            String phone = customerTable.getSelectionModel().getSelectedItem().getPhone();
            int fld = customerTable.getSelectionModel().getSelectedItem().getDivisionId();

            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
            Region root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            ModifyCustomerScreen modifyCustomerScreen = loader.getController();
            modifyCustomerScreen.setFields(id,name,address,zip,phone,fld);

            stage.show();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {

        try{
            Customer customer = customerTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("The selected customer will be deleted.");
            alert.setContentText("Would you like to delete this customer?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(customer.getAppointments().isEmpty()){
                    CustomerQuery.delete(customer.getId());
                    Lists.clearCustomerList();
                    Lists.customerResult();

                    FilteredList<Customer> filterTest = new FilteredList<>(Lists.getAllCustomers(), b -> true);

                    SortedList<Customer> sortTest = new SortedList<>(filterTest);
                    sortTest.comparatorProperty().bind(customerTable.comparatorProperty());
                    customerTable.setItems(sortTest);

                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setHeaderText("Deletion Error");
                    alert1.setContentText("Customer has appointments associated to them and cannot be deleted");

                    alert1.showAndWait();
                }
            } else {
                alert.close();
            }

        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("Please select a customer from the table");

            alert.showAndWait();
        }

    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 681, 533);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {

        if(monthTab.isSelected()){

            try{

                int appId = monthTable.getSelectionModel().getSelectedItem().getId();
                String title = monthTable.getSelectionModel().getSelectedItem().getTitle();
                String location = monthTable.getSelectionModel().getSelectedItem().getLocation();
                String type = monthTable.getSelectionModel().getSelectedItem().getType();
                String description = monthTable.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = monthTable.getSelectionModel().getSelectedItem().getStartDate().toLocalDateTime().toLocalDate();
                LocalTime startTime = monthTable.getSelectionModel().getSelectedItem().getStartDate().toLocalDateTime().toLocalTime();
                LocalTime endTime = monthTable.getSelectionModel().getSelectedItem().getEndDate().toLocalDateTime().toLocalTime();
                int customerId = monthTable.getSelectionModel().getSelectedItem().getCustomerID();
                int contactId = monthTable.getSelectionModel().getSelectedItem().getContact();
                int userId = monthTable.getSelectionModel().getSelectedItem().getUserId();

                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
                Region root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                ModifyAppointmentScreen modifyAppointmentScreen = loader.getController();
                modifyAppointmentScreen.setFields(appId, title, location, type, description, date, startTime, endTime, customerId, contactId, userId);

                stage.show();

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        } else{

            try{

                int appId = weekTable.getSelectionModel().getSelectedItem().getId();
                String title = weekTable.getSelectionModel().getSelectedItem().getTitle();
                String location = weekTable.getSelectionModel().getSelectedItem().getLocation();
                String type = weekTable.getSelectionModel().getSelectedItem().getType();
                String description = weekTable.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = weekTable.getSelectionModel().getSelectedItem().getStartDate().toLocalDateTime().toLocalDate();
                LocalTime startTime = weekTable.getSelectionModel().getSelectedItem().getStartDate().toLocalDateTime().toLocalTime();
                LocalTime endTime = weekTable.getSelectionModel().getSelectedItem().getEndDate().toLocalDateTime().toLocalTime();
                int customerId = weekTable.getSelectionModel().getSelectedItem().getCustomerID();
                int contactId = weekTable.getSelectionModel().getSelectedItem().getContact();
                int userId = weekTable.getSelectionModel().getSelectedItem().getUserId();

                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
                Region root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                ModifyAppointmentScreen modifyAppointmentScreen = loader.getController();
                modifyAppointmentScreen.setFields(appId, title, location, type, description, date, startTime, endTime, customerId, contactId, userId);

                stage.show();

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {

        Lists.clearAppointmentList();

        if(monthTab.isSelected()){

            int id = monthTable.getSelectionModel().getSelectedItem().getId();

            AppointmentQuery.delete(id);

            Lists.appointmentResult();

            FilteredList<Appointment> filterTest = new FilteredList<>(Lists.getAllAppointments(), b -> true);

            SortedList<Appointment> sortTest = new SortedList<>(filterTest);
            sortTest.comparatorProperty().bind(monthTable.comparatorProperty());
            monthTable.setItems(sortTest);

        } else {

            int id = weekTable.getSelectionModel().getSelectedItem().getId();

            AppointmentQuery.delete(id);

            Lists.appointmentResult();

            FilteredList<Appointment> filterTest = new FilteredList<>(Lists.getAllAppointments(), b -> true);

            SortedList<Appointment> sortTest = new SortedList<>(filterTest);
            sortTest.comparatorProperty().bind(weekTable.comparatorProperty());
            weekTable.setItems(sortTest);

        }

    }

    public void toWeekTab(Event event) throws SQLException {

        Lists.clearAppointmentList();

        Lists.appointmentResultWeek();

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(weekTable.comparatorProperty());
        weekTable.setItems(sortTest2);

    }

    public void toMonthTab(Event event) throws SQLException {

        Lists.clearAppointmentList();

        try {
            Lists.appointmentResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
        monthTable.setItems(sortTest2);
    }

    public void customerSelected(MouseEvent mouseEvent) throws SQLException {

        Lists.clearAscAppointmentList();
        Lists.clearAppointmentList();

        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if(monthTab.isSelected()){
            if(customer != null){
                Lists.ascAppointmentResults(customer.getId());

                customerTable.getSelectionModel().getSelectedItem().setAppointments(Lists.getAllAscAppointments());

                FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

                SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
                sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
                monthTable.setItems(sortTest2);
            }else{
                Lists.appointmentResult();

                FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
                sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
                monthTable.setItems(sortTest2);
            }
        } else{

            if(customer != null){
                Lists.ascAppointmentResults(customer.getId());

                customerTable.getSelectionModel().getSelectedItem().setAppointments(Lists.getAllAscAppointments());

                FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

                SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
                sortTest2.comparatorProperty().bind(weekTable.comparatorProperty());
                weekTable.setItems(sortTest2);
            }else{
                Lists.appointmentResult();

                FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
                sortTest2.comparatorProperty().bind(weekTable.comparatorProperty());
                weekTable.setItems(sortTest2);
            }

        }
    }

    public void exitProgram(ActionEvent actionEvent) {

        JDBC.closeConnection();

        System.exit(0);
    }
}
