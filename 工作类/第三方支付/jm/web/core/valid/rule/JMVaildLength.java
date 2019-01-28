package com.cn.jm.web.core.valid.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cn.jm.core.utils.util.JMResult;
import com.cn.jm.core.utils.util.MapUtils;
import com.cn.jm.web.core.JMMessage;

public class JMVaildLength implements JMVaildRule {

	@Override
	public JMResult vaildParam(Map<String, String[]> map, String[] keys, String[] exKeys, boolean isAll,
			String[] maxlength, String[] minlength, String[] regex) {

		// 一个参数都没
		if (MapUtils.isEmpty(map)) {
			return JMResult.fail(false, JMMessage.PARAM_NULL);
		}

		for (String key : keys) {

			String[] param = map.get(key);

			if (param == null) {
				return JMResult.success(true);
			}

			int index = 0;
			for (String value : param) {
				if (StringUtils.isEmpty(value)) {
					return JMResult.fail(false, key + JMMessage.PARAM_NULL);
				}

				if (value.length() > Integer.valueOf(maxlength[index])) {
					return JMResult.fail(false, key + JMMessage.PARAM_TOO_LONG);
				}

				if (value.length() < Integer.valueOf(minlength[index])) {
					return JMResult.fail(false, key + JMMessage.PARAM_TOO_SHORE);
				}
				index++;
			}

		}

		return JMResult.success(true);
	}

	public static void main(String[] args) {

		JMVaildLength JMVaildNull = new JMVaildLength();
		Map<String, String[]> map = new HashMap<>();
		map.put("测试", new String[] {});
		map.put("code", new String[] {});

		// System.out.println(JMVaildNull.vaildParam(map, new String[] { "测试" },
		// false));

	}

}
