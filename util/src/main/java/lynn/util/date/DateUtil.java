package lynn.util.date;

import lynn.util.StringUtil;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date工具类 Date 、String 、XMLGregorianCalendar 3种类型的转换<br/>
 * 以及日期的常见操作,底层依赖的是java.util.Calendar
 */
public class DateUtil {
    /**
     * 日期格式为yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String YMD_HMS_S = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 日期格式为yyyy-MM-dd HH:mm:ss
     */
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式为yyyy-MM-dd
     */
    public static final String YMD = "yyyy-MM-dd";

    public static final String HMS = "HH:mm:ss";
//	public template final int YEAR = 1;
//	public template final int MOUTH = 2;
//	public template final int WEEK_OF_YEAR = 3;
//	public template final int WEEK_OF_MONTH = 4;
    /**
     * 天
     */
    public static final int DAY = 5;
    /**
     * 时(12小时制)
     */
    public static final int HOUR = 10;
    /**
     * 时(24小时制)
     */
    public static final int HOUR_OF_DAY = 11;
    /**
     * 分钟
     */
    public static final int MINUTE = 12;
    /**
     * 秒
     */
    public static final int SECOND = 13;
    /**
     * 毫秒
     */
    public static final int MILLISECOND = 14;

    /**
     * 将指定字符(String)串转换成日期 (Date),格式是yyyy-MM-dd HH:mm:ss <br>
     * 如果发生错误，则返回 null, 格式不是yyyy-MM-dd HH:mm:ss也会返回null
     *
     * @param dateStr String 日期字符串
     * @return Date
     */
    public static Date strToDate(String dateStr) {
//		SimpleDateFormat sd = new SimpleDateFormat(YMD_HMS);
//		// return sd.parse(dateStr);//这样可能会出 类型现转换异常
//		return sd.parse(dateStr, new java.text.ParsePosition(0));
        return strToDate(dateStr, YMD_HMS);
    }

    /**
     * 将指定字符(String)串转换成日期 (Date)<br/>
     * 如果发生格式对不上差一部分，则返回 null。
     * 如果发生格式对不上多一部分，则返回 datePattern的格式。
     * <p>
     * datePattern为yyyy-MM-dd，dateStr为""或"2017-01"，则返回null
     * datePattern为yyyy-MM-dd，dateStr为"2017-01-01 12"，则返回"2017-01-01"对应的日期
     * </p>
     *
     * @param dateStr     String 日期字符串
     * @param datePattern String 日期格式
     * @return Date
     */
    public static Date strToDate(String dateStr, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        // return sd.parse(dateStr);//这样可能会出 类型现转换异常
        return sd.parse(dateStr, new java.text.ParsePosition(0));
    }

    /**
     * 将指定日期(Date)对象转换成 格式化字符串 (String),格式是yyyy-MM-dd HH:mm:ss
     *
     * @param date Date 日期对象
     * @return String
     */
    public static String dateToStr(Date date) {
//		SimpleDateFormat sd = new SimpleDateFormat(YMD_HMS);
//		return sd.format(date);
        return dateToStr(date, YMD_HMS);
    }

    /**
     * 将指定日期(Date)对象转换成 格式化字符串 (String)
     *
     * @param date        Date 日期对象
     * @param datePattern String 日期格式
     * @return String
     */
    public static String dateToStr(Date date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        return sd.format(date);
    }

