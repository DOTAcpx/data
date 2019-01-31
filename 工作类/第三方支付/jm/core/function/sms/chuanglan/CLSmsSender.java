package com.cn.jm.core.function.sms.chuanglan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.cn.jm.core.function.sms.base.ISmsSender;
import com.cn.jm.core.function.sms.base.SmsListener;
import com.cn.jm.core.function.sms.base.SmsMessage;
import com.cn.jm.web.plugin.message.JMMessageKit;

public class CLSmsSender implements ISmsSender {

	@Override
	public void send(SmsMessage sms) {
		JMMessageKit.sendMessage(SmsListener.SMS_CL, sms);
	}

	public static String doSend(SmsMessage sms) {
		return batchSend(sms.getUrl(), sms.getAccount(), sms.getPassword(), sms.getMobiles(), sms.getContent(),
				sms.isNeedstatus(), sms.getExtno());
	}

	public static String batchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String extno) {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		String resultStr = null;
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), new NameValuePair("msg", msg),
					new NameValuePair("extno", extno), });
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				resultStr = URLDecoder.decode(baos.toString(), "UTF-8");
				return resultStr;
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} catch (URIException e) {
			e.printStackTrace();
			log.info(e.toString());
		} catch (HttpException e) {
			e.printStackTrace();
			log.info(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.toString());
		} finally {
			method.releaseConnection();
		}
		return resultStr;
	}

}
