package utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactQuery {

    public static ResultSet getAllContacts() throws SQLException {
        String query = "SELECT * FROM client_schedule.contacts";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }

    public static ResultSet getContact(int contactId) throws SQLException {
        String query = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, contactId);

        return statement.executeQuery();
    }

}
