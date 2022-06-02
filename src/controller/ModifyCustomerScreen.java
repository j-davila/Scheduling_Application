package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utility.CountryQuery;
import utility.CustomerQuery;
import utility.FirstLevelDivQuery;
import utility.Lists;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lists.clearCountryList();

        try {
            Lists.countryResult();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        countryCombo.setItems(Lists.getAllCountries());
        countryCombo.setVisibleRowCount(5);

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

    public void saveCustomer(ActionEvent actionEvent) throws IOException {

        try {

            String name;
            String address;
            String zip;
            String phoneNumber;
            int divisionId;
            int id;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(nameTxt.getText().isEmpty()){
                throw new NullPointerException("Name field is empty");
            }else{
                name = nameTxt.getText();
            }

            if (addressTxt.getText().isEmpty()) {
                throw new NullPointerException("Address field is empty");
            }else {
                address = addressTxt.getText();
            }

            if (zipTxt.getText().isEmpty()) {
                throw new NullPointerException("Zip field is empty");
            }else {
                zip = zipTxt.getText();
            }

            if (numberTxt.getText().isEmpty()) {
                throw new NullPointerException("Phone number field is empty");
            }else {
                phoneNumber = numberTxt.getText();
            }

            id = Integer.parseInt(idTxt.getText());

            divisionId = firstlevelCombo.getSelectionModel().getSelectedItem().getId();

            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

            CustomerQuery.update(name, address, zip, phoneNumber, createDate, MainScreen.currentUser, divisionId, id);

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

    public void changeCountry(ActionEvent actionEvent) throws SQLException {

        Lists.clearDivisionList();

        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();

        Lists.divisionResultByCountry(selectedCountry.getId());

        firstlevelCombo.setItems(Lists.getAlldivisions());
        firstlevelCombo.setVisibleRowCount(5);
        firstlevelCombo.getSelectionModel().selectFirst();
    }
}
