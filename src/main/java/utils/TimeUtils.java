package utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils(){
    }

    public static String getActualTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" " + "EEEE" + " " + "dd-MM-yyyy" + " " + "HH-mm");
        return LocalDateTime.now().format(formatter);
    }
}
