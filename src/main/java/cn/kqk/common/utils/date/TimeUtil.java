package cn.kqk.common.utils.date;

import java.util.Calendar;

/**
 * @author lhr
 * 时间工具类
 * @date 2018/12/20 8:49
 */
public class TimeUtil {
    public static final long ONE_DAY = 1000 * 60 * 60 * 24;

    /**
     * 获取一天的开始时间
     * @param time 要获取的时间
     * @return 时间戳
     */
    public static long getOneDayStart(long time){
        Calendar oneDayStart = Calendar.getInstance();
        oneDayStart.setTimeInMillis(time);
        oneDayStart.set(Calendar.HOUR, 0);
        oneDayStart.set(Calendar.MINUTE, 0);
        oneDayStart.set(Calendar.SECOND, 0);
        oneDayStart.set(Calendar.MILLISECOND, 0);
        return oneDayStart.getTime().getTime();
    }

    /**
     * 获取当天0点的时间戳
     * @return 时间戳
     */
    public static long getTodayStart(){
        return getOneDayStart(System.currentTimeMillis());
    }

    /**
     * 获取前N天的0点时间戳
     * @param dayBefore 提前的天数
     * @return 时间戳
     */
    public static long getLastNDayStart(int dayBefore){
        return getTodayStart() - (dayBefore * ONE_DAY);
    }

    /**
     * 获取后N天的0点时间戳
     * @param dayAfter 之后的天数
     * @return 时间戳
     */
    public static long getNextNDayStart(int dayAfter){
        return getTodayStart() + (dayAfter * ONE_DAY);
    }

    /**
     * 判断时间是多少天前
     * @param time 需要判断的时间戳
     * @return 天数
     */
    public static int howMuchDayBefore(long time){
        long interval = getTodayStart() - time;
        if (interval <= 0){
            return 0;
        }
        int days = (int) (interval / ONE_DAY);
        days += interval % ONE_DAY > 0 ? 1 : 0;
        return days;
    }

    /**
     * 获取月份开始时的时间戳
     * @param year 年份
     * @param month 月份
     * @return 时间戳
     */
    public static long getMonthStart(int year, int month){
        Calendar monthStart = Calendar.getInstance();
        monthStart.setTimeInMillis(System.currentTimeMillis());
        monthStart.set(Calendar.YEAR, year);
        monthStart.set(Calendar.MONTH, month-1);
        return getOneDayStart(monthStart.getTime().getTime());
    }

    /**
     * 获取年份开始的时间戳
     * @param year 年份
     * @return 时间戳
     */
    public static long getYearStart(int year){
        return getMonthStart(year, 1);
    }

    /**
     * 获取当前天数（每月几号）
     * @return 当前天数
     */
    public static int getDayOfMonth(){
        Calendar dayOfMonth = Calendar.getInstance();
        dayOfMonth.setTimeInMillis(System.currentTimeMillis());
        return dayOfMonth.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月份
     * @return 当前月份
     */
    public static int getMonthOfYear(){
        Calendar monthOfYear = Calendar.getInstance();
        monthOfYear.setTimeInMillis(System.currentTimeMillis());
        return monthOfYear.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前年份
     * @return 当前年份
     */
    public static int getYear(){
        Calendar year = Calendar.getInstance();
        year.setTimeInMillis(System.currentTimeMillis());
        return year.get(Calendar.YEAR);
    }
}
