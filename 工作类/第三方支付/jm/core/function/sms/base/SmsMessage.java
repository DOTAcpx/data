package com.cn.jm.core.function.sms.base;

public class SmsMessage {
	
	private String account;// 账号
	private String password;// 密码
	private String mobiles;//手机号码，多个号码使用","分割
	private String content;//内容
	private String url;//请求路径
	private String extno = null;// 扩展码
	private boolean needstatus = false;// 是否需要状态报告，需要true，不需要false

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	public boolean isNeedstatus() {
		return needstatus;
	}

	public void setNeedstatus(boolean needstatus) {
		this.needstatus = needstatus;
	}


}
