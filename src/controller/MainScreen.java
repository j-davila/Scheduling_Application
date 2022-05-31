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
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    @FXML
    private Tab weekTab;
    @FXML
    private Tab monthTab;

    @FXML
    private TableView<Appointment> monthTable;

    @FXML
    private TableColumn monthAppIdColumn;

    @FXML
    private TableColumn monthTitleColumn;

    @FXML
    private TableColumn monthDescrColumn;

    @FXML
    private TableColumn monthLocColumn;

    @FXML
    private TableColumn monthCntColumn;

    @FXML
    private TableColumn monthTypeColumn;

    @FXML
    private TableColumn monthStartColumn;

    @FXML
    private TableColumn monthEndColumn;

    @FXML
    private TableColumn monthCustIdColumn;

    @FXML
    private TableColumn monthUserIdColumn;

    @FXML
    private TableView<Appointment> weekTable;

    @FXML
    private TableColumn weekAppIdColumn;

    @FXML
    private TableColumn weekTitleColumn;

    @FXML
    private TableColumn weekDescrColumn;

    @FXML
    private TableColumn weekLocColumn;

    @FXML
    private TableColumn weekContactColumn;

    @FXML
    private TableColumn weekTypeColumn;

    @FXML
    private TableColumn weekStartColumn;

    @FXML
    private TableColumn weekEndColumn;

    @FXML
    private TableColumn weekCustIdColumn;

    @FXML
    private TableColumn weekUserIdColumn;

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


        Lists.customerResult();

        FilteredList<Customer> filterTest = new FilteredList<>(Lists.getAllCustomers(), b -> true);

        // The logic inside the predicate filters the content of the tableview using ID or part name.
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> filterTest.setPredicate(customer -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

//                try {
//                    if (Inventory.lookupPart(Integer.parseInt(newValue)).getId() == part.getId()) {
//                        return true;
//                    }
//                } catch (NumberFormatException | NullPointerException e) {
//                    return false;
//                } finally {
//                    try{
//                        String lowerNewValue = newValue.toLowerCase();
//                        if (Inventory.lookupPart(lowerNewValue).contains(part)){
//                            return true;
//                        }
//                    }catch (NullPointerException e){
//                        return false;
//                    }
//                }
            return false;
        }));

        SortedList<Customer> sortTest = new SortedList<>(filterTest);
        sortTest.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortTest);

        /*************************************************************************/

        Lists.appointmentResult();

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

            Customer testCustomer = customerTable.getSelectionModel().getSelectedItem();
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
            modifyCustomerScreen.getCustomer(testCustomer);

            stage.show();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {

        int id = customerTable.getSelectionModel().getSelectedItem().getId();

        CustomerQuery.delete(id);

        Lists.clearCustomerList();

        Lists.customerResult();

        FilteredList<Customer> filterTest = new FilteredList<>(Lists.getAllCustomers(), b -> true);

        SortedList<Customer> sortTest = new SortedList<>(filterTest);
        sortTest.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortTest);

    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 519, 533);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 519, 533);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void toWeekTab(Event event) {

        Lists.clearAppointmentList();

        Lists.appointmentResult();

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(weekTable.comparatorProperty());
        weekTable.setItems(sortTest2);

    }

    public void toMonthTab(Event event) {

        Lists.clearAppointmentList();

        Lists.appointmentResult();

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
        monthTable.setItems(sortTest2);
    }

    public void customerSelected(MouseEvent mouseEvent) throws SQLException {

        Lists.clearAscAppointmentList();

        int custId = customerTable.getSelectionModel().getSelectedItem().getId();

        Lists.ascAppointmentResults(custId);

        FilteredList<Appointment> filterTest2 = new FilteredList<>(Lists.getAllAscAppointments(), b -> true);

        SortedList<Appointment> sortTest2 = new SortedList<>(filterTest2);
        sortTest2.comparatorProperty().bind(monthTable.comparatorProperty());
        monthTable.setItems(sortTest2);

    }

    public void exitProgram(ActionEvent actionEvent) {

        JDBC.closeConnection();

        System.exit(0);
    }
}
