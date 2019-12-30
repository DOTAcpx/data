package com.cn.jm.web.plugin.message;

import java.util.List;

import com.cn.jm.core.utils.util.ClassScaner;
import com.cn.jm.web.plugin.message.msg.MessageListener;
import com.cn.jm.web.plugin.message.msg.MessageManager;
import com.jfinal.plugin.IPlugin;

public class MessagePlugin implements IPlugin {


	@Override
	public boolean start() {
		autoRegister();
		return true;
	}

	private void autoRegister() {
		List<Class<MessageListener>> list = ClassScaner.scanSubClass(MessageListener.class, true);
		if (list != null && list.size() > 0) {
			for (Class<MessageListener> clazz : list) {
				MessageManager.me().registerListener(clazz);
			}
		}
	}

	@Override
	public boolean stop() {
		return true;
	}

}
