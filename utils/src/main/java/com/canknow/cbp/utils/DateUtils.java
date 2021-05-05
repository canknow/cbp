package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {
    public LocalDateTime getTodayStart() {
        return stringToDate(format(LocalDateTime.now(), "yyyy-MM-dd 00:00:00"));
    }

    public String format(String format) {
        return format(LocalDateTime.now(), format);
    }

    public String format(LocalDateTime date, String format) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(date);
    }

    public LocalDateTime stringToDate(String value) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS);
        return LocalDateTime.parse(value, dateTimeFormatter);
    }

    public LocalDateTime stringToDate(String value, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(value, dateTimeFormatter);
    }

    public String getDateTimeString(LocalDateTime date){
        return format(date, DatePattern.YYYY_MM_DD_HH_MM_SS);
    }

    public LocalDateTime timestampToDatetime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
