package com.cn.jm.core.function.sms.base;

import com.cn.jm.core.function.sms.chuanglan.CLSmsSender;
import com.cn.jm.web.plugin.message.annotation.Listener;
import com.cn.jm.web.plugin.message.msg.Message;
import com.cn.jm.web.plugin.message.msg.MessageListener;

@Listener(action ={SmsListener.SMS_CL}, async = false)
public class SmsListener implements MessageListener{
	
	public static final String SMS_CL = "chuanglan";

	@Override
	public void onMessage(Message message) {
		if (message.getAction().equals(SmsListener.SMS_CL)) {//创蓝短信
			SmsMessage sms = message.getData();
			CLSmsSender.doSend(sms);
			
		}
		
	}

}
