package com.cn.jm.web.core.db;

public class JMSubTable {
	
	private String tableName;//表名
	private String subKeyName;//子键名
	private int action;//行为
	
	public static final int NO_ACTION = 0;//无操作
	public static final int SET_NULL = 1;//关联值设置为null
	public static final int CASCADE = 2;//关联直接操作
	
	public JMSubTable(String tableName,String subKeyName,int action) {
		this.tableName = tableName;
		this.subKeyName = subKeyName;
		this.action = action;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getSubKeyName() {
		return subKeyName;
	}
	
	public void setSubKeyName(String subKeyName) {
		this.subKeyName = subKeyName;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
	
}
