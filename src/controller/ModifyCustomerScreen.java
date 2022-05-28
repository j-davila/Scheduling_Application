package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utility.CountryQuery;
import utility.FirstLevelDivQuery;
import utility.Lists;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyCustomerScreen implements Initializable {

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField numberTxt;

    @FXML
    private TextField zipTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ComboBox<Division> firstlevelCombo;

    @FXML
    private ComboBox<Country> countryCombo;

    public static Customer thisCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setFields(int id,String name, String address, String zip, String phone, int fld) throws SQLException {

        idTxt.setText(Integer.toString(id));
        nameTxt.setText(name);
        addressTxt.setText(address);
        zipTxt.setText(zip);
        numberTxt.setText(phone);

        ResultSet rs = FirstLevelDivQuery.getDivision(fld);

        Division testDivision = null;
        Country testCountry = null;

        while (rs.next()) {

            testDivision = new Division(rs.getInt("Division_ID"),rs.getString("Division"), rs.getInt("Country_ID"));
        }

        ResultSet rs2 = CountryQuery.getCountry(testDivision.getCountryId());

        while (rs2.next()) {

            testCountry = new Country(rs2.getInt("Country_ID"), rs2.getString("Country"));
        }

        firstlevelCombo.setValue(testDivision);
        countryCombo.setValue(testCountry);

    }

    public void getCustomer(Customer customer){
        thisCustomer = customer;
    }

    public void saveCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 450);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelAdd(ActionEvent actionEvent) throws IOException {

        Lists.clearCustomerList();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 450);
        stage.setScene(scene);
        stage.show();
    }

    public void changeCountry(ActionEvent actionEvent) {

        ObservableList<Division> divisiontest = FXCollections.observableArrayList();

        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();

        divisiontest.clear();

        try {
            ResultSet rs = FirstLevelDivQuery.getDivisionByCountry(selectedCountry.getId());

            while (rs.next()) {

                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Division newDivision = new Division(id, division, countryId);

                divisiontest.add(newDivision);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        firstlevelCombo.setItems(divisiontest);
        firstlevelCombo.setVisibleRowCount(5);
        firstlevelCombo.getSelectionModel().selectFirst();
    }
}
