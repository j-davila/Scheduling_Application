package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserQuery {

    // user query is to go through the database and find a match for username and password. Lets user login if match is found

    public static ResultSet getUser(String userName, String password) throws SQLException {

        String query = "SELECT * FROM client_schedule.users Where User_Name LIKE BINARY ? AND Password LIKE BINARY ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    public static ResultSet getAllUsers() throws SQLException {
        String query = "SELECT * FROM client_schedule.users";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }

    public static ResultSet getUser(int userId) throws SQLException {
        String query = "SELECT * FROM client_schedule.users WHERE User_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, userId);

        return statement.executeQuery();
    }

}
