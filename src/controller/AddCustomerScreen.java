package controller;

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
import database.CustomerQuery;
import utility.Lists;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class that allows the user to add a customer. in this screen the user can add a customer.
 *
 * @author José L Dávila Montalvo
 * */
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

        firstlevelCombo.setItems(Lists.getAlldivisions());
        firstlevelCombo.setVisibleRowCount(5);
        firstlevelCombo.getSelectionModel().selectFirst();

    }

    // When the user presses the button, the information is saved to the database using the insert query.
    public void saveCustomer(ActionEvent actionEvent) throws IOException {
        try {
            String name;
            String address;
            String zip;
            String phoneNumber;

            // if-else statements validate user input and throw detailed exceptions specific to the invalid entry.
            if(nameTxt.getText().isEmpty()){
                throw new NullPointerException("Please enter a name in the Name field");
            }else{
                name = nameTxt.getText();
            }

            if (addressTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a address in the Address field");
            }else {
                address = addressTxt.getText();
            }

            if (zipTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a postal code in the Postal Code field");
            }else {
                zip = zipTxt.getText();
            }

            if (phoneTxt.getText().isEmpty()) {
                throw new NullPointerException("Please enter a phone number in the Phone field");
            }else {
                phoneNumber = phoneTxt.getText();
            }

            Division selectedDivision = firstlevelCombo.getSelectionModel().getSelectedItem();
            ZonedDateTime createDate = ZonedDateTime.now();

            CustomerQuery.insert(name, address, zip, phoneNumber, Instant.from(createDate), MainScreen.currentUser, Timestamp.from(Instant.from(createDate)),
                    MainScreen.currentUser, selectedDivision.getId());

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1128, 793);
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

    // Takes user back to the mainscreen
    public void cancelAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1128, 793);
        stage.setScene(scene);
        stage.show();
    }

    // If the country is changed it will update the division combobox with the appropriate divisions
    public void changeCountry(ActionEvent actionEvent) throws SQLException {

        Lists.clearDivisionList();

        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        Lists.divisionResultByCountry(selectedCountry.getId());

        firstlevelCombo.setItems(Lists.getAlldivisions());
        firstlevelCombo.setVisibleRowCount(5);
        firstlevelCombo.getSelectionModel().selectFirst();
    }
}
