package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Abstract database class for all queries related to appointments. This class contains all appointment queries.
 *
 * @author José L Dávila Montalvo
 * */
public abstract class AppointmentQuery {

    /**
     * This method inserts appointment data into the database. Data collected from the user is entered in the INSERT query and added to the database.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param startDate Start Instant of the appointment
     * @param endDate End Instant of the appointment
     * @param createDate Instant when appointment was created
     * @param createdBy Username of the appointment creator
     * @param lastUpdated Instant of the last update
     * @param lastUpdatedBy Username of who updated it last
     * @param customerId Id of the customer
     * @param userId Id of the user
     * @param contactId Id of the contact
     *
     * */
    public static void insert(String title, String description, String location, String type, Instant startDate, Instant endDate,
                              Instant createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId,
                              int userId, int contactId) throws SQLException {

        String query = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End,Create_Date, " +
                "Created_by,Last_Update,Last_Updated_By, Customer_ID,User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,title);
        statement.setString(2,description);
        statement.setString(3,location);
        statement.setString(4,type);
        statement.setTimestamp(5,Timestamp.from(startDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(6, Timestamp.from(endDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(7, Timestamp.from(createDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setString(8, createdBy);
        statement.setTimestamp(9, lastUpdated, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setString(10, lastUpdatedBy);
        statement.setInt(11, customerId);
        statement.setInt(12, userId);
        statement.setInt(13, contactId);

        statement.executeUpdate();
    }

    /**
     * This method updates an existing appointment. Data collected from the user is entered in the UPDATE query and updates the corresponding appointment.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param startDate Start Instant of the appointment
     * @param endDate End Instant of the appointment
     * @param lastUpdated Instant of the last update
     * @param lastUpdatedBy Username of who updated it last
     * @param customerId Id of the customer
     * @param userId Id of the user
     * @param contactId Id of the contact
     * @param iD Appointment Id used in WHERE statement to match the correct appointment being updated
     *
     * */
    public static void update(String title, String description, String location, String type, Instant startDate, Instant endDate,
                             Timestamp lastUpdated, String lastUpdatedBy, int customerId,
                             int userId, int contactId, int iD) throws SQLException {

        String query = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1,title);
        statement.setString(2,description);
        statement.setString(3,location);
        statement.setString(4,type);
        statement.setTimestamp(5,Timestamp.from(startDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(6, Timestamp.from(endDate), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(7, lastUpdated, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setString(8, lastUpdatedBy);
        statement.setInt(9, customerId);
        statement.setInt(10, userId);
        statement.setInt(11, contactId);
        statement.setInt(12, iD);

        statement.executeUpdate();
    }

    /**
     * This method deletes an existing appointment. The user will delete a selected appointment.
     *
     * @param iD Appointment Id for the appointment that will be deleted
     * */
    public static void delete(int iD) throws SQLException {
        String query = "DELETE from client_schedule.appointments WHERE Appointment_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, iD);

        statement.executeUpdate();
    }

    /**
     * Method that returns all appointments. This method returns all the appointments in the database.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getAllAppointments() throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments ORDER BY START";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    /**
     * Method that returns related appointments. This method will return all appointments that have the same customer id.
     *
     * @param custId Id of the customer
     *
     * @return returns a result set from the query
     * */
    public static ResultSet relatedAppointments(int custId) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, custId);

        return statement.executeQuery();
    }

    /**
     * Method that returns related appointments. This method will return appointments where the start time is within 15 minutes of the time entered.
     *
     * @param appTime Local time
     *
     * @return returns a result set from the query
     * */
    public static ResultSet relatedAppointment(Instant appTime) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE TIMESTAMPDIFF(MINUTE,?, Start) BETWEEN 0 and 15 ";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.from(appTime),  Calendar.getInstance(TimeZone.getTimeZone("UTC")));

        return statement.executeQuery();
    }

    /**
     * Method that returns related appointments. This method will return appointments where the contact id is the same.
     *
     * @param contactId Id of the contact
     *
     * @return returns a result set from the query
     * */
    public static ResultSet contactAppointments(int contactId) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, contactId);

        return statement.executeQuery();
    }

    /**
     * Method that returns all appointments. This method returns all the appointments for the current month.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getMonthAppointments() throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE MONTH(START) = MONTH(NOW())";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    public static ResultSet getMonthAppointments(int customerID) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE MONTH(START) = MONTH(NOW()) AND Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, customerID);

        return statement.executeQuery();
    }

    /**
     * Method that returns all appointments. This method returns all the appointments for the current week.
     *
     * @return returns a result set from the query
     * */
    public static ResultSet getWeekAppointments() throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE WEEK(START) = WEEK(NOW())";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    public static ResultSet getWeekAppointments(int customerId) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE WEEK(START) = WEEK(NOW()) AND Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, customerId);

        return statement.executeQuery();
    }

    /**
     * Method that returns all appointments. This method returns all overlapping appointments.
     *
     * @param startTime Start time of selected appointment
     * @param endTime End time of selected appointment
     *
     * @return returns a result set from the query
     * */
    public static ResultSet timeOverlap(Instant startTime, Instant endTime) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE (Start > ? and Start < ?) OR (End > ? and End < ?) OR (Start = ? and End = ?)";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.from(startTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(2, Timestamp.from(endTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(3, Timestamp.from(startTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(4, Timestamp.from(endTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(5, Timestamp.from(startTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        statement.setTimestamp(6, Timestamp.from(endTime), Calendar.getInstance(TimeZone.getTimeZone("UTC")));

        return statement.executeQuery();
    }

    /**
     * Method returns number of appointments. The number of appointments returned depend on the month and appointment type selected.
     *
     * @param month Month of selected appointment
     * @param type Type of selected appointment
     *
     * @return returns a result set from the query
     * */
    public static ResultSet numberOfAppointments(String month, String type) throws SQLException {
        String query = "SELECT COUNT(TYPE) AS quantity FROM client_schedule.appointments WHERE MONTH(Start) = MONTH(str_to_date(?, '%M')) AND TYPE = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setString(1, month);
        statement.setString(2, type);

        return statement.executeQuery();
    }
}
