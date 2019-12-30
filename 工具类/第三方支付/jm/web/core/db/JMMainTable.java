package com.cn.jm.web.core.db;

import java.util.List;

public class JMMainTable {
	
	private String tableName;//表名
	private String primaryKeyName;//主键名
	private String subKeyName;//子键名
	
	private List<JMSubTable> subTableNameList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public String getSubKeyName() {
		return subKeyName;
	}

	public void setSubKeyName(String subKeyName) {
		this.subKeyName = subKeyName;
	}

	public List<JMSubTable> getSubTableNameList() {
		return subTableNameList;
	}

	public void setSubTableNameList(List<JMSubTable> subTableNameList) {
		this.subTableNameList = subTableNameList;
	}
	
	
}
