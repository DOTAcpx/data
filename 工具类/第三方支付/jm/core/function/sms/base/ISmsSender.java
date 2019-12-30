package com.cn.jm.core.function.sms.base;

import org.apache.log4j.Logger;

public interface ISmsSender {
	public static Logger log = Logger.getLogger(ISmsSender.class);

	public void send(SmsMessage sms);

}
