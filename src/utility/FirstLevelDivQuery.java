package utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class FirstLevelDivQuery {

    public static ResultSet getAllDivisions() throws SQLException {
        String query = "SELECT * FROM client_schedule.first_level_divisions";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }

    public static ResultSet getDivision(int country) throws SQLException {

        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, country);

        ResultSet results = statement.executeQuery();

        return results;
    }

}
