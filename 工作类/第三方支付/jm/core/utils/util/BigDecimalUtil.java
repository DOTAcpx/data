package com.cn.jm.core.utils.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	/**
	 * 
	 * @Description:o1是否大于o2
	 * @param o1
	 * @param o2
	 */
	public static boolean isBig(BigDecimal o1, BigDecimal o2) {
		return o1.compareTo(o2) > 0 ? true : false;
	}

	/**
	 * 
	 * @Description: o1是否大于等于o2
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isBigAndEqual(BigDecimal o1, BigDecimal o2) {
		return o1.compareTo(o2) >= 0 ? true : false;
	}

	/**
	 * 
	 * @Description: o1是否小于等于o2
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isSmallAndEqual(BigDecimal o1, BigDecimal o2) {
		return o1.compareTo(o2) <= 0 ? true : false;
	}

	/**
	 * 
	 * @Description: o1是否小于o2
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isSmall(BigDecimal o1, BigDecimal o2) {
		return o1.compareTo(o2) < 0 ? true : false;
	}

	/**
	 * 
	 * @Description: o1是否等于o2
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isEquals(BigDecimal o1, BigDecimal o2) {
		return o1.compareTo(o2) == 0 ? true : false;
	}

}
