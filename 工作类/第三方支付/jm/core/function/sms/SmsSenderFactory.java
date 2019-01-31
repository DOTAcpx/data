package com.cn.jm.core.function.sms;

import com.cn.jm.core.function.sms.base.ISmsSender;
import com.cn.jm.core.function.sms.base.SmsMessage;
import com.cn.jm.core.function.sms.chuanglan.CLSmsSender;

public class SmsSenderFactory {
	
	/**
	 * 
	 * @param account: 账号
	 * @param password 密码
	 * @param mobiles 手机号码，多个号码使用","分割
	 * @param content 短信内容
	 */
	public static void CLSend(String account,String password,String mobiles,String content){
		String url = "http://sapi.253.com/msg/";// 应用地址
//		String account = "vipxprj__gxyyg";// 账号
//		String password = "Gxyyg123456";// 密码
//		String mobile = mobiles;// 手机号码，多个号码使用","分割
//		String msg = content;// 短信内容
		boolean needstatus = false;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码
		
		SmsMessage sms = new SmsMessage();
		sms.setAccount(account);
		sms.setPassword(password);
		sms.setUrl(url);
		sms.setContent(content);
		sms.setNeedstatus(needstatus);
		sms.setExtno(extno);
		
		ISmsSender sender = new CLSmsSender();
		sender.send(sms);
	}
	

}
