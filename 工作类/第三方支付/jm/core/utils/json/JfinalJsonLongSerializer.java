package com.cn.jm.core.utils.json;

public class JfinalJsonLongSerializer implements ObjectSerializer {

	@Override
	public String serializer(Object obj, MyJFinalJson json) {
		return "\"" + json.escape(String.valueOf(obj)) + "\"";
	}

}
