package com.cn.jm.core.utils.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;

/**
 * 用于支持java8的time api
 *
 * @author liuzhou create at 2017-03-17 15:15
 */
public class TimeUtil {

	/**
	 * 根据实例获取DateTimeFormatter
	 */
	public static DateTimeFormatter getDateTimeFormatter(Object data) {
		if (!(data instanceof TemporalAccessor)) {
			throw new RuntimeException("Arg Error:Type should be TemporalAccessor");
		}
		if (data instanceof LocalDate) {
			return DateTimeFormatter.ofPattern("yyyy-MM-dd");
		}
		if (data instanceof LocalTime) {
			return DateTimeFormatter.ofPattern("HH:mm:ss");
		}
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据类型获取DateTimeFormatter
	 */
	public static DateTimeFormatter getDateTimeFormatter(Class<?> clazz) {
		if (!(TemporalAccessor.class.isAssignableFrom(clazz))) {
			throw new RuntimeException("clazz应该是TemporalAccessor或其子类型");
		}
		if (clazz == LocalDate.class) {
			return DateTimeFormatter.ofPattern("yyyy-MM-dd");
		}
		if (clazz == LocalTime.class) {
			return DateTimeFormatter.ofPattern("HH:mm:ss");
		}
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将传入的时间段判断是否为空,如果传入的时间段为空则不添加
	 * 如果传入的时间段不为空则将拆分成开始时间和结束时间
	 * 将时间加入到传入的map的判断条件中
	 * @param theTime 传入的时间段
	 * @param sql 传入的sql语句
	 * @param startTimeKey 传入开始时间的条件
	 * @param endTimeKey 传入结束时间的条件
	 * cpx
	 */
	public static void addWhereParamTime(String theTime,HashMap<String,Object> whereParamMap,String startTimeKey,String endTimeKey){
		if (theTime != null && !"".equals(theTime.trim())) {
			String theTimes[] = theTime.split(" - ");//将时间进行拆分
			String startTime = theTimes[0];//0为开始时间
			String endTime = theTimes[1];//1为结束时间
			if (startTime != null && endTime != null) {
				whereParamMap.put(startTimeKey,startTime);
				whereParamMap.put(endTimeKey,endTime);
			}
		}
	}
	
}
