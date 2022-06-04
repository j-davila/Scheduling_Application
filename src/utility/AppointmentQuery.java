package utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class AppointmentQuery {

    //add, update, delete appointment
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

    public static void delete(int iD) throws SQLException {
        String query = "DELETE from client_schedule.appointments WHERE Appointment_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, iD);

        statement.executeUpdate();
    }

    public static ResultSet getAllAppointments() throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments ORDER BY START";

        return JDBC.connection.createStatement().executeQuery(query);
    }

    public static ResultSet relatedAppointments(int custId) throws SQLException {

        String query = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";

        PreparedStatement statement = JDBC.connection.prepareStatement(query);
        statement.setInt(1, custId);

        return statement.executeQuery();

    }

    public static ResultSet getWeekAppointments() throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE WEEK(START) = WEEK(NOW())";

        return JDBC.connection.createStatement().executeQuery(query);
    }


}
