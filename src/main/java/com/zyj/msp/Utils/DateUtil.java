package com.zyj.msp.Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public static List<String> getScheTime() {
        // 使用Duration设置每15分钟的间隔
        Duration duration = Duration.ofMinutes(15);
        // 创建时间格式化对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = LocalDate.parse("2024-06-24");
        // 设置开始和结束时间
        LocalTime startTime = LocalTime.parse("08:00", timeFormatter);
        LocalTime endTime = LocalTime.parse("18:00", timeFormatter);

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

}