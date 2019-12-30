package com.cn.jm.web.core.interceptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.cn.jm.core.tool.ToolDateTime;
import com.cn.jm.web.core.controller.JMBaseController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 参数封装拦截器
 * 
 * @author 董华健
 */
public class ParamPkgInterceptor implements Interceptor {

	private static Logger log = Logger.getLogger(ParamPkgInterceptor.class);

	@Override
	public void intercept(Invocation inv) {
		JMBaseController controller = (JMBaseController) inv.getController();

		Class<?> controllerClass = controller.getClass();
		Class<?> superControllerClass = controllerClass.getSuperclass();

		Field[] fields = controllerClass.getDeclaredFields();
		Field[] parentFields = superControllerClass.getDeclaredFields();

		log.info("*********************** 封装参数值到 controller 全局变量  start ***********************");

		// 封装controller变量值
		for (Field field : fields) {
			setControllerFieldValue(controller, field);
		}

		// 封装JMBaseController变量值
		for (Field field : parentFields) {
			setControllerFieldValue(controller, field);
		}

		log.info("*********************** 封装参数值到 controller 全局变量  end ***********************");

		inv.invoke();

		log.info("*********************** 设置全局变量值到 request start ***********************");

		// 封装controller变量值
		for (Field field : fields) {
			setRequestValue(controller, field);
		}

		// 封装JMBaseController变量值
		for (Field field : parentFields) {
			setRequestValue(controller, field);
		}

		log.info("*********************** 设置全局变量值到 request end ***********************");
	}

	/**
	 * 反射set值到全局变量
	 * 
	 * @param controller
	 * @param field
	 */
	public void setControllerFieldValue(JMBaseController controller, Field field) {
		try {
			field.setAccessible(true);
			String name = field.getName();
			String value = controller.getPara(name);
			if (null == value || value.trim().isEmpty()) {// 参数值为空直接结束
				log.debug("封装参数值到全局变量：field name = " + name + " value = 空");
				return;
			}
			log.debug("封装参数值到全局变量：field name = " + name + " value = " + value);

			String fieldType = field.getType().getSimpleName();
			if (fieldType.equals("String")) {
				field.set(controller, value);

			} else if (fieldType.equals("int")) {
				field.set(controller, Integer.parseInt(value));

			} else if (fieldType.equals("Date")) {
				int dateLength = value.length();
				if (dateLength == ToolDateTime.pattern_ymd.length()) {
					field.set(controller, ToolDateTime.parse(value, ToolDateTime.pattern_ymd));

				} else if (dateLength == ToolDateTime.pattern_ymd_hms.length()) {
					field.set(controller, ToolDateTime.parse(value, ToolDateTime.pattern_ymd_hms));

				} else if (dateLength == ToolDateTime.pattern_ymd_hms_s.length()) {
					field.set(controller, ToolDateTime.parse(value, ToolDateTime.pattern_ymd_hms_s));
				}

			} else if (fieldType.equals("BigDecimal")) {
				BigDecimal bdValue = new BigDecimal(value);
				field.set(controller, bdValue);

			} else {
				log.debug("没有解析到有效字段类型");
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * 反射全局变量值到request
	 * 
	 * @param controller
	 * @param field
	 */
	public void setRequestValue(JMBaseController controller, Field field) {
		try {
			field.setAccessible(true);
			String name = field.getName();
			Object value = field.get(controller);
			if (null == value || (value instanceof String && ((String) value).isEmpty())) {// 参数值为空直接结束
				log.debug("设置全局变量到request：field name = " + name + " value = 空");
				return;
			}
			log.debug("设置全局变量到request：field name = " + name + " value = " + value);
			controller.setAttr(name, value);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} finally {
			field.setAccessible(false);
		}
	}

}
