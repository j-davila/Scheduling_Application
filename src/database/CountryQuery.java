package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {

    public static ResultSet getAllCountries() throws SQLException {
        String query = "SELECT * FROM client_schedule.countries";

        ResultSet results = JDBC.connection.createStatement().executeQuery(query);

        return results;
    }

    public static ResultSet getCountry(int countryId) throws SQLException {
        String query = "SELECT * FROM client_schedule.countries WHERE Country_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, countryId);

        return statement.executeQuery();
    }


}
