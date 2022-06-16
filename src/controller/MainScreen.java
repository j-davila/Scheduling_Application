package controller;

import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.Appointment;
import database.AppointmentQuery;
import database.CustomerQuery;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import database.JDBC;
import model.Customer;
import utility.Lists;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * Controller class where the user manages customers and appointments. In this screen the user can add/modify/delete customers and appointments. User can also
 * access a report.
 *
 *@author José L Dávila Montalvo
 * */
public class MainScreen implements Initializable {

    @FXML
    private Tab allTab;

    @FXML
    private TableView<Appointment> allTable;

    @FXML
    private TableColumn<Appointment, Integer> allAppIdColumn;

    @FXML
    private TableColumn<Appointment, String> allTitleColumn;

    @FXML
    private TableColumn<Appointment, String> allDescrColumn;

    @FXML
    private TableColumn<Appointment, String> allLocColumn;

    @FXML
    private TableColumn<Appointment, Integer> allCntColumn;

    @FXML
    private TableColumn<Appointment, String> allTypeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> allStartColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> allEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> allCustIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> allUserIdColumn;

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
    private TableColumn<Appointment, LocalDateTime> monthStartColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthEndColumn;

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
    private TableColumn<Appointment, LocalDateTime> weekStartColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> weekEndColumn;

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

    /**
     * This method initializes the screen.
     * The first lambda expression makes the filtered list true which makes sure th entire list is displayed.
     * The second lambda involving the predicate makes the tableview change depending on what is entered in the textfield.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearCustomerList();
        Lists.clearAppointmentList();

        // sets table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        fldColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        allAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        allDescrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        allLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        allTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        allStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTbl"));
        allEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTbl"));
        allCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        allUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        allCntColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        monthAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthDescrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTbl"));
        monthEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTbl"));
        monthCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        monthUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        monthCntColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        weekAppIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        weekTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        weekDescrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weekLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        weekTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTbl"));
        weekEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTbl"));
        weekCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        weekUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        weekContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Populates customer table
        try {
            Lists.customerResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Customer> customerFilter = new FilteredList<>(Lists.getAllCustomers(), b -> true);

        // The logic inside the predicate filters the content of the tableview using id or customer name.
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> customerFilter.setPredicate(customer -> {
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

        SortedList<Customer> customerSort = new SortedList<>(customerFilter);
        customerSort.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(customerSort);

        // Checks for upcoming appointments
        try {

            Instant userLoginTime= Instant.now();

            Appointment upcomingAppointment = Lists.upcomingAppointment(userLoginTime);

            if (upcomingAppointment == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment");
                alert.setHeaderText("Upcoming Appointments");
                alert.setContentText("There are no upcoming appointments.");
                alert.show();

            }else{

                Alert appInfo = new Alert(Alert.AlertType.INFORMATION);
                appInfo.setTitle("Appointment");
                appInfo.setHeaderText("Upcoming Appointments");
                appInfo.setContentText("Appointment ID: " + upcomingAppointment.getId() + "\n" + "Appointment Time: " + upcomingAppointment.getStartDate());
                appInfo.show();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Populates appointment table
        try {
            Lists.appointmentResultTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
        appointmentSort.comparatorProperty().bind(allTable.comparatorProperty());
        allTable.setItems(appointmentSort);
    }

    /**
     * Method gets the name of the current user
     * This is the user that is currently logged in
     *
     * @param user  The user that is logged in to the application
     * */
    public void getCurrentUser(String user){
        currentUser = user;
    }

