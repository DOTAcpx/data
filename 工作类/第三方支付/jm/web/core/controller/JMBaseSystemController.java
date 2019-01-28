package com.cn.jm.web.core.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.cn.jm.core.tool.security.ToolToken;
import com.cn.jm.web.core.JMConsts;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;

public class JMBaseSystemController extends JMBaseController {

	/**
	 * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
	 * 
	 * @return
	 */
	public boolean isRepeatSubmit() {
		String client_token = getPara("mToken");
		// 1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
		if (client_token == null) {
			return true;
		}
		// 取出存储在Session中的token
		String server_token = (String) getSession().getAttribute("mToken");
		// 2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
		if (server_token == null) {
			return true;
		}
		// 3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
		if (!client_token.equals(server_token)) {
			return true;
		}
		return false;
	}

	public void setToken() {
		String token = ToolToken.makeToken();
		getSession().setAttribute("mToken", token);
	}

	public void jump() {
		String parameter = getParameter();
		try {
			if (parameter != null) {
				setAttr("parameter", parameter);
				setAttr("redirect",
						URLEncoder.encode(URLEncoder.encode(getServletPath() + parameter, "utf-8"), "utf-8"));
			} else {
				setAttr("redirect", URLEncoder.encode(URLEncoder.encode(getServletPath(), "utf-8"), "utf-8"));
			}
			setAttr("path", getServletPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String tzUrl(String name) {
		String url = getPara(name);
		try {
			return URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void toSystemError(Controller controller, String error) {
		controller.setAttr("error", error);
		controller.render(JMConsts.base_view_url + "/system/error.jsp");
	}

	/**
	 * 判断后台是否已登录
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean isLogin(String sessionId) {
		return JMConsts.getTemporaryCacheAttr(sessionId) != null;
	}

	public <T> T getLoginAccount() {
		return getSessionAttr("account");
	}

	public void setLoginAccount(Model<?> model) {
		setSessionAttr("account", model);
	}

	public String getLoginSessionId() {
		return getSessionAttr("sessionId");
	}

	public void setLoginSessionId(String sessionId) {
		setSessionAttr("sessionId", sessionId);
	}

	public <T> T getLoginRole() {
		return getSessionAttr("role");
	}

	public void setLoginRole(Model<?> role) {
		setSessionAttr("role", role);
	}

	public boolean isSuper() {
		return getSessionAttr("isSuper", false);
	}

}
