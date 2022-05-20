package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AppointmentQuery {

    //add, update, delete appointment
    public static int insert(String title, String description, String location, String type, Timestamp startDate, Timestamp endDate,
                             Timestamp createDate,String createdBy,Timestamp lastUpdated, String lastUpdatedBy, int customerId,
                             int userId, int contactId) throws SQLException {

        String query = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End,Create_Date, " +
                "Created_by,Last_Update,Last_Updated_By, Customer_ID,User_ID, Contact_ID) VALUES(?,?)";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,title);
        statement.setString(2,description);
        statement.setString(3,location);
        statement.setString(4,type);
        statement.setTimestamp(5,startDate);
        statement.setTimestamp(6, endDate);
        statement.setTimestamp(7, createDate);
        statement.setString(8, createdBy);
        statement.setTimestamp(9, lastUpdated);
        statement.setString(10, lastUpdatedBy);
        statement.setInt(11, customerId);
        statement.setInt(12, userId);
        statement.setInt(13, contactId);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
    }

    public static int update(int iD, String title, String description, String location, String type, Timestamp startDate, Timestamp endDate,
                             Timestamp createDate,String createdBy,Timestamp lastUpdated, String lastUpdatedBy, int customerId,
                             int userId, int contactId) throws SQLException {

        String query = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                "Create_Date = ?, Created_By = ?,Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,title);
        statement.setString(2,description);
        statement.setString(3,location);
        statement.setString(4,type);
        statement.setTimestamp(5,startDate);
        statement.setTimestamp(6, endDate);
        statement.setTimestamp(7, createDate);
        statement.setString(8, createdBy);
        statement.setTimestamp(9, lastUpdated);
        statement.setString(10, lastUpdatedBy);
        statement.setInt(11, customerId);
        statement.setInt(12, userId);
        statement.setInt(13, contactId);
        statement.setInt(14, iD);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;

    }

    public static int delete(int iD) throws SQLException {
        String query = "DELETE from client_schedule.customers WHERE Appoinment_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, iD);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated;
    }
}
