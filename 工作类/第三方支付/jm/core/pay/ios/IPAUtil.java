package com.cn.jm.core.pay.ios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;

public class IPAUtil {
	/**
	 * 大致原理也就是拿着receipt-data请求苹果服务器，苹果会返回给我们详细的账单信息，根据该信息判断账单是否正确
	 *	沙箱环境:https://sandbox.itunes.apple.com/verifyReceipt 
	 *	正式环境:https://buy.itunes.apple.com/verifyReceipt
	 *	请求参数:receipt-data
	 *	请求方法:POST
	 * @param url
	 * @param receipt
	 * @return
	 */
	public static JSONObject verify(String url, String receipt) {  
	    try {  
	        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();    
	        connection.setRequestMethod("POST");    
	        connection.setDoOutput(true);    
	        connection.setAllowUserInteraction(false);   
	        PrintStream ps = new PrintStream(connection.getOutputStream());    
	        ps.print("{\"receipt-data\": \"" + receipt + "\"}");    
	        ps.close();    
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));    
	        String str;    
	        StringBuffer sb = new StringBuffer();    
	        while ((str = br.readLine()) != null) {    
	            sb.append(str);      
	        }    
	        br.close();    
	        String resultStr = sb.toString();    
	        JSONObject result = JSONObject.fromObject(resultStr);  
//	        System.out.println(result);
//	        if (result != null && result.get("status").equals(21007)) {   //递归，以防漏单
//	            return verify("https://sandbox.itunes.apple.com/verifyReceipt", receipt);  
//	        }  
	        return result;  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return null;  
	} 
}
