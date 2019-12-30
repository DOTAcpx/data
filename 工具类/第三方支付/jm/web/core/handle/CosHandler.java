package com.cn.jm.web.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class CosHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest req, HttpServletResponse res, boolean[] isHandled) {

//		String[] allowDomain = { "http://localhost:8080", "http://localhost:8081", "http://1929u21c31.imwork.net",
//				"http://10.0.0.183:8081", "http://10.0.0.225:8081", "http://10.0.0.92:8081","http://10.0.0.168:8080" };
//		Set<String> allowedOrigins = new HashSet<>(Arrays.asList(allowDomain));
		String originHeader = req.getHeader("Origin");
//		if (allowedOrigins.contains(originHeader)) {
			res.setHeader("Access-Control-Allow-Origin", originHeader);
			res.setContentType("application/json;charset=UTF-8");
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			res.setHeader("Access-Control-Max-Age", "3600");
			// res.setHeader("Access-Control-Allow-Headers","Origin, No-Cache,
			// X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control,
			// Expires, Content-Type, X-E4M-With,userId,token");// 表明服务器支持的所有头信息字段
			res.setHeader("Access-Control-Allow-Credentials", "true"); // 如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true;
			res.setHeader("XDomainRequestAllowed", "1");
//		}
		if ("OPTIONS".equals(res.getHeader("Access-Control-Allow-Methods"))) {
			return;
		}
//		res.setHeader("Access-Control-Allow-Origin", "*");
//		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		res.setHeader("Access-Control-Max-Age", "3600");
//		res.setHeader("Access-Control-Allow-Headers", "*");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
//		res.addHeader("Access-Control-Allow-Origin", "*");
//		 res.setHeader("Access-Control-Allow-Headers","Origin, No-Cache,X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control,Expires, Content-Type, X-E4M-With,userId,token");// 表明服务器支持的所有头信息字段

		next.handle(target, req, res, isHandled);
//
//		
//		 next.handle(target, req, res, isHandled);

	}

}
