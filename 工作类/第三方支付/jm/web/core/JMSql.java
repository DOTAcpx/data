package com.cn.jm.web.core;

public class JMSql {
	
	String sql = "SELECT a.* FROM system_role AS a LEFT JOIN system_role_account AS b ON a.id = b.roleId WHERE b.accountId = ? ORDER BY a.id DESC";

}
