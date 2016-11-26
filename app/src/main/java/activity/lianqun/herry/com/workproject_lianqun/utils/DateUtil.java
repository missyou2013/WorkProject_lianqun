package activity.lianqun.herry.com.workproject_lianqun.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.net.ParseException;

public class DateUtil {
    // 取当前月的第一个周的星期一的时间
    public static String getFirstWeekDateOfMonth(Date date) {
        Calendar currCal = Calendar.getInstance();
        if (date != null) {
            currCal.setTime(date);
        }
        // 设置为当前月第1周
        currCal.set(Calendar.WEEK_OF_MONTH, 1);
        // 设置为本周第2天（ 周一）
        currCal.set(Calendar.DAY_OF_WEEK, 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // //System.out.println(dateFormat.format(currCal.getTime()));
        return dateFormat.format(currCal.getTime());

    }

    // 取当前月的第四个周的星期五的时间
    public static String getLastWeekDateOfMonth(Date date) {
        Calendar currCal = Calendar.getInstance();
        if (date != null) {
            currCal.setTime(date);
        }
        // 设置为当前月第4周
        currCal.set(Calendar.WEEK_OF_MONTH, 4);
        // 设置为本周第6天（ 周五）
        currCal.set(Calendar.DAY_OF_WEEK, 6);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // //System.out.println(dateFormat.format(currCal.getTime()));
        return dateFormat.format(currCal.getTime());

    }

    // 取当前月的第一个天 new
    public static String getFirstDateOfMonth(Date date) {
        Calendar currCal = Calendar.getInstance();
        if (date != null) {
            currCal.setTime(date);
        }
        // 设置为当前月第1周
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        // 设置为本周第2天（ 周一）
        // currCal.set(Calendar.DAY_OF_WEEK,2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // //System.out.println(dateFormat.format(currCal.getTime()));
        return dateFormat.format(currCal.getTime());

    }

//    // 取当前月的最后一天 new
//    public static String getLastDateOfMonth(Date date) {
//        Calendar currCal = Calendar.getInstance();
//        if (date != null) {
//            currCal.setTime(date);
//        }
//        currCal.set(Calendar.DAY_OF_MONTH, 1);
//        currCal.add(Calendar.MONDAY, 1);
//        currCal.add(Calendar.DAY_OF_MONTH, -1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        // //System.out.println(dateFormat.format(currCal.getTime()));
//        return dateFormat.format(currCal.getTime());
//
//    }

    /**
     * 计算结束时间
     *
     * @param specifiedDay
     * @return
     */
    public static String getLastDateOfMonth2(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 25);

        String dayAfter = new SimpleDateFormat("yyyy/MM/dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy/MM/dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得周一为开始
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayFri(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 3);

        String dayAfter = new SimpleDateFormat("yyyy/MM/dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayAfter = new SimpleDateFormat("yyyy/MM/dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的后一月
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedMonthAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        // if(month < 11) month+=1;
        month += 1;
        c.set(Calendar.MONTH, month);

        String monthAfter = new SimpleDateFormat("yyyy/MM/dd").format(c
                .getTime());
        return monthAfter;
    }

    /**
     * 获得指定日期的前一月
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedMonthBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        // if(month > 1) month-=1;
        month -= 1;
        c.set(Calendar.MONTH, month);

        String monthAfter = new SimpleDateFormat("yyyy/MM/dd").format(c
                .getTime());
        return monthAfter;
    }

    /**
     * 当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getChineseWeek(Calendar date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        // //System.out.println(dayNames[dayOfWeek - 1]);
        return dayNames[dayOfWeek - 1];

    }

    /**
     * String 转 Calendar
     *
     * @param sdate
     * @return
     */
    public static Calendar getCalendarByString(String sdate) {
        // //System.out.println("sdate-------------------"+sdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Date date = null;
        try {
            try {
                date = sdf.parse(sdate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar;

    }

    /**
     * String 转 date
     *
     * @param sdate
     * @return
     */
    public static Date getDateByString(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            try {
                date = sdf.parse(sdate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String 日期格式化
     *
     * @param sdate
     * @return
     */
    public static String getDateByStrHM(String sdate) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            try {
                date2 = sdf1.parse(sdate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String date = sdf.format(date2);
        return date;
    }

    /**
     * 时间格式化 yyyy-MM-dd
     *
     * @param sdate
     * @return
     */
    public static String getDateForSurplus(String sdate) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            try {
                date2 = sdf1.parse(sdate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String date = sdf1.format(date2);
        return date;

    }

    /**
     * 时间格式化 yyyy-MM-dd
     *
     * @param sdate
     * @return
     */
    public static String getDateForSurplus2(String sdate) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        Date date2 = null;
        try {
            try {
                date2 = sdf1.parse(sdate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String date = sdf1.format(date2);
        return date;

    }

    // 日期格式化 01月01日

    // public static String getFormatDate(String sdate) {
    // String str = "";
    // if (sdate != null) {
    // str += sdate.substring(sdate.indexOf("/") + 1,
    // sdate.lastIndexOf("/"));
    // str += "月";
    // str += sdate
    // .subSequence(sdate.lastIndexOf("/") + 1, sdate.length());
    // str += "日";
    // }
    // return str;
    // }

    /**
     * 比较大小
     *
     * @param
     * @return
     */
    public static int compare(String sdate, String edate) {
        int result = sdate.compareTo(edate);
        /*
		 * if (result > 0) { //System.out.println(sdate + " 晚于 " + edate); }
		 * else if (result == 0) { //System.out.println(sdate + " 等于 " + edate);
		 * } else { //System.out.println(sdate + " 早于 " + edate); }
		 */
        if (result == 0)
            System.out.println("c1相等c2");
        else if (result < 0)
            System.out.println("c1小于c2");
        else
            System.out.println("c1大于c2");
        return result;
    }

    /**
     * date 转 String
     *
     * @param date
     * @return
     */
    public static String DateToStrHM(Date date) {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy年MM月dd日");
        String date1 = dd.format(date);
        return date1;
    }

    /**
     * 获得当天日期，不含时分秒 yyyy/MM/dd
     *
     * @return
     */
    public static String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        String s = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        return s;
    }

    /**
     * 获得当天日期，不含时分秒 yyyy-MM-dd
     *
     * @return
     */
    public static String getTodayDate2() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        String s = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return s;
    }

    /**
     * 当前日期格式化
     *
     * @return
     */
    public static String getFormatToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        String s = new SimpleDateFormat("yyyy年MM月dd日").format(cal.getTime());
        // //System.out.println(s);
        return s;
    }

    public static int getDaysBetween(String sdate, String edate) {
        Calendar d1 = getCalendarByString(sdate);
        Calendar d2 = getCalendarByString(edate);
        return getDaysBetween(d1, d2);
    }

    /**
     * 计算2个日期之间的相隔天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDaysBetween(Calendar d1,
                                     Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR)
                - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * // 获取当前具体时间
     *
     * @return
     */
    public static String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static void main(String arg[]) {
        String str = getDateByStrHM("2012-11-12");
        // System.out.println("str==" + str);
    }
    //获取当前周几
    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
//        return mYear + "年" + mMonth + "月" + mDay + "日" + "/星期" + mWay;
        return "星期" + mWay;
    }
}