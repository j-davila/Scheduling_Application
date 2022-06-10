package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract database class for all queries related to countries. This class contains all country queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class CountryQuery {

    /**
     * Method that returns all countries. This method returns all the countries in the database.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getAllCountries() throws SQLException {
        String query = "SELECT * FROM client_schedule.countries";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    /**
     * Method that returns related countries. This method will return all countries that have the same country id.
     *
     * @param countryId Id of the country
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getCountry(int countryId) throws SQLException {
        String query = "SELECT * FROM client_schedule.countries WHERE Country_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, countryId);

        return statement.executeQuery();
    }
}
