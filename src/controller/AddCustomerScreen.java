package controller;

import com.mysql.cj.protocol.Resultset;
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

public class AddCustomerScreen implements Initializable {

    @FXML
    private ComboBox<Division> firstlevelCombo;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField zipTxt;

    @FXML
    private TextField nameTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Division> divisiontest = FXCollections.observableArrayList();

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

    public void saveCustomer(ActionEvent actionEvent) throws IOException {

        try {

            String name;
            String address;
            String zip;
            String phoneNumber;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(nameTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a part name in the Name field");
            }else{
                name = nameTxt.getText();
            }

            if (addressTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a part name in the Name field");
            }else {
                address = addressTxt.getText();
            }

            if (zipTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a part name in the Name field");
            }else {
                zip = zipTxt.getText();
            }

            if (phoneTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a part name in the Name field");
            }else {
                phoneNumber = phoneTxt.getText();
            }

            Division selectedDivision = firstlevelCombo.getSelectionModel().getSelectedItem();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

            CustomerQuery.insert(name, address, zip, phoneNumber, createDate, MainScreen.currentUser, createDate, MainScreen.currentUser, selectedDivision.getId());

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
