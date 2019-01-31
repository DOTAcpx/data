package com.cn.jm.web.core.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import com.cn.jm.core.utils.util.JMResult;
import com.cn.jm.web.core.valid.annotation.JMRuleVaild;
import com.cn.jm.web.core.valid.annotation.JMRulesVaild;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class JMVaildInterceptor extends JMBaseInterceptor {

	@Override
	public boolean before(Invocation inv, Controller controller) {

		Method method = inv.getMethod();

		return this.vaild(method, controller);
	}

	@Override
	public void after(Invocation inv, Controller controller) {

	}

	private boolean vaild(Method method, Controller controller) {

		boolean isAnnotationJMRulesVaild = method.isAnnotationPresent(JMRulesVaild.class);

		boolean result = true;

		if (isAnnotationJMRulesVaild) {

			JMRulesVaild rulesVaild = method.getAnnotation(JMRulesVaild.class);

			JMRuleVaild[] ruleVailds = rulesVaild.value();

			for (JMRuleVaild ruleVaild : ruleVailds) {
				result = this.vaild(ruleVaild, controller);
				if (!result) {
					break;
				}
			}

		}

		boolean isJMRuleVaild = method.isAnnotationPresent(JMRuleVaild.class);

		if (isJMRuleVaild) {
			JMRuleVaild ruleVaild = method.getAnnotation(JMRuleVaild.class);
			result = this.vaild(ruleVaild, controller);
		}
		return result;
	}

	private boolean vaild(JMRuleVaild ruleVaild, Controller controller) {

		try {

			Method vaidMethod = ruleVaild.ruleClass().getMethod("vaildParam", Map.class, String[].class, String[].class,
					boolean.class, String[].class, String[].class, String[].class);
			JMResult result = (JMResult) vaidMethod.invoke(ruleVaild.ruleClass().newInstance(), controller.getParaMap(),
					ruleVaild.fields(), ruleVaild.exclusive(), ruleVaild.isAll(), ruleVaild.maxlength(),
					ruleVaild.minlength(), ruleVaild.regex());

			if (JMResult.FAIL == result.getCode()) {
				result.renderJson(controller);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