    public static String dateToStrTimezone(Date date, String datePattern, TimeZone tz) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        sd.setTimeZone(tz);
        return sd.format(date);
    }

    /**
     * 将指定时间戳(timestamp) 转换成 格式化日期(Date),格式是yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(long timestamp) {
        return timestampToDate(timestamp, YMD_HMS);
    }

    /**
     * 将指定时间戳(timestamp) 转换成 格式化日期(Date)
     *
     * @param timestamp   时间戳
     * @param datePattern 日期格式
     * @return
     */
    public static Date timestampToDate(long timestamp, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        String dateStr = sd.format(timestamp);
        return sd.parse(dateStr, new java.text.ParsePosition(0));
    }

    /**
     * 将指定XML日期(XMLGregorianCalendar)对象转换成 格式化字符串 (String),格式是yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param xmlDate Date XML日期对象
     * @return String
     */
    public static String xmlDateToStr(XMLGregorianCalendar xmlDate) {
        return xmlDateToStr(xmlDate, YMD_HMS_S);
//		return xmlDate.toString();//转成的格式是2017-01-10T10:54:36.647+08:00
    }

    /**
     * 将指定XML日期(XMLGregorianCalendar)对象转换成 格式化字符串 (String)
     *
     * @param xmlDate     Date XML日期对象
     * @param datePattern String 日期格式
     * @return String
     */
    public static String xmlDateToStr(XMLGregorianCalendar xmlDate, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        Calendar calendar = xmlDate.toGregorianCalendar();
        return sd.format(calendar.getTime());
    }

    /**
     * 字符串 (String),格式是yyyy-MM-dd HH:mm:ss转换成XML日期(XMLGregorianCalendar)对象
     *
     * @param dateStr String 日期字符串
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar strToXmlDate(String dateStr) {
        return strToXmlDate(dateStr, YMD_HMS);
    }

    /**
     * 字符串 (String) 转换成XML日期(XMLGregorianCalendar)对象
     *
     * @param dateStr     String 日期字符串
     * @param datePattern String 日期格式
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar strToXmlDate(String dateStr, String datePattern) {
        return dateToXmlDate(strToDate(dateStr, datePattern));
    }

    /**
     * 将指定XML日期(XMLGregorianCalendar)对象转换成 日期对象 (Date)
     *
     * @param xmlDate Date XML日期对象
     * @return Date
     */
    public static Date xmlDateToDate(XMLGregorianCalendar xmlDate) {
        return xmlDate.toGregorianCalendar().getTime();
    }

    /**
     * 将指定日期对象 (Date)转换成 XML日期(XMLGregorianCalendar) 对象
     *
     * @param date Date 日期对象
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

    /**
     * 获得当前时间的str格式，格式为yyyyMMddHHmmss
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @Title: todayStr
     */
    public static String today2YyyyMMddHHmmss() {
        Date date = new Date();
        return dateToStr(date, "yyyyMMddHHmmss");
    }

    /**
     * 获得当前时间的str格式，格式为yyyyMMddHHmmssSSS
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @Title: todayStr
     */
    public static String today2YyyyMMddHHmmssSSS() {
        Date date = new Date();
        return dateToStr(date, "yyyyMMddHHmmssSSS");
    }

    /**
     * 获得当前时间的str格式，格式为yyMMddHHmmss
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @Title: todayStr
     */
    public static String today2YyMMddHHmmss() {
        Date date = new Date();
        return dateToStr(date, "yyMMddHHmmss");
    }

    /**
     * 获得当前时间的str格式，格式为yyMMddHHmmssSSS
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @Title: todayStr
     */
    public static String today2YyMMddHHmmssSSS() {
        Date date = new Date();
        return dateToStr(date, "yyMMddHHmmssSSS");
    }

    /**
     * time1与time2的时间差，返回单位是毫秒数 <br>
     * 返回值大于0, 表示time1 > time2
     * 返回值等于0, 表示time1 = time2
     * 返回值小于0, 表示time1 < time2
     *
     * @param time1 当前时间
     * @param time2 比较时间
     * @return
     */
    public static long timeDiff(Date time1, Date time2) {
        return time1.getTime() - time2.getTime();
    }

    /**
     * 返回true表示 time1在time2前（time1与time2一样返回是false）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isBefore(Date time1, Date time2) {
        long times = timeDiff(time1, time2);
        if (times < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回true表示 time1在time2前（time1与time2一样返回是true）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isBeforeHasEq(Date time1, Date time2) {
        long times = timeDiff(time1, time2);
        if (times <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回true表示 time1在time2后（time1与time2一样返回是false）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isAfter(Date time1, Date time2) {
        long times = timeDiff(time1, time2);
        if (times > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 返回true表示 time1在time2后（time1与time2一样返回是true）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isAfterHasEq(Date time1, Date time2) {
        long times = timeDiff(time1, time2);
        if (times >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * time1与time2的时间差,设定了type则根据其返回<br/>
     * 结果都是整型,默认的取整方式(向上取整),如0.6返回是0
     *
     * @param time1 当前时间
     * @param time2 比较时间
     * @param type  获取时间类型——DateUtil.DAY(天)、DateUtil.HOUR(小时)、DateUtil.MINUTE(分钟)、DateUtil.SECOND(秒)
     * @return
     */
    public static long timeDiff(Date time1, Date time2, int type) {
        long times = time1.getTime() - time2.getTime();
        switch (type) {
            case DateUtil.DAY:
                times = times / 1000 / 3600 / 24;
                break;
            case DateUtil.HOUR:
                times = times / 1000 / 3600;
                break;
            case DateUtil.MINUTE:
                times = times / 1000 / 60;
                break;
            case DateUtil.SECOND:
                times = times / 1000;
                break;
        }
        return times;
    }

    /**
     * time1与time2的时间差，没有type返回单位是毫秒数,设定了type则根据其返回,结果都是整型,依据四舍五入
     *
     * @param time1 当前时间
     * @param time2 比较时间
     * @param type  获取时间类型——DateUtil.DAY(天)、DateUtil.HOUR(小时)、DateUtil.MINUTE(分钟)、DateUtil.SECOND(秒)
     * @return
     */
    public static long timeDiff2(Date time1, Date time2, int type) {
        long times = time1.getTime() - time2.getTime();
        switch (type) {
            case DateUtil.DAY:
                times = (long) (times / 1000.0 / 3600 / 24 + 0.5);
                break;
            case DateUtil.HOUR:
                times = (long) (times / 1000.0 / 3600 + 0.5);
                break;
            case DateUtil.MINUTE:
                times = (long) (times / 1000.0 / 60 + 0.5);
                break;
            case DateUtil.SECOND:
                times = (long) (times / 1000.0 + 0.5);
                break;
        }
        return times;
    }

    /**
     * time1与time2的时间差，没有type返回单位是毫秒数,设定了type则根据其返回
     *
     * @param time1 当前时间
     * @param time2 比较时间
     * @param type  获取时间类型——DateUtil.HOUR(小时)、DateUtil.MINUTE(分钟)、DateUtil.SECOND(秒)
     * @return
     */
    public static double timeDiff2Double(Date time1, Date time2, int type) {
        double times = time1.getTime() - time2.getTime();
        switch (type) {
            case DateUtil.HOUR:
                times = times / 1000.0 / 3600;
                break;
            case DateUtil.MINUTE:
                times = times / 1000.0 / 60;
                break;
            case DateUtil.SECOND:
                times = times / 1000.0;
                break;
        }
        return times;
    }

    /**
     * 获取日期的年份
     *
     * @param date 日期对象
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 如果需要将int 转成 String，请用 String.valueOf()方法
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份
     *
     * @param date 日期对象
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数
     *
     * @param date 日期对象
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获取日期的星期(国际的记录方式), 周日为一周第一天,即 1 周日  2周一  7周六
     *
     * @param date 日期对象
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取日期的星期 (中国的记录方式), 周一为一周第一天,即 1 周一  2周二  7周日
     *
     * @param date 日期对象
     * @return
     */
    public static int getWeek2Zh(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return getWeek2Zh(c);
    }

    public static int getWeek2Zh(Calendar c) {
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else {
            week--;
        }

        return week;
    }

    /**
     * 判断日期 是不是周末,true表示是周末
     *
     * @param date 日期对象
     * @return true表示该日期是周末
     */
    public static boolean isWeekend(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 获取当前日期星期，英国算法(周日为一周第一天)
        int day = c.get(Calendar.DAY_OF_WEEK);
        // 如果是周六或周日就返回true
        if (day == 7 || day == 1) {
            return true;
        }
        return false;
    }

    /**
     * 指定date 增加天数、小时、分钟或秒数
     *
     * @param date
     * @param num  数值，天数、小时、分钟或秒数，可以是负值
     * @param type 类型——DateUtil.DAY(天)、DateUtil.HOUR(小时)、DateUtil.MINUTE(分钟)、DateUtil.SECOND(秒)
     */
    private static Date addDayOrHourOrMinuteOrSecond(Date date, int num, int type) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (type) {
            case DateUtil.DAY:
                c.add(Calendar.DAY_OF_MONTH, num);
                break;
            case DateUtil.HOUR:
                c.add(Calendar.HOUR, num);
                break;
            case DateUtil.MINUTE:
                c.add(Calendar.MINUTE, num);
                break;
            case DateUtil.SECOND:
                c.add(Calendar.SECOND, num);
                break;
        }

        return c.getTime();
    }

    /**
     * 指定date 增加days天，可以是负值
     *
     * @param date
     * @param days
     */
    public static Date addDay(Date date, int days) {
        return addDayOrHourOrMinuteOrSecond(date, days, DateUtil.DAY);
    }

    /**
     * 指定date 增加hour小时，可以是负值
     *
     * @param date
     * @param hours
     */
    public static Date addHour(Date date, int hours) {
        return addDayOrHourOrMinuteOrSecond(date, hours, DateUtil.HOUR);
    }

    /**
     * 指定date 增加minutes分钟，可以是负值
     *
     * @param date
     * @param minutes
     */
    public static Date addMinute(Date date, int minutes) {
        return addDayOrHourOrMinuteOrSecond(date, minutes, DateUtil.MINUTE);
    }

    /**
     * 指定date 增加seconds秒，可以是负值
     *
     * @param date
     * @param seconds
     */
    public static Date addSecond(Date date, int seconds) {
        return addDayOrHourOrMinuteOrSecond(date, seconds, DateUtil.SECOND);
    }

    /**
     * 获取某个日期该月有多少天
     *
     * @param date 日期对象
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取某个日期的开始时间<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-6 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getStartTimeOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取某个日期的结束时间<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-7 0:00:00"<br/>
     * 获取的不是"2016-6-6 23:59:59",为了避免出现转点的问题
     *
     * @param date 日期对象
     * @return
     */
    public static Date getEndTimeOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
