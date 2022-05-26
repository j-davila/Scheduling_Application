package utility;

import javafx.collections.ObservableList;

import java.sql.*;

public abstract class CustomerQuery {

    //Add, update, delete customer

    public static int insert(String name, String address, int postalCode, String phone, Timestamp createDate, String createdBy,
                             Timestamp lastUpdated, String lastUpdatedBy, int divisionId) throws SQLException {

        String query = "INSERT INTO client_schedule.customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update,Last_Updated_By, Division_ID) VALUES(?,?)";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,name);
        statement.setString(2,address);
        statement.setInt(3,postalCode);
        statement.setString(4,phone);
        statement.setTimestamp(5,createDate);
        statement.setString(6, createdBy);
        statement.setTimestamp(7, lastUpdated);
        statement.setString(8, lastUpdatedBy);
        statement.setInt(9, divisionId);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
   }

   public static int update(int iD, String name, String address, int postalCode, String phone,
                            Timestamp lastUpdated, String lastUpdatedBy, int divisionId) throws SQLException {

        String query = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone= ?, Last_Update = ?," +
                "Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

       PreparedStatement statement = JDBC.connection.prepareStatement(query);
       statement.setString(1,name);
       statement.setString(2,address);
       statement.setInt(3,postalCode);
       statement.setString(4,phone);
       statement.setTimestamp(5, lastUpdated);
       statement.setString(6, lastUpdatedBy);
       statement.setInt(7, divisionId);
       statement.setInt(8, iD);

       int rowsUpdated = statement.executeUpdate();
       return rowsUpdated;

   }

    public static int delete(int iD) throws SQLException {
        String query = "DELETE from client_schedule.customers WHERE Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, iD);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
    }

    // query gathers all customer records from the database
    public static ResultSet getAllCustomers() throws SQLException {
        String query = "SELECT * FROM client_schedule.customers";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }
}
