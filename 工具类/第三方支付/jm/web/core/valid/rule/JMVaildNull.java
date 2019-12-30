package com.cn.jm.web.core.valid.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.cn.jm.core.tool.JMToolString;
import com.cn.jm.core.utils.util.JMResult;
import com.cn.jm.core.utils.util.MapUtils;
import com.cn.jm.web.core.JMMessage;

public class JMVaildNull implements JMVaildRule {

	@Override
	public JMResult vaildParam(Map<String, String[]> map, String[] keys, String[] exKeys, boolean isAll,
			String[] maxlength, String[] minlength, String[] regex) {

		// 一个参数都没
		if (MapUtils.isEmpty(map)) {
			return JMResult.fail(false, JMMessage.PARAM_NULL);
		}

		// 如果全都需要校验
		if (isAll) {

			for (Entry<String, String[]> entry : map.entrySet()) {

				// 排他如果拥有
				if (JMToolString.arrayisHas(exKeys, entry.getKey()))
					continue;

				String[] values = entry.getValue();

				if (values == null || values.length == 0) {
					return JMResult.fail(false, entry.getKey() + JMMessage.PARAM_NULL);
				}

				for (String value : values) {
					if (StringUtils.isEmpty(value)) {
						return JMResult.fail(false, entry.getKey() + JMMessage.PARAM_NULL);
					}
				}
			}

		} else {

			for (String key : keys) {

				String[] param = map.get(key);

				if (param == null) {
					return JMResult.fail(false, key + JMMessage.PARAM_NULL);
				}

				for (String value : param) {
					if (StringUtils.isEmpty(value)) {
						return JMResult.fail(false, key + JMMessage.PARAM_NULL);
					}
				}

			}

		}

		return JMResult.success(true);
	}

	public static void main(String[] args) {

		JMVaildNull JMVaildNull = new JMVaildNull();
		Map<String, String[]> map = new HashMap<>();
		map.put("测试", new String[] {});
		map.put("code", new String[] {});

		// System.out.println(JMVaildNull.vaildParam(map, new String[] { "测试" },
		// false));

	}

}
