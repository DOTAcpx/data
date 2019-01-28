package com.cn.jm.web.core.handle;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jfinal.handler.Handler;
import com.jfinal.kit.JsonKit;

public class RequestParamHandler extends Handler{
	
	private static Logger log = Logger.getLogger(RequestParamHandler.class);

	@Override
	public void handle(String target, HttpServletRequest request,HttpServletResponse response, boolean[] isHandled) {
		Map<String, String[]> paramsMap = request.getParameterMap();
		 System.err.println(JsonKit.toJson(paramsMap));
		 for (Entry<String, String[]> entry : paramsMap.entrySet()) {
			 String key = entry.getKey();
			 String[] params = entry.getValue();
			 System.err.println(key+"---"+JsonKit.toJson(params));
			 if (params == null) {
				 
			 }
		 }
	}

}
