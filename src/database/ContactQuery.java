package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract database class for all queries related to contacts. This class contains all contact queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class ContactQuery {

    /**
     * Method that returns all contacts. This method returns all the contacts in the database.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getAllContacts() throws SQLException {
        String query = "SELECT * FROM client_schedule.contacts";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    /**
     * Method that returns related contacts. This method will return all contacts that have the same contact id.
     *
     * @param contactId Id of the contact
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getContact(int contactId) throws SQLException {
        String query = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, contactId);

        return statement.executeQuery();
    }
}
