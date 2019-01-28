package com.cn.jm.core.pay.wechat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;

import com.cn.jm.core.emun.WechatTradeType;
import com.cn.jm.core.pay.PayException;
import com.cn.jm.core.utils.util.AmountUtils;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;

import net.sf.json.JSONObject;

public class WechatPay {

	private static String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//退款
	private static String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//汇率
	public static String exchangeRateUrl ="https://api.mch.weixin.qq.com/pay/queryexchagerate";
	
	private String key = "";
	private SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	/**
	 * APP支付
	 * 
	 * @param out_trade_no
	 * @param body
	 * @param total_fee
	 * @param spbill_create_ip
	 * @return
	 * @throws PayException
	 */
	public static Map<String, String> app(String appKey, String appId, String mchId, String out_trade_no, String body,
			String total_fee, String spbill_create_ip, String url,String trade_type,String openId) throws PayException {
		total_fee = AmountUtils.changeY2F(total_fee);
//		String trade_type = "APP";
		Map<String, String> data = new HashMap<String, String>();

		try {
			data = weichatPay(appKey, appId, mchId, body, out_trade_no, total_fee, spbill_create_ip, url, trade_type,openId);
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		System.out.println(data);
		String return_code = data.get("return_code");
		String return_msg = data.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			throw new PayException(return_msg);
		}
		String result_code = data.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			throw new PayException(return_msg);
		}
		Map<String, String> map = new HashMap<String, String>();
		if(trade_type.equals(WechatTradeType.APP.name())) {
			map.put("appid", data.get("appid"));// 应用ID
			map.put("partnerid", data.get("mch_id"));// 商户号
			map.put("prepayid", data.get("prepay_id"));// 预支付交易会话ID
			map.put("package", "Sign=WXPay");// 扩展字段
			map.put("noncestr", System.currentTimeMillis() + "");// 随机字符串
			map.put("timestamp", Long.toString(new Date().getTime() / 1000));// 时间戳
			map.put("sign", data.get("sign"));// 签名
			String sign = PaymentKit.createSign(map, appKey);
			map.put("sign", sign);
		}else if(trade_type.equals(WechatTradeType.JSAPI.name())) {
		    map.put("appId", data.get("appid"));// 应用ID
		    // map.put("partnerid", data.get("mch_id"));//商户号
		    // map.put("prepayid", data.get("prepay_id"));//预支付交易会话ID
		    map.put("package", "prepay_id=" + data.get("prepay_id"));// 扩展字段
		    map.put("nonceStr", System.currentTimeMillis() + "");// 随机字符串
		    map.put("signType", "MD5");// 签名方式
		    map.put("timeStamp", Long.toString(new Date().getTime() / 1000));// 时间戳
		    // map.put("sign", data.get("sign"));//签名
		    String sign = PaymentKit.createSign(map, appKey);
		    map.put("paySign", sign);
		    map.put("total_fee", total_fee);
		}else if(trade_type.equals(WechatTradeType.NATIVE.name())) {//扫码支付
//			map.put("appid", data.get("appid"));// 应用ID
//			map.put("partnerid", data.get("mch_id"));// 商户号
//			map.put("prepayid", data.get("prepay_id"));// 预支付交易会话ID
//			map.put("package", "Sign=WXPay");// 扩展字段
//			map.put("noncestr", System.currentTimeMillis() + "");// 随机字符串
//			map.put("timestamp", Long.toString(new Date().getTime() / 1000));// 时间戳
//			map.put("sign", data.get("sign"));// 签名
//			String sign = PaymentKit.createSign(map, appKey);
//			map.put("sign", sign);
			map.put("codeUrl", data.get("code_url"));
		}
		return map;
	}
	
	

	/**
	 * 
	 * @param key
	 *            商家key
	 * @param appid
	 *            应用ID
	 * @param mch_id
	 *            商户号
	 * @param nonce_str
	 *            随机字符串
	 * @param body
	 *            商品或支付单简要描述
	 * @param out_trade_no
	 *            总金额
	 * @param total_fee
	 *            总金额
	 * @param spbill_create_ip
	 *            终端IP
	 * @param notify_url
	 *            通知地址
	 * @param trade_type
	 * @param openId 
	 */
	public static Map<String, String> weichatPay(String key, String appid, String mch_id, String body,
			String out_trade_no, String total_fee, String spbill_create_ip, String notify_url, String trade_type, String openId)
			throws JDOMException, IOException {
		WechatPay pay = new WechatPay();
		pay.setKey(key);
		pay.setParams(appid, mch_id, body, out_trade_no, total_fee, spbill_create_ip, notify_url, trade_type,openId);
		return pay.doRequest();
	}
	
	


	/**
	 * 设置商户Key
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 
	 * @param appid
	 *            应用ID
	 * @param mch_id
	 *            商户号
	 * @param nonce_str
	 *            随机字符串
	 * @param sign
	 *            签名
	 * @param body
	 *            商品或支付单简要描述
	 * @param out_trade_no
	 *            总金额
	 * @param total_fee
	 *            总金额
	 * @param spbill_create_ip
	 *            终端IP
	 * @param notify_url
	 *            通知地址
	 * @param trade_type
	 * @param openId 
	 * @param openId 
	 */
	public void setParams(String appid, String mch_id, String body, String out_trade_no, String total_fee,
			String spbill_create_ip, String notify_url, String trade_type, String openId) {
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", System.currentTimeMillis() + "");
		parameters.put("body", body);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		if(openId != null) {
			parameters.put("openid", openId);
		}
		parameters.put("sign", createSign("UTF-8", parameters));
	}
	public Map<String, String> doRequest() throws JDOMException, IOException {
		String requestXML = getRequestXml(parameters);
		String result = httpsRequest(url, "POST", requestXML);
		return XMLUtil.doXMLParse(result);
	}
	
	public Map<String, String> doRequest(SortedMap<Object, Object> map,String refundUrl) throws JDOMException, IOException {
		
		String requestXML = getRequestXml(map);
		String result = httpsRequest(refundUrl, "POST", requestXML);
		return XMLUtil.doXMLParse(result);
	}

	private String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding);
		return sign;
	}

	private String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			System.err.println("连接超时：{}" + ce);
		} catch (Exception e) {
			System.err.println("https请求异常：{}" + e);
		}
		return null;
	}
	
	/**
	 * APP支付
	 * 
	 * @param out_trade_no
	 * @param body
	 * @param total_fee
	 * @param spbill_create_ip
	 * @return
	 * @throws PayException
	 */
	public static Map<String, String> appRefund(String appKey, String appId, String mchId,String out_refund_no, String body,
			String total_fee,String refund_fee,String transaction_id,String certificatePath) throws PayException {
		total_fee = AmountUtils.changeY2F(total_fee);
		refund_fee = AmountUtils.changeY2F(refund_fee);
		SortedMap<Object, Object> SortedMap = new TreeMap<Object, Object>();
		
		SortedMap.put("appid", appId);
	    SortedMap.put("mch_id", mchId);
	    SortedMap.put("nonce_str", System.currentTimeMillis() + "");
	    SortedMap.put("transaction_id", transaction_id);
	    SortedMap.put("out_refund_no", out_refund_no);
	    SortedMap.put("total_fee",total_fee);
	    SortedMap.put("refund_fee", refund_fee);
	    
		WechatPay pay = new WechatPay();
		
		pay.setKey(appKey);
		
		String sign = pay.createSign("UTF-8", SortedMap);
		SortedMap.put("sign", sign);
		
		
		Map resultMap = null;
		
		String requestXml = pay.getRequestXml(SortedMap);
		String result = null;
		
	    try {
	        result = WechatPay.WeixinSendPost(requestXml,mchId,certificatePath);
	        //解析返回的xml
	        resultMap = XMLUtil.doXMLParse(result);
	       System.out.println(resultMap);
	    }catch (Exception e){
	        e.printStackTrace();
	    }

		return resultMap;
	}
	
	
	
	public static String WeixinSendPost(String xmlObj,String mch_id,String certificatePath) throws Exception {

		   String result = "";
		   InputStream instream = new FileInputStream(certificatePath);
		   KeyStore keyStore = KeyStore.getInstance("PKCS12");
		   try {
		      keyStore.load(instream, mch_id.toCharArray());
		   } catch (Exception e) {
		     
		   } finally {
		      instream.close();
		   }

		   SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();

		   SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },null,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		   
		   CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		   try {

		      HttpPost httpPost = new HttpPost(refundUrl);
		      
		      ContentType contentType = ContentType.create(ContentType.TEXT_XML.getMimeType(),"iso-8859-1");
		      
		      HttpEntity xmlData = new StringEntity(xmlObj,contentType);
		      
		      httpPost.setEntity(xmlData);

		      System.out.println("executing request" + httpPost.getRequestLine());

		      CloseableHttpResponse response = httpclient.execute(httpPost);
		      try {
		         HttpEntity entity = response.getEntity();
		         result = EntityUtils.toString(entity, "UTF-8");
		         System.out.println(response.getStatusLine());
		         EntityUtils.consume(entity);
		      } finally {
		         response.close();
		      }
		   } finally {
		      httpclient.close();
		   }
		   //去除空格
		   return result.replaceAll(" ", "");
		}
	
	/**
	 * 
	 * @param appId appId
	 * @param mchId 商务id
	 * @param fee_type 转换的币种类型
	 * @param key app私钥
	 * @return 
	 * 如果汇率不存在的时候会返回空值
	 */
	public static BigDecimal getExchangeRate(String appId, String mchId,String fee_type,String key) throws Exception {
		WechatPay wechatPay = new WechatPay();
		SortedMap<Object, Object> SortedMap = new TreeMap<Object, Object>();
		
		SortedMap.put("appid", appId);
	    SortedMap.put("mch_id", mchId);
	    SortedMap.put("fee_type", fee_type);
	    SortedMap.put("date", getDateStr());
	    
	    WechatPay pay = new WechatPay();
	    pay.setKey(key);
		String sign = pay.createSign("UTF-8", SortedMap);
		SortedMap.put("sign", sign);
		
		String requestXml = pay.getRequestXml(SortedMap);
		Map map = XMLUtil.doXMLParse(wechatPay.httpsRequest(exchangeRateUrl, "POST", requestXml));
		Object returnMsg = map.get("return_msg");
		if(!returnMsg.equals("OK")) {
			return null;
		}
		String rate = map.get("rate").toString();
		int lackLength = 9-rate.length();
		for(int i =0;i<lackLength;i++) {
			rate = 0+rate;
		}
		return new BigDecimal(rate.substring(0,1)+"."+rate.substring(1)).stripTrailingZeros();
	}
   

    /**
     * 返回当前时间字符串
     * 
     * @return yyyyMMddHHmmss
     */
    public static String getDateStr()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
    
    /**
     * 获取accountToken
     * @param appid
     * @param secret
     * @return
     */
    public static Object getAccessToken(String appid,String secret) {
    	String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		JSONObject request = JSONObject.fromObject(HttpKit.post(accessTokenUrl, null));
		return request != null ?request.get("access_token"):null;
    }

    /**
     * 获取jsapiTicket
     * @param accessToken
     * @return
     */
	public static Object getJsapiTicket(Object accessToken) {
		String jsapiTicketUrl ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken +"&type=jsapi";
		JSONObject request = JSONObject.fromObject(HttpKit.post(jsapiTicketUrl, null));
		return request != null ?request.get("ticket"):null;
	}



	/**
	 * 获取签名
	 * @param noncestr 随机字符串
	 * @param jsapiTicket
	 * @param timestamp 时间戳
	 * @param url
	 * @return 
	 */
	public static Object getSignature(String noncestr, Object jsapiTicket, long timestamp,String url) {
		String signature = "jsapi_ticket="+jsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		return SHA1.encode(signature);
	}
	
}
