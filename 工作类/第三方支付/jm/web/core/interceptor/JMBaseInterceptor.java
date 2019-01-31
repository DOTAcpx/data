package com.cn.jm.web.core.interceptor;

import javax.servlet.http.HttpSession;

import com.cn.jm.core.utils.util.JMUploadKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public abstract class JMBaseInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();

		String contentType = controller.getRequest().getContentType(); // 获取Content-Type
		if ((contentType != null) && (contentType.toLowerCase().startsWith("multipart/"))) {
			controller.getFile("", JMUploadKit.uploadTempPath, JMUploadKit.videoMaxSize);
		}

		if (!before(inv, controller))
			return;

		inv.invoke();

		after(inv, controller);
	}

	public abstract boolean before(Invocation inv, Controller controller);

	public abstract void after(Invocation inv, Controller controller);

	public HttpSession getSession(Controller controller) {
		return controller.getSession();
	}

}
