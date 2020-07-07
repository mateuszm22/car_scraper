package utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils(){
    }

    public static String getActualTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" " + "yyyy-MM-dd" + " " + "HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }
}
