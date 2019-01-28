package com.cn.jm.web.plugin.message.msg;

import org.apache.log4j.Logger;

public interface MessageListener {
	
	public static Logger log = Logger.getLogger(MessageListener.class);

	public  void onMessage(Message message);

}
