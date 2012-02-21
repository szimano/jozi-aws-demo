package pl.softwaremill.jozijug.joziawsdemo.impl.sdb;

import java.text.SimpleDateFormat;

/**
 * User: szimano
 */
public class DateFormatter {

    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
