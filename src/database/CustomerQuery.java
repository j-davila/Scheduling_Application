package database;

import java.sql.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Abstract database class for all queries related to customers. This class contains all customer queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class CustomerQuery {

    /**
     * This method inserts customer data into the database. Data collected from the user is entered in the INSERT query and added to the database.
     *
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param createDate Instant when customer was created
     * @param createdBy Username of the customer creator
     * @param lastUpdated Instant of the last update
     * @param lastUpdatedBy Username of who updated it last
     * @param divisionId Id of the first-level division where the customr is located at
     *
     * @return number of rows updated
     * */
    public static int insert(String name, String address, String postalCode, String phone, Instant createDate, String createdBy,
                             Timestamp lastUpdated, String lastUpdatedBy, int divisionId) throws SQLException {

        String query = "INSERT INTO client_schedule.customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update,Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,name);
        statement.setString(2,address);
        statement.setString(3,postalCode);
        statement.setString(4,phone);
        statement.setTimestamp(5, Timestamp.from(createDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setString(6, createdBy);
        statement.setTimestamp(7, lastUpdated, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setString(8, lastUpdatedBy);
        statement.setInt(9, divisionId);

        return statement.executeUpdate();
   }

    /**
     * This method updates customer data into the database. Data collected from the user is entered in the UPDATE query and updates the selected customer.
     *
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param lastUpdated Instant of the last update
     * @param lastUpdatedBy Username of who updated it last
     * @param divisionId Id of the first-level division where the customr is located at
     * @param id Id of the customer to update
     *
     * @return number of rows updated
     * */
   public static int update(String name, String address, String postalCode, String phone,
                            Timestamp lastUpdated, String lastUpdatedBy, int divisionId, int id) throws SQLException {

        String query = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone= ?, Last_Update = ?," +
                "Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

       PreparedStatement statement = JDBC.connection.prepareStatement(query);
       statement.setString(1,name);
       statement.setString(2,address);
       statement.setString(3,postalCode);
       statement.setString(4,phone);
       statement.setTimestamp(5, lastUpdated, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
       statement.setString(6, lastUpdatedBy);
       statement.setInt(7, divisionId);
       statement.setInt(8, id);

       return statement.executeUpdate();
   }

    /**
     * This method deletes an existing customer. The user will delete a selected customer.
     *
     * @param iD Id for the customer that will be deleted
     *
     * @return number of rows updated
     * */
    public static int delete(int iD) throws SQLException {
        String query = "DELETE from client_schedule.customers WHERE Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, iD);

        return statement.executeUpdate();
    }

    /**
     * Method that returns all customers. This method returns all the customers in the database.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getAllCustomers() throws SQLException {
        String query = "SELECT * FROM client_schedule.customers";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    /**
     * Method that returns related customers. This method will return all customers that have the same customer id.
     *
     * @param customerId Id of the customer
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getCustomer(int customerId) throws SQLException {
        String query = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, customerId);

        return statement.executeQuery();
    }

    /**
     * Method returns number of customers. The number of customers returned depends on the first-level division selected.
     *
     * @param fldId First-level division id
     *
     * @return returns a result set from the query
     * */
    public static ResultSet numberOfCustomers(int fldId ) throws SQLException {
        String query = "SELECT COUNT(DISTINCT(Customer_ID)) AS quantity FROM client_schedule.customers WHERE Division_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, fldId);

        return statement.executeQuery();
    }
}
