package com.cn.jm.core.pay.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.DetailedRefund;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
import com.paypal.base.rest.APIContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PayPalPay {


    private static final String TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";	//测试环境
    private static final String PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/"; //测试环境
//    private static final String TOKEN_URL = "https://api.paypal.com/v1/oauth2/token"; //正式
//    private static final String PAYMENT_DETAIL = "https://api.paypal.com/v1/payments/payment/"; //正式

    /**
     * 获取token
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @return
     */
    public static String getAccessToken(String clientId,String secret){
        try{
            URL url = new URL(TOKEN_URL);
            String authorization = clientId+":"+secret;
            authorization = Base64.encodeBase64String(authorization.getBytes());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Authorization", "Basic "+authorization);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            String params = "grant_type=client_credentials";
            conn.getOutputStream().write(params.getBytes());// 输入参数

            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            String accessTokey = JSONObject.fromObject(result.toString()).optString("access_token");
//            System.out.println("getAccessToken:"+accessTokey);
            return accessTokey;
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    private static String getPaymentDetails(String paymentId,String clientId,String secret){
        try{
            URL url = new URL(PAYMENT_DETAIL+paymentId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+getAccessToken(clientId,secret));
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            return result.toString();
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public static boolean verifyPayment(String paymentId,String clientId,String secret) throws Exception {
        String str = getPaymentDetails(paymentId,clientId,secret);
//        System.out.println(str);
        JSONObject detail = JSONObject.fromObject(str);
        //校验订单是否完成
        if("approved".equals(detail.optString("state"))){
            JSONObject transactions = detail.optJSONArray("transactions").optJSONObject(0);
//            JSONObject amount = transactions.optJSONObject("amount");
            JSONArray relatedResources = transactions.optJSONArray("related_resources");
            //从数据库查询支付总金额与Paypal校验支付总金额
//            double total = 0;
//            System.out.println("amount.optDouble('total'):"+amount.optDouble("total"));
//            if( total != amount.optDouble("total") ){
//                return false;
//            }
//            //校验交易货币类型
//            String currency = "USD";
//            if( !currency.equals(amount.optString("currency")) ){
//                return false;
//            }
            //校验每个子订单是否完成
            for (int i = 0,j = relatedResources.size(); i < j; i++) {
                JSONObject sale = relatedResources.optJSONObject(i).optJSONObject("sale");
                if(sale!=null){
                    if( !"completed".equals(sale.optString("state")) ){
                        System.out.println("子订单未完成,订单状态:"+sale.optString("state"));
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 
	 * ##RefundSale
	 * Sample showing how to refund
	 * a sale
     * @param transId 退款id
     * @param paid 退款金额
     * @param clientID paypalId
     * @param clientSecret paypalSecret
     * @param mode 执行方式
     * @return 
     * @throws Exception 
     */
	public DetailedRefund paypalRefund(String transId,String paid,String clientID,String clientSecret,String mode) throws Exception{
		APIContext apiContext = new APIContext(clientID, clientSecret, mode);
		Payment payment = Payment.get(apiContext, transId);
		// ###Sale
		// A sale transaction.
		// Create a Sale object with the
		// given sale transaction id.
		Sale sale = new Sale();
		sale.setId(payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());

		// ###Refund
		// A refund transaction.
		// Use the amount to create
		// a refund object
		RefundRequest refund = new RefundRequest();
		// ###Amount
		// Create an Amount object to
		// represent the amount to be
		// refunded. Create the refund object, if the refund is partial
		Amount amount = new Amount();
		amount.setCurrency("USD");//支付币种
		amount.setTotal(paid);
		refund.setAmount(amount);
		return sale.refund(apiContext, refund);
	}

    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    private static String getPaymentDetails(String paymentId,String clientId,String secret,String accountToKen){
        try{
            URL url = new URL(PAYMENT_DETAIL+paymentId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+accountToKen);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            return result.toString();
        }catch(Exception err){
            err.printStackTrace();
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public static boolean verifyPayment(String paymentId,String clientId,String secret,String accountToKen) throws Exception {
        String str = getPaymentDetails(paymentId,clientId,secret,accountToKen);
//        System.out.println(str);
        JSONObject detail = JSONObject.fromObject(str);
        //校验订单是否完成
        if("approved".equals(detail.optString("state"))){
            JSONObject transactions = detail.optJSONArray("transactions").optJSONObject(0);
//            JSONObject amount = transactions.optJSONObject("amount");
            JSONArray relatedResources = transactions.optJSONArray("related_resources");
            //从数据库查询支付总金额与Paypal校验支付总金额
//            double total = 0;
//            System.out.println("amount.optDouble('total'):"+amount.optDouble("total"));
//            if( total != amount.optDouble("total") ){
//                return false;
//            }
//            //校验交易货币类型
//            String currency = "USD";
//            if( !currency.equals(amount.optString("currency")) ){
//                return false;
//            }
            //校验每个子订单是否完成
            for (int i = 0,j = relatedResources.size(); i < j; i++) {
                JSONObject sale = relatedResources.optJSONObject(i).optJSONObject("sale");
                if(sale!=null){
                    if( !"completed".equals(sale.optString("state")) ){
                        System.out.println("子订单未完成,订单状态:"+sale.optString("state"));
                    }
                }
            }
            return true;
        }
        return false;
    }
	public static void main(String[] args) throws Exception {
//		PayPalPay payPalPay = new PayPalPay();
		String transId = "PAYID-LPT223Y8LL378500R803173P";
//		String paid = "2";
//		String clientID = "AVBtr7VAhtEzsl48RNzZ7tRQnH8ll4BEqpJt-H0tjeIhaV6NyNChM5sYCPcSriW0vWRUyH0fHTLov2bl";
//		String clientSecret = "EPZj-31ABbJvfvrKxWyPfz0hrg6oMcBlks2veVvJ8ayNJ-BgI1RcZ1ZkXBpQYE1p-WlcR_cn8lMIDfDe";
		String clientID = "AWq_VEvNu1LqlnLJTKKALsi_rdoEfUKfaF6Kl0eCIpBMbfyAF5r4IWe7sCvNmm6VuzecF34HJGekY6oA";
		String clientSecret = "EJ6aznYIx0JphFYfYeyUqyrRlAXkrb1lJ9MkgYKKX0Qih33zcbAAm7qLApFCOj0DnkVre5dnIlFsiD2e";
//		String mode = "live";
		String accessToken = "A21AAG2q4HFsMHUwsYKqczfyo6Mokag_nzJOu1-QcARtMy3LWUP_UokFCUMnsyBx17f_15GHTWPQbxSDuCM3GS6cZedr4ZJeA";
//		String accessToken = getAccessToken(clientID, clientSecret);
		boolean flag = PayPalPay.verifyPayment(transId, clientID, clientSecret,accessToken);
		System.out.println(flag);
//		DetailedRefund paypalRefund = payPalPay.paypalRefund(transId, paid, clientID, clientSecret, mode);
//		System.out.println(paypalRefund);PAYID-LPVJE3A21W00104KB6750452
//		System.out.println("----------------------------------------------------------------");
//		System.out.println("state : "+paypalRefund.getState());
//		System.out.println("amount : "+paypalRefund.getAmount());
//		System.out.println("total : "+paypalRefund.getAmount().getTotal());
	}
}
