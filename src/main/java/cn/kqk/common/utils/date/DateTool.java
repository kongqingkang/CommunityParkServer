package cn.kqk.common.utils.date;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * 日期对象 -- 字符串 之间的相互转换
 *
 * @author Administrator
 */
public class DateTool {
    /**
     * 将时间对象格式化成字符串
     *
     * @param date    时间对象
     * @param pattern 格式字符串
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将日期字符串解析成时间对象
     *
     * @param dateStr 日期字符串
     * @param pattern 格式字符串
     * @return 解析后的日期对象
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return date;

    }

    /**
     * 将日期字符串解析成Time格式时间对象
     *
     * @param strDate 时间字符串
     * @return
     */
    public static Time strToTime(String strDate, String pattern) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Time date = new Time(d.getTime());
        return date;
    }

    public static Set<String> getDateStrList(Date startDate, Date endDate, String type) {
        String pattern = null;
        int calendarField = 0;
        // 判断类型
        if (type.equals("day")) {
            pattern = "yyyy-MM-dd";
            calendarField = Calendar.DAY_OF_YEAR;
        } else if (type.equals("month")) {
            pattern = "yyyy-MM";
            calendarField = Calendar.MONDAY;
        } else if (type.equals("year")) {
            pattern = "yyyy";
            calendarField = Calendar.YEAR;
        } else if (type.equals("hour")) {
            pattern = "yyyy-MM-dd HH:00";
            calendarField = Calendar.HOUR_OF_DAY;
        } else {
            // 暂时什么也不做
        }
        Set<String> allDateStrList = new TreeSet<>();
        Calendar startCdr = Calendar.getInstance();
        startCdr.setTime(startDate);
        Calendar endCdr = Calendar.getInstance();
        endCdr.setTime(endDate);
        if (type.equals("hour")) {
            endCdr.set(Calendar.HOUR_OF_DAY, 23);
            endCdr.set(Calendar.MINUTE, 59);
            endCdr.set(Calendar.SECOND, 59);
        }

        while (startCdr.before(endCdr)) {
            allDateStrList.add(DateTool.format(startCdr.getTime(), pattern));
            startCdr.add(calendarField, 1); // 加一个单位
        }
        allDateStrList.add(DateTool.format(endCdr.getTime(), pattern)); // 再加上最后一个
        return allDateStrList;
    }
}
