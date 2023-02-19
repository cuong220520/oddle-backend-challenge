package com.oddle.app.weather.infrastructure.util;

import org.joda.time.format.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.util
 */
public class DateUtil {

    private DateUtil() {}

    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @return
     */
    public static Date getCurrentDateTime() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String formatDateTime(Date date, String format) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatter);
    }

    public static Date parseDateTime(String date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return Date.from(LocalDateTime.parse(date, dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date parseDateFromEpoch(Long unixSeconds) {
        return Date.from(Instant.ofEpochSecond(unixSeconds));
    }

}
