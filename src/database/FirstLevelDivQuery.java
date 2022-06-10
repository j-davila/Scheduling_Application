package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract database class for all queries related to first-level division. This class contains all first-level division queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class FirstLevelDivQuery {

    /**
     * Method that returns related divisions. This method will return all divisions that are from the matched country id.
     *
     * @param country Id of the country
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getDivisionByCountry(int country) throws SQLException {
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, country);

        return statement.executeQuery();
    }

    /**
     * Method that returns related divisions. This method will return all divisions that have the same division id.
     *
     * @param divisionId Id of the division
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getDivision(int divisionId) throws SQLException {
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, divisionId);

        return statement.executeQuery();
    }
}
