package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import utility.CustomerQuery;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        fldColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        ResultSet rs;

        try {

            // code example from https://stackoverflow.com/questions/1966836/resultset-to-list
            rs = CustomerQuery.getAllCustomers();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int fld = rs.getInt("Division_ID");

                Customer testCustomer = new Customer(id, name, address, zip, phone,fld);

                Lists.addCustomer(testCustomer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Customer> filterTest = new FilteredList<>(Lists.getAllCustomers(), b -> true);

        // The logic inside the predicate filters the content of the tableview using ID or part name.
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTest.setPredicate(part -> {
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
            });
        });

        SortedList<Customer> sortTest = new SortedList<>(filterTest);
        sortTest.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortTest);

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

    public void updateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
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

//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomerScreen.fxml")));
//        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root, 519, 469);
//        stage.setScene(scene);
//        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
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

    public void exitProgram(ActionEvent actionEvent) {

        JDBC.closeConnection();

        System.exit(0);
    }
}