//	//与上面的效果相同
//	public template Date getEndTime(Date date) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.add(Calendar.DAY_OF_MONTH, 1);
//		c.set(Calendar.HOUR_OF_DAY, 0);
//		c.set(Calendar.MINUTE, 0);
//		c.set(Calendar.SECOND, 0);
//		return c.getTime();
//	}

    /**
     * 获取某个日期该周的第一天（周一）<br/>
     * 如"2016-6-6 10:10:10 周一",返回"2016-6-6 0:00:00"
     * 如"2016-6-8 10:10:10 周三",返回"2016-6-6 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getFirstDayOfWeek2zh(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //周几
        int week2Zh = getWeek2Zh(c);
        c.add(Calendar.DAY_OF_MONTH, 1 - week2Zh);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    /**
     * 获取某个日期指定月份第一天的日期<br/>
     * 如"2016-6-6 10:10:10",指定为5月,返回"2016-5-1 0:00:00"
     *
     * @param date           指定日期
     * @param specifiedMonth 指定月份
     * @return
     */
    public static Date getFirstDayOfMonth(Date date, int specifiedMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, specifiedMonth - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当年指定月份第一天的日期<br/>
     * 如"2016-6-6 10:10:10",指定为5月,返回"2016-5-1 0:00:00"
     *
     * @param specifiedMonth 指定月份
     * @return
     */
    public static Date getFirstDayOfMonth(int specifiedMonth) {
        return getFirstDayOfMonth(new Date(), specifiedMonth);
    }

    /**
     * 获取某个日期该月的第一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-1 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    /**
     * 设置日期的分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date setMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, minute);
        return c.getTime();
    }
    
    /**
     * 设置日期的秒钟
     * @param date
     * @param minute
     * @return
     */
    public static Date setSecond(Date date, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }
    
    /**
     * 设置日期的小时
     * @param date
     * @param minute
     * @return
     */
    public static Date setHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        return c.getTime();
    }
    

    /**
     * 获取某个日期前后n个月的第一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-1 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getFirstDayOfSomeMonth(Date date, int specifiedMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, specifiedMonth);
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static void main(String[] args) {
        Date firstDayOfBeforeThreeMonth = getFirstDayOfSomeMonth(new Date(), -5);
        System.out.println(firstDayOfBeforeThreeMonth.toLocaleString());
    }

    /**
     * 获取某个日期该月的最后一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-30 23:59:59"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getLastDayOfMonth(Date date, int specifiedMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, specifiedMonth - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取某个日期该月的最后一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-30 23:59:59"
     *
     * @param specifiedMonth 日期对象
     * @return
     */
    public static Date getLastDayOfMonth(int specifiedMonth) {
        return getLastDayOfMonth(new Date(), specifiedMonth);
    }

    /**
     * 获取某个日期该月的最后一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-6-30 23:59:59"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取某个日期下个月的第一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-7-1 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取某个日期上个月的第一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-5-1 0:00:00"
     *
     * @param date 日期对象
     * @return
     */
    public static Date getFirstDayOfLastMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取某个日期今年的第一天<br/>
     * 如"2016-6-6 10:10:10",返回"2016-1-1 0:00:00"
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 0);//月份从0开始
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    /**
     * 比较日期的年月日时间部分对应的毫秒数
     *
     * @param d1
     * @param d2
     * @return 0:d1=d2;  小于 0:d1 小于 d2; 大于0: d1 大于 d2
     */
    public static long compareDateOnly(Date d1, Date d2) {
        return getDateTimeOnly(d1) - getDateTimeOnly(d2);
    }

    /**
     * 获得日期的年月日时间部分对应的毫秒数
     *
     * @param date
     * @return
     */
    public static long getDateTimeOnly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 计算两个时间的差，返回相差时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Double timeDiff3(Date date1, Date date2) {
        //先将两个时间转换为毫秒相减，得到相差的毫秒数
        long diffTime = date1.getTime() - date2.getTime();
        BigDecimal timeCost = BigDecimal.valueOf(diffTime).divide(BigDecimal.valueOf(60000), 2, BigDecimal.ROUND_HALF_UP);
        return timeCost.doubleValue();
    }

    /**
     * 当前日期向后推多少年
     *
     * @param addYear 增加的年数
     * @return
     */
    public static Date addYear(Integer addYear) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, addYear);
        return calendar.getTime();
    }

    /**
     * 给定日期向后推多少年
     *
     * @param date    给定日期
     * @param addYear 增加的年数
     * @return
     */
    public static Date addYear(Date date, Integer addYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, addYear);
        return calendar.getTime();
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        return lDate;
    }

    /**
     * 服务器同事，当前端提交的时间格式不正确时（必须时XX时XX分）这种格式，才可以保存成功
     *
     * @return
     */
    public static boolean validNightTime(String time) {
        List<String> timeList = Arrays.asList(time.split(":"));
        if (timeList.size() < 2 ||
                StringUtil.isEmpty(timeList.get(0)) ||
                StringUtil.isEmpty(timeList.get(1)) ||
                timeList.get(0).length() != 2 ||
                timeList.get(1).length() != 2) {
            return false;
        }
        Integer hour = Integer.valueOf(timeList.get(0));
        if (hour < 0 || hour >= 24) {
            return false;
        }
        Integer minute = Integer.valueOf(timeList.get(1));
        if (minute < 0 || minute >= 60) {
            return false;
        }
        return true;
    }

    /**
     *  获得某天的凌晨的时间time
     * @return
     */
    public static Date getDesignationDayZeroTime(int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

   /**
     *  获得今天凌晨的时间time
     * @return
     */
    public  static Date getTodaytDayZeroTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 将秒转化为时分秒
     * @param seconds
     * @return
     */
    public static String secondsConvertedTOHMS(Integer seconds) {
        int secondTime = seconds;
        int minuteTime = 0;
        int hourTime = 0;
        //如果秒数大于60，将秒数转换成整数
        if (secondTime >= 60) {

            //获取分钟，除以60取整数，得到整数分钟
            minuteTime = secondTime / 60;
            //获取秒数，秒数取佘，得到整数秒数
            secondTime = secondTime % 60;
            //如果分钟大于60，将分钟转换成小时
            if (minuteTime >= 60) {
                //获取小时，获取分钟除以60，得到整数小时
                hourTime = minuteTime / 60;
                //获取小时后取佘的分，获取分钟除以60取佘的分
                minuteTime = minuteTime % 60;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (hourTime == 0) {
            stringBuffer.append("00");
        } else {
            if (hourTime <= 9) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hourTime);
        }
        stringBuffer.append(":");
        if (minuteTime == 0) {
            stringBuffer.append("00");
        } else {
            if (minuteTime <= 9) {
                stringBuffer.append("0");
            }
            stringBuffer.append(minuteTime);
        }
        stringBuffer.append(":");
        if (secondTime == 0) {
            stringBuffer.append("00");
        } else {
            if (secondTime <= 9) {
                stringBuffer.append("0");
            }
            stringBuffer.append(secondTime);
        }
        return stringBuffer.toString();
    }

}
