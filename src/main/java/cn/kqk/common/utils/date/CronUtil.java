package cn.kqk.common.utils.date;

import cn.kqk.common.utils.string.StringUtil;

import java.util.Calendar;

/**
 * @author lhr
 * @description Cron表达式生成工具
 * @date 2019/4/27 20:51
 */
public class CronUtil {
    /**
     * 根据时间戳生成cron表达式
     * @param timeMillis 时间戳
     * @return cron表达式
     */
    public static String generateByTimeMillis(long timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String second = calendar.get(Calendar.SECOND) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String dayOfWeek = "?";
        String year = calendar.get(Calendar.YEAR) + "";
        return generateCron(second, minute, hour, dayOfMonth, month, dayOfWeek, year);
    }

    /**
     * 生成按周重复的cron表达式
     * @param timeMillis 时间戳
     * @param dayOfWeek 周几
     * @return cron表达式
     */
    public static String generateByWeekRepeat(long timeMillis, int dayOfWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String second = calendar.get(Calendar.SECOND) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String dayOfMonth = "?";
        String month = "*";
        String year = "*";
        return generateCron(second, minute, hour, dayOfMonth, month, dayOfWeek + "", year);
    }

    /**
     * 生成按月循环的cron表达式
     * @param timeMillis 时间戳
     * @param dayOfMonth 每月几号
     * @return cron表达式
     */
    public static String generateByMonthRepeat(long timeMillis, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String second = calendar.get(Calendar.SECOND) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String dayOfWeek = "?";
        String month = "*";
        String year = "*";
        return generateCron(second, minute, hour, dayOfMonth + "", month, dayOfWeek, year);
    }

    /**
     * 生成按年循环的cron表达式
     * @param timeMillis 时间戳
     * @return cron表达式
     */
    public static String generateByYearRepeat(long timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String second = calendar.get(Calendar.SECOND) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String dayOfWeek = "?";
        String year = "*";
        return generateCron(second, minute, hour, dayOfMonth, month, dayOfWeek, year);
    }

    /**
     * 拼接cron字符串，附带简单判断
     * @param second 秒
     * @param minute 分
     * @param hour 小时
     * @param dayOfMonth 每月几号
     * @param month 月份
     * @param dayOfWeek 周几
     * @param year 年
     * @return cron字符串
     */
    public static String generateCron(String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek, String year){
        if (!StringUtil.checkAllNotEmpty(second, minute, hour, dayOfMonth, month, dayOfWeek, year)){
            return null;
        }
        if (!"?".equals(dayOfMonth) && !"?".equals(dayOfWeek)){
            dayOfWeek = "?";
        }
        return second + " " + minute + " " + hour + " " + dayOfMonth + " " + month + " " + dayOfWeek + " " + year;
    }
}
