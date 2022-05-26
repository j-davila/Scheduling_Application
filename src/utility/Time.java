package utility;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

public class Time {

    // Returns timestamp for SQl database
  public static Timestamp toTimestamp(LocalDateTime now){
      return Timestamp.valueOf(now);
  }

  // converts timestamp to localDateTime
  public static LocalDateTime toLocalDT(Timestamp local){
      return local.toLocalDateTime();
  }

  // converts local date time to zone date time
  public static ZonedDateTime toZoneDateTime(String zoneID, LocalDateTime dateTime){
      return dateTime.atZone(ZoneId.of(zoneID));
  }

  // overloaded method to convert zone date time to local date time
  public static LocalDateTime toLocalDT(ZonedDateTime local){
      return local.toLocalDateTime();
  }

  //converts dateTime to a string
  public static String dateTimeToString(LocalDateTime dateTime){

      return dateTime.toString();
  }

  // converts string to dateTime
  public static LocalDateTime stringToDateTime(String dateTime){

      return LocalDateTime.parse(dateTime);
  }

  public static LocalDate toDate(LocalDateTime dateTime){

      return dateTime.toLocalDate();
  }

  public static LocalTime toTime(LocalDateTime dateTime){

      return dateTime.toLocalTime();
  }

}
