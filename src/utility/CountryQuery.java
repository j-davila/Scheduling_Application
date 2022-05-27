package utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {

    public static ResultSet getAllCountries() throws SQLException {
        String query = "SELECT * FROM client_schedule.countries";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }
}
