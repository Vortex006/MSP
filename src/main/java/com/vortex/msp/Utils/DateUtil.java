package com.vortex.msp.Utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 格式化日期
 */
public class DateUtil {

    private static SimpleDateFormat format = null;

    //格式化日期 格式:“yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒”
    public static String formatDateTimeCN(Date date) {
        format = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒");
        return format.format(date);
    }

    //格式化日期 格式:“yyyy-MM-dd HH:mm:ss”
    public static String formatDateTime(Date date) {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    //格式化日期 格式:“yyyy-MM-dd”
    public static String formatDate(Date date) {
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String format(Date date, String formatStr) {
        format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    public static List<String> getScheTime(String startStr, String endStr) {
        // 使用Duration设置每15分钟的间隔
        Duration duration = Duration.ofMinutes(30);
        // 创建时间格式化对象
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        LocalDate date = LocalDate.parse("2024-06-24");
        // 设置开始和结束时间
        LocalTime startTime = LocalTime.parse(startStr, timeFormatter);
        LocalTime endTime = LocalTime.parse(endStr, timeFormatter);

        // 特定排除的开始和结束时间
        LocalTime excludeStart = LocalTime.parse("11:30", timeFormatter);
        LocalTime excludeEnd = LocalTime.parse("13:30", timeFormatter);

        // 存储每15分钟的时间段，排除特定时间段
        List<String> intervals = new ArrayList<>();


        while (startTime.isBefore(endTime)) {
            StringBuilder sb = new StringBuilder();
            if (startTime.isBefore(excludeStart) || startTime.isAfter(excludeEnd)) {
                sb.append(startTime.format(timeFormatter));
                sb.append("-");
                startTime = startTime.plus(duration);
                sb.append(startTime.format(timeFormatter));
                intervals.add(sb.toString());
            } else {
                startTime = startTime.plus(duration);
            }
        }
        return intervals;
    }

    public static LocalDateTime getLocalDateTime(long timestamp) {
        // 将毫秒时间戳转换为Instant
        Instant instant = Instant.ofEpochMilli(timestamp);

        // 将Instant转换为LocalDateTime，需要指定时区
        ZoneId zoneId = ZoneId.systemDefault(); // 使用系统默认时区
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    public static LocalDateTime getLocalDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static String getLocalDateTimeStr(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesBetween = new ArrayList<>();
        if (startDate.equals(endDate)) {
            datesBetween.add(startDate);
            return datesBetween;
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("开始日期必须在结束日期之前");
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        for (int i = 0; i <= daysBetween; i++) {
            datesBetween.add(startDate.plusDays(i));
        }

        return datesBetween;
    }

    public static String now() {
        LocalDateTime dateTime = LocalDateTime.now();
        return getLocalDateTimeStr(dateTime);
    }

    public static LocalTime getLocalTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(time, formatter);
    }


}