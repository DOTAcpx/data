package com.cn.jm.web.core.interceptor;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class JMCommonInterceptor extends JMBaseInterceptor {

	@Override
	public boolean before(Invocation inv, Controller controller) {

		return true;
	}

	@Override
	public void after(Invocation inv, Controller controller) {
	}

}
