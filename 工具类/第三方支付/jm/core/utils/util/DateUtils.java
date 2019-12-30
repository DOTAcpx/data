package com.cn.jm.core.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

	public static String FORMAT_SHORT = "yyyy-MM-dd";

	public static String FORMAT_MOTH = "yyyyMM";

	/**
	 * 功能描述：返回月
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @Description: 获取日期的月份
	 * @param date
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public static String getMonthString(Date date, int offset) {

		date = addMonth(date, offset);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		DateFormat format = new SimpleDateFormat(FORMAT_MOTH);
		return format.format(date);
	}

	public static String getMonthString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		DateFormat format = new SimpleDateFormat(FORMAT_MOTH);
		return format.format(date);
	}

	/**
	 * 功能描述：返回日
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @author lgk 功能描述：返回小
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @author lgk 功能描述：返回分
	 *
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @author lgk 返回秒钟
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 * 
	 * @throws ParseException
	 */
	public static Date stringToDate(String strDate, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用预设格式将字符串转为Date
	 * 
	 * @throws ParseException
	 */
	public static String datetoString(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * @author lgk 加减天数
	 *
	 * @param date
	 *            Date 日期
	 * @param int
	 *            dateNum 天数
	 * @return 日期
	 */
	public static Date addDate(Date date, int dateNum, String pattern) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dateNum);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		if (null != pattern) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);// 小写的mm表示的是分钟
			String dateString = sdf.format(date);
			return stringToDate(dateString, pattern);
		} else {
			return date;
		}
	}

	/**
	 * @author lgk 加减月份
	 *
	 * @param date
	 *            Date 日期
	 * @param int
	 *            monthNum 月数
	 * @return 日期
	 */
	public static Date addMonth(Date date, int monthNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthNum);
		date = calendar.getTime();
		return date;
	}

	/**
	 * @author lgk 加减年份
	 *
	 * @param date
	 *            Date 日期
	 * @param int
	 *            year 年数
	 * @return 日期
	 */
	public static Date addYear(Date date, int yearNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, yearNum);
		date = calendar.getTime();
		return date;
	}

	/**
	 * @author lgk 加减秒数
	 *
	 * @param date
	 *            Date 日期
	 * @param int
	 *            dateNum 天数
	 * @return 日期
	 */
	public static Date addSecond(Date date, int secondNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, secondNum);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取工作日
	 * 
	 * @param d1
	 * @param d2
	 * @return 工作日天数
	 */
	public int getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
		int result = -1;
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int charge_start_date = 0;// 开始日期的日期偏移量
		int charge_end_date = 0;// 结束日期的日期偏移量
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
			charge_end_date = etmp - 1;
		}
		// }
		result = (getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)) / 7) * 5 + charge_start_date
				- charge_end_date;
		return result;
	}

	/**
	 * 获取休息日
	 * 
	 * @param d1
	 * @param d2
	 * @return 休息日天数
	 */
	public int getHolidays(Calendar d1, Calendar d2) {
		return this.getDaysBetween(d1, d2) - this.getWorkingDay(d1, d2);
	}

	/**
	 * 获取日期之间的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	public static int getDaysBetween(Date day1, Date day2) {

		Calendar d1 = Calendar.getInstance();
		d1.setTime(day1);

		Calendar d2 = Calendar.getInstance();
		d2.setTime(day2);

		if (d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}

		int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}

		return days;
	}

	/**
	 * 获得日期的下一个星期一的日期
	 * 
	 * @param date
	 * @return
	 */
	public Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 获取中文日期
	 * 
	 * @param date
	 * @return
	 */
	public String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];
	}

	/**
	 * 判断给定日期是否为月末的一天
	 * 
	 * @author lgk
	 * @param date
	 * @return true:是|false:不是
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定日期是否为月初
	 * 
	 * @author LGK
	 * @param date
	 * @return true:是|false:不是
	 */
	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int first = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		return first == calendar.get(Calendar.DAY_OF_MONTH) ? true : false;
	}

	/**
	 * 
	 * @date 2018年1月30日 上午10:34:54
	 * @author LGK
	 * @Description: 当月第一天
	 * @return
	 *
	 */
	public static Date getStartMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date startOfDate = calendar.getTime();
		return startOfDate;
	}

	/**
	 * 
	 * @date 2018年1月30日 上午10:34:40
	 * @author LGK
	 * @Description: 当月最后一天 23:59:59
	 * @return
	 *
	 */
	public static Date getEndMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 
	 * @date 2018年1月30日 上午10:34:40
	 * @author LGK
	 * @Description: 当月最后一天 0:0:0
	 * @return
	 *
	 */
	public static Date getEndMonthDawn() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 
	 * @date 2018年1月30日 上午10:34:40
	 * @author LGK
	 * @Description: 当月最后一天 0:0:0
	 * @return
	 *
	 */
	public static Date getEndMonthDawn(int addNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.add(Calendar.MONTH, addNum);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 得到当天零点时间
	 * 
	 * @return
	 */

	public static Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 得到以今天为标准的偏移量的 0点
	 * 
	 * @return
	 */

	public static Date getStartDate(Integer dayOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, dayOffset);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 得到以今天为标准的偏移量的 23.59.59
	 * 
	 * @return
	 */
	public static Date getEndDay(Integer dayOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.add(Calendar.DATE, dayOffset);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 得到当天23.59.59
	 * 
	 * @return
	 */
	public static Date getEndDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 
	 * @date 2018年1月31日 下午8:30:24
	 * @author LGK
	 * @Description:查询补全 秒数 ->针对datetime 2018-01-31 -> 2018-01-31 23:59:59
	 * @param end
	 * @return
	 *
	 */
	public static Date addMs(Date end) {
		if (end == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();

	}

	/**
	 * 
	 * @date 2018年3月29日 下午2:20:16
	 * @author LGK
	 * @Description: 凌晨算昨日
	 * @param date
	 * @return
	 *
	 */
	public static Boolean isTodayBeforeDawn(Date date) {

		return isBetween(getStartDate(-1), getEndDay(-1), date);
	}

	/**
	 * 
	 * @date 2018年3月29日 下午2:20:16
	 * @author LGK
	 * @Description: 是否是当月
	 * @param date
	 * @return
	 *
	 */
	public static Boolean isMoth(Date date) {
		return isBetween(getStartMonth(), getEndMonth(), date);
	}

	/**
	 * @author LGK
	 * @Description: 是否是当天
	 * @param date
	 * @return
	 */
	public static Boolean isToday(Date date) {

		return isBetween(getStartDate(), getEndDay(), date);
	}

	public static Boolean isBetween(Date startTime, Date endTime, Date date) {

		if (startTime.getTime() <= date.getTime() && endTime.getTime() >= date.getTime()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 
	 * @Description: 现在日期偏移量
	 * @param min
	 * @param type
	 * @return
	 */
	public static Date offsetDate(Integer num, Integer type) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		calendar.add(type, num);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 
	 * @Description: 指定日期偏移
	 * @param date
	 * @param num
	 * @param type
	 * @return
	 */
	public static Date offsetDate(Date date, Integer num, Integer type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, num);
		Date beginOfDate = calendar.getTime();
		return beginOfDate;
	}

	/**
	 * 
	 * @Description:两个时间相差分钟
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	public static long daysMinute(Date smdate, Date bdate) {
		long l = bdate.getTime() - smdate.getTime();
		long minute = l / (60 * 1000);
		return minute;
	}

	/**
	 * 
	 * @Description:两个时间相差秒
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	public static long daysSecond(Date smdate, Date bdate) {
		long l = bdate.getTime() - smdate.getTime();
		long minute = l / 1000;
		return minute;
	}

	public static void main(String[] args) {

		// Date date = new Date();
		// System.out.println(date);
		// System.out.println(offsetDate(date, 31, Calendar.DATE));

		// Date date = new Date();
		// Date date1 = offsetDate(date, 2, Calendar.MINUTE);
		//
		// System.out.println(daysMinute(date, date1));
		//
		// System.out.println(daysSecond(date, date1));
		//
		// System.out.println(getMonthString(new Date(), -1));
		//
		// Date last = getEndMonthDawn(-1);
		// Date last1 = getEndMonth();
		// System.out.println(last1);
		// System.out.println(last);
		//
		// int day = getDaysBetween(new Date(), last);
		//
		// System.out.println(day);

		System.out.println(DateUtils.offsetDate(new Date(), 1, Calendar.DATE));

	}

}
