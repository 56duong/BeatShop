package beatalbumshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The TimeHelper class provides utility methods for handling time-related operations.
 */
public class TimeHelper {
    
    /**
     * Converts milliseconds to a formatted string representing minutes and seconds.
     * 
     * @param ms the duration in milliseconds
     * @return the formatted string in the format "mm:ss"
     */
    public static String msToMinute(String ms) {
        long msLong = Long.parseLong(ms);
        long minutes = (msLong / 1000) / 60;
        long seconds = (msLong / 1000) % 60;
        String n = minutes + ":" + (seconds < 10 ? ("0" + seconds) : seconds);
        return n;
    }
    
    
    
    /**
     * Retrieves the current date and time in the format "yyyy-MM-dd HH:mm:ss".
     * 
     * @return the current date and time as a string
     */
    public static String getCurrentDateTime() {
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentTime);
        
        return currentDateTime;
    }
}
