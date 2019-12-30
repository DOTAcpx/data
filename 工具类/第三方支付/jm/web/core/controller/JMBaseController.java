package com.cn.jm.web.core.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.cn.jm.core.tool.JMToolString;
import com.cn.jm.core.tool.JMToolWeb;
import com.cn.jm.core.utils.json.FastJsonLongSerializer;
import com.cn.jm.core.utils.json.JfinalJsonLongSerializer;
import com.cn.jm.core.utils.json.JsonPareType;
import com.cn.jm.core.utils.json.MyJFinalJson;
import com.cn.jm.core.utils.util.I18NUtils;
import com.cn.jm.core.utils.util.JMResult;
import com.cn.jm.core.utils.util.JsoupUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.NotAction;
import com.jfinal.i18n.Res;

public class JMBaseController extends Controller {

	public static Logger log = Logger.getLogger(JMBaseController.class);

	static {
		SerializeConfig.getGlobalInstance().put(Long.class, new FastJsonLongSerializer());

		MyJFinalJson.put(Long.class, new JfinalJsonLongSerializer());
	}

	/**
	 * 解决IE8下下载失败的问题
	 */
	public void renderFile(File file) {
		getResponse().reset();
		super.renderFile(file);
	}

	/**
	 * 是否是IE浏览器
	 * 
	 * @return
	 */
	public boolean isIEBrowser() {
		String ua = getRequest().getHeader("User-Agent").toLowerCase();
		if (ua != null && ua.indexOf("msie") > 0) {
			return true;
		}
		if (ua != null && ua.indexOf("gecko") > 0 && ua.indexOf("rv:11") > 0) {
			return true;
		}
		return false;
	}

	public boolean isAjaxRequest() {
		String header = getRequest().getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(header);
	}

	public boolean isMultipartRequest() {
		String contentType = getRequest().getContentType();
		return contentType != null && contentType.toLowerCase().indexOf("multipart") != -1;
	}

	public void setHeader(String key, String value) {
		getResponse().setHeader(key, value);
	}

	@Before(NotAction.class)
	public String getUserAgent() {
		return getRequest().getHeader("User-Agent");
	}

	/**
	 * 获取当前项目的访问路径
	 */
	public String getProjectUrl(HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";
		return baseUrl;
	}

	/**
	 * request流转字符串
	 * 
	 * @return
	 */
	protected String getRequestContent() {
		return JMToolWeb.requestStream(getRequest());
	}

	protected String getIp() {
		return JMToolWeb.getIpAddr(getRequest());
	}

	@Override
	public String getPara(String name) {
		return JsoupUtils.clear(getRequest().getParameter(name));
	}

	/**
	 * 获取访问参数
	 */
	public String getParameter() {
		String qs = getRequest().getQueryString();
		if (JMToolString.isNotEmpty(qs)) {
			return "?" + qs;
		}
		return null;
	}

	public String getServletPath() {
		return getRequest().getServletPath();
	}

	public String getRedirectUrl() {
		String url = getRequest().getServletPath();
		String qs = getRequest().getQueryString();
		if (qs != null) {
			url = url + "?" + (getRequest().getQueryString());
		}
		return url;
	}

	/**
	 * 
	 * @Description: api i18n
	 * @param result
	 * @param key
	 *            前段的语言
	 */
	public void renderJsonI18nDesc(JMResult result, String key) {

		Res resEn = I18NUtils.getRes(key);

		String desc = result.getDesc();
		// 直接获取数据
		desc = resEn.get(desc);
		// 获取数据并使用参数格式化
		// String msgEnFormat = resEn.format("msg", "james", new Date());
		result.setDesc(desc);

		this.renderJson(JsonPareType.FAST_JSON, result);
	}

	/**
	 * 
	 * @Description: api i18n
	 * @param result
	 * @param key
	 *            前段的语言
	 */
	public void renderJsonI18nDesc(JMResult result) {

		String key = getPara("language");

		Res resEn = I18NUtils.getRes(key);

		String desc = result.getDesc();
		// 直接获取数据
		desc = resEn.get(desc);
		// 获取数据并使用参数格式化
		// String msgEnFormat = resEn.format("msg", "james", new Date());
		result.setDesc(desc);

		this.renderJson(JsonPareType.MY_JFINAL_JSON, result);
	}

	@SuppressWarnings("unused")
	public void renderJson(JsonPareType type, Object obj) {
		if (this == null) {
			throw new UnsupportedOperationException("");
		} else {
			// renderJson(FastJson.getJson().toJson(obj));
			super.renderJson(type.getJson().toJson(obj));
		}
	}

}
