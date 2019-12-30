package com.cn.jm.core.tool;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.jm.api.chuanglan.HttpUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class ToolMessageUtil {

	private static Logger logger = Logger.getLogger(ToolMessageUtil.class);

	private final static String url="http://intapi.253.com/send/json";
	private final static String corpID = "I9800363";// 账户名
//	private final static String pwd = "URk1Dri2a";// 密码
	private final static String pwd = "gRI41l3tc";// 密码
//	public static final String REGEX_MOBILE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

	public static boolean sendCode(String mobile, int code) {
		String content = "【世界模特小姐大赛MMW】您本次的验证码为（Your verification code is)："+code;
		JSONObject result = send(mobile, content);
		if( result != null) {
			if ( result.get("code").toString().equals("0")) {
				return true;
			} else {
				logger.error("短信发送失败：" + mobile + ",错误码为：" + result.get("code").toString());
				return false;
			}
		}
		return false;
	}

	public static void sendMessage(String mobiles, String message) {
		String content = "尊敬的客户:您好！" + message;
		JSONObject result = send(mobiles, content);
		if( result != null) {
			if ( result.get("code").toString().equals("0")) {
			} else {
				logger.error("短信发送失败：" + mobiles + ",错误码为：" + result.get("code").toString());
			}
		}
	}

	public static boolean sendMessageToMaster(final String mobiles, String message) {
		String content = "尊敬的大师:您好！" + message;
		JSONObject result = send(mobiles, content);
		if( result != null) {
			if ( result.get("code").toString().equals("0")) {
				return true;
			} else {
				logger.error("短信发送失败：" + mobiles + ",错误码为：" + result.get("code").toString());
				return false;
			}
		}
		return false;
	}


	public static JSONObject send(String mobile,String msg) {
		//组装请求参数
		JSONObject map=new JSONObject();
		map.put("account", corpID);
		map.put("password", pwd);
		map.put("msg", msg);
		map.put("mobile", mobile);

		String params=map.toString();
		
//				logger.info("请求参数为:" + params);
		try {
			String result=HttpUtil.post(url, params);
			
//					logger.info("返回参数为:" + result);
			
			JSONObject jsonObject =  JSON.parseObject(result);
//			String code = jsonObject.get("code").toString();
//			String msgid = jsonObject.get("msgid").toString();
//			String error = jsonObject.get("error").toString();
			return jsonObject;
//			System.out.println("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
//		int code = (int) (Math.random() * 9000 + 1000);
		JSONObject jsonObject = send("8615767318059", "【世界模特小姐大赛MMW】您本次的验证码为（Your verification code is)：1234");

		String c = jsonObject.get("code").toString();
		String msgid = jsonObject.get("msgid").toString();
		String error = jsonObject.get("error").toString();
		System.out.println("状态码:" + c + ",状态码说明:" + error + ",消息id:" + msgid);
	}



	/**
     * 根据国家代码和手机号  判断手机号是否有效
     * @param phoneNumber 手机号
     * @param countryCode 国家手机代码
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber, String countryCode){

        int ccode = Integer.parseInt(countryCode);//StringUtil.toInteger(countryCode);
        long phone = Long.parseLong(phoneNumber);

        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);

        return phoneNumberUtil.isValidNumber(pn);

    }

}