    // Sends the user to the add customr screen
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomerScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 519, 469);
        stage.setScene(scene);
        stage.show();
    }

    // Gathers the information from the selected object and sends it to the update screen. The user is taken to the update screen and
    // displays the information from the selected customer object.

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
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
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("Please select a customer from the table");

            alert.showAndWait();
        }
    }

    // Deletes the selected customer
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

                    FilteredList<Customer> customerFilter = new FilteredList<>(Lists.getAllCustomers(), b -> true);

                    SortedList<Customer> sortTest = new SortedList<>(customerFilter);
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

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Deletion");
        alert2.setHeaderText("Deletion Confirmation");
        alert2.setContentText("Customer has been deleted.");

        alert2.showAndWait();

    }


    // Takes the user to the add appointment screen
    public void addAppointment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 681, 533);
        stage.setScene(scene);
        stage.show();
    }

    // Gathers the information from the selected object and sends it to the update screen. The user is taken to the update screen and
    // displays the information from the selected appointment object.

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void updateAppointment(ActionEvent actionEvent) throws IOException {

        if(allTab.isSelected()){
            try{
                int appId = allTable.getSelectionModel().getSelectedItem().getId();
                String title = allTable.getSelectionModel().getSelectedItem().getTitle();
                String location = allTable.getSelectionModel().getSelectedItem().getLocation();
                String type = allTable.getSelectionModel().getSelectedItem().getType();
                String description = allTable.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = LocalDate.now();
                LocalTime startTime = allTable.getSelectionModel().getSelectedItem().getStartDateTbl().toLocalDateTime().toLocalTime();
                LocalTime endTime = allTable.getSelectionModel().getSelectedItem().getEndDateTbl().toLocalDateTime().toLocalTime();
                int customerId = allTable.getSelectionModel().getSelectedItem().getCustomerID();
                int contactId = allTable.getSelectionModel().getSelectedItem().getContact();
                int userId = allTable.getSelectionModel().getSelectedItem().getUserId();

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
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Selection");
                alert.setContentText("Please select an appointment from the table");

                alert.showAndWait();
            }
        } else if(monthTab.isSelected()){
            try{
                int appId = monthTable.getSelectionModel().getSelectedItem().getId();
                String title = monthTable.getSelectionModel().getSelectedItem().getTitle();
                String location = monthTable.getSelectionModel().getSelectedItem().getLocation();
                String type = monthTable.getSelectionModel().getSelectedItem().getType();
                String description = monthTable.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = LocalDate.now();
                LocalTime startTime = monthTable.getSelectionModel().getSelectedItem().getStartDateTbl().toLocalDateTime().toLocalTime();
                LocalTime endTime = monthTable.getSelectionModel().getSelectedItem().getEndDateTbl().toLocalDateTime().toLocalTime();
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
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Selection");
                alert.setContentText("Please select a customer from the table");

                alert.showAndWait();
            }
        }else{
            try{
                int appId = weekTable.getSelectionModel().getSelectedItem().getId();
                String title = weekTable.getSelectionModel().getSelectedItem().getTitle();
                String location = weekTable.getSelectionModel().getSelectedItem().getLocation();
                String type = weekTable.getSelectionModel().getSelectedItem().getType();
                String description = weekTable.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = LocalDate.now();
                LocalTime startTime = weekTable.getSelectionModel().getSelectedItem().getStartDateTbl().toLocalDateTime().toLocalTime();
                LocalTime endTime = weekTable.getSelectionModel().getSelectedItem().getEndDateTbl().toLocalDateTime().toLocalTime();
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
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Selection");
                alert.setContentText("Please select a customer from the table");

                alert.showAndWait();
            }
        }
    }

    // Deletes the selected appointment
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        try{
            if(allTab.isSelected()){
                Appointment appointment = allTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("The selected appointment will be deleted.");
                alert.setContentText("Would you like to delete this appointment?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    AppointmentQuery.delete(appointment.getId());
                    Lists.clearAppointmentList();
                    Lists.appointmentResultTable();

                    FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                    SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                    appointmentSort.comparatorProperty().bind(allTable.comparatorProperty());
                    allTable.setItems(appointmentSort);

                    Alert deletionInfo = new Alert(Alert.AlertType.INFORMATION);
                    deletionInfo.setTitle("Deletion Confirmation");
                    deletionInfo.setHeaderText("Appointment Details");
                    deletionInfo.setContentText("Appointment ID: " + appointment.getId() + "\n" + "Appointment Type: " + appointment.getType());
                    deletionInfo.show();

                } else {
                    alert.close();
                }
            } else if(monthTab.isSelected()){
                Appointment appointment = monthTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("The selected appointment will be deleted.");
                alert.setContentText("Would you like to delete this appointment?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    AppointmentQuery.delete(appointment.getId());
                    Lists.clearAppointmentList();
                    Lists.appointmentResultMonth();

                    FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                    SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                    appointmentSort.comparatorProperty().bind(monthTable.comparatorProperty());
                    monthTable.setItems(appointmentSort);

                    Alert deletionInfo = new Alert(Alert.AlertType.INFORMATION);
                    deletionInfo.setTitle("Deletion Confirmation");
                    deletionInfo.setHeaderText("Appointment Details");
                    deletionInfo.setContentText("Appointment ID: " + appointment.getId() + "\n" + "Appointment Type: " + appointment.getType());
                    deletionInfo.show();
                } else {
                    alert.close();
                }
            } else {
                Appointment appointment = weekTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("The selected appointment will be deleted.");
                alert.setContentText("Would you like to delete this appointment?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    AppointmentQuery.delete(appointment.getId());
                    Lists.clearAppointmentList();
                    Lists.appointmentResultWeek();

                    FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                    SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                    appointmentSort.comparatorProperty().bind(weekTable.comparatorProperty());
                    weekTable.setItems(appointmentSort);
                } else {
                    alert.close();
                }
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("Please select an appointment from the table");

            alert.showAndWait();
        }
    }

    // Switches the appointment tableview to the week tab
    public void toWeekTab(Event event) {
        Lists.clearAppointmentList();

        try {
            Lists.appointmentResultWeek();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
        appointmentSort.comparatorProperty().bind(weekTable.comparatorProperty());
        weekTable.setItems(appointmentSort);

    }

    // Switches the appointment tableview to the month tab
    public void toMonthTab(Event event) {
        Lists.clearAppointmentList();

        try {
            Lists.appointmentResultMonth();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
        appointmentSort.comparatorProperty().bind(monthTable.comparatorProperty());
        monthTable.setItems(appointmentSort);
    }

    // Switches the appointment tableview to the all tab
    public void toAllTab(Event event) {
        Lists.clearAppointmentList();

        try {
            Lists.appointmentResultTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
        appointmentSort.comparatorProperty().bind(allTable.comparatorProperty());
        allTable.setItems(appointmentSort);
    }

    // When the user selects a customer object, it will display all the appointments associated with that customer object.
    public void customerSelected(MouseEvent mouseEvent) throws SQLException {
        Lists.clearAscAppointmentList();
        Lists.clearAppointmentList();

        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if(allTab.isSelected()){
            if(customer != null){
                Lists.ascAppointmentResults(customer.getId());

                customerTable.getSelectionModel().getSelectedItem().setAppointments(Lists.getAllAscAppointments());

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(allTable.comparatorProperty());
                allTable.setItems(appointmentSort);
            }else{
                Lists.appointmentResultTable();

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(allTable.comparatorProperty());
                allTable.setItems(appointmentSort);
            }
        } else if(monthTab.isSelected()){
            if(customer != null){
                Lists.appointmentResultMonth(customer.getId());

                customerTable.getSelectionModel().getSelectedItem().setAppointments(Lists.getAllAscAppointments());

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(monthTable.comparatorProperty());
                monthTable.setItems(appointmentSort);
            }else{
                Lists.appointmentResultMonth();

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(monthTable.comparatorProperty());
                monthTable.setItems(appointmentSort);
            }
        } else{
            if(customer != null){
                Lists.appointmentResultWeek(customer.getId());

                customerTable.getSelectionModel().getSelectedItem().setAppointments(Lists.getAllAscAppointments());

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(weekTable.comparatorProperty());
                weekTable.setItems(appointmentSort);
            }else{
                Lists.appointmentResultWeek();

                FilteredList<Appointment> appointmentFilter = new FilteredList<>(Lists.getAllAppointments(), b -> true);

                SortedList<Appointment> appointmentSort = new SortedList<>(appointmentFilter);
                appointmentSort.comparatorProperty().bind(weekTable.comparatorProperty());
                weekTable.setItems(appointmentSort);
            }
        }
    }

    // Takes the user to the reports screen

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goToReports(ActionEvent actionEvent) throws IOException {
        Lists.clearContactList();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportsScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 910, 597);
        stage.setScene(scene);
        stage.show();
    }

    // Closes the connection to the database and closes the program.
    public void exitProgram(ActionEvent actionEvent) {
        JDBC.closeConnection();

        System.exit(0);
    }
}
