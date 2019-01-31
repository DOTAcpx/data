package com.cn.jm.web.core.db;

public class JMDbSourceMeta {
	
	private String dbConfigName;
	private String url;
	private String userName;
	private String password;
	private IMappingKit mappingKit;
	
	
	public String getDbConfigName() {
		return dbConfigName;
	}
	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public IMappingKit getMappingKit() {
		return mappingKit;
	}
	public void setMappingKit(IMappingKit mappingKit) {
		this.mappingKit = mappingKit;
	}

}
