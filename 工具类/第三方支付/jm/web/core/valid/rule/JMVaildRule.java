package com.cn.jm.web.core.valid.rule;

import java.util.Map;

import com.cn.jm.core.utils.util.JMResult;

public interface JMVaildRule {

	/**
	 * 
	 * @Description: true 通过校验 false 不通过
	 * @param objects
	 * @return
	 */
	public JMResult vaildParam(Map<String, String[]> map, String[] keys, String[] exKeys, boolean isAll,
			String[] maxlength, String[] minLength, String[] regex);
}
