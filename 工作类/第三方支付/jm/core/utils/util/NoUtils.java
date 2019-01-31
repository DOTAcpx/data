package com.cn.jm.core.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.cn.jm.web.core.dao.JMBaseDao;

public class NoUtils {

	/**
	 * 生成订单号
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		key = key + String.valueOf(JMBaseDao.getSnowflake().nextId()).substring(6, 14);
		return key;
	}

	/**
	 * 生成订单号
	 */
	public static String getOutTradeNo(String pre) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		key = key + String.valueOf(JMBaseDao.getSnowflake().nextId()).substring(6, 14);
		return key;
	}

	public static String getInvitationCode(int length) {
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Description:生成指定长度的随机数
	 * @param length
	 * @return
	 */
	public static String getCode(int length) {
		String code = "";
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			int str = random.nextInt(9) + 1;
			code += str;
		}
		return code;
	}

	/**
	 * 
	 * @Description: 生成UUID
	 * @param is32bit
	 * @return
	 */
	public static String getUuidByJdk(boolean is32bit) {
		String uuid = UUID.randomUUID().toString();
		return is32bit ? uuid.toString().replace("-", "") : uuid;
	}

}
