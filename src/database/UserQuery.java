package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract database class for all queries related to users. This class contains all user queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class UserQuery {

    /**
     * Method returns a matching user. If the username and password match, it returns a valid user.
     *
     * @param userName Username for the user trying to login
     * @param password User's password
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getUser(String userName, String password) throws SQLException {

        String query = "SELECT * FROM client_schedule.users Where User_Name LIKE BINARY ? AND Password LIKE BINARY ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    /**
     * Method that returns all users. This method returns all the users in the database.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getAllUsers() throws SQLException {
        String query = "SELECT * FROM client_schedule.users";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    /**
     * Method that returns related users. This method will return all users that have the same id.
     *
     * @param userId Id of the user
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getUser(int userId) throws SQLException {
        String query = "SELECT * FROM client_schedule.users WHERE User_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, userId);

        return statement.executeQuery();
    }
}
