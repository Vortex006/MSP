package com.zyj.msp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}