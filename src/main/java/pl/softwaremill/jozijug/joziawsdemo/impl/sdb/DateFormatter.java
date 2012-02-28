package pl.softwaremill.jozijug.joziawsdemo.impl.sdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: szimano
 */
public class DateFormatter {

    private static final SimpleDateFormat format;

    static {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public String formatDate(Date date) {
       return format.format(date);
    }
    
    public Date parseDate(String dateString) {
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

