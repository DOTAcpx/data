package com.cn.jm.core.pay.wechat;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClientTrusted(X509Certificate[] arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isServerTrusted(X509Certificate[] arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		
	}

}
