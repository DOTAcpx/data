package com.cn.jm.web.plugin.message;

import com.cn.jm.web.plugin.message.msg.Message;
import com.cn.jm.web.plugin.message.msg.MessageListener;
import com.cn.jm.web.plugin.message.msg.MessageManager;

public class JMMessageKit {

	public static void register(Class<? extends MessageListener> listenerClass) {
		MessageManager.me().registerListener(listenerClass);
	}
	
	public static void unRegister(Class<? extends MessageListener> listenerClass) {
		MessageManager.me().unRegisterListener(listenerClass);
	}

	public static void sendMessage(Message message) {
		MessageManager.me().pulish(message);
	}

	public static void sendMessage(String action, Object data) {
		MessageManager.me().pulish(new Message(action, data));
	}

	public static void sendMessage(String action) {
		MessageManager.me().pulish(new Message(action, null));
	}

}
