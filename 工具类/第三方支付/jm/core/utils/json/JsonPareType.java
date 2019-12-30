package com.cn.jm.core.utils.json;

import com.jfinal.json.FastJsonFactory;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.json.Json;

public enum JsonPareType {

	FAST_JSON(FastJsonFactory.me().getJson()),

	/*
	 * JACK_SON(JacksonFactory.me().getJson()),
	 * 
	 * MIXED_JSON(MixedJsonFactory.me().getJson()),
	 */

	JFINAL_JSON(JFinalJsonFactory.me().getJson()),

	MY_JFINAL_JSON(MyJFinalJsonFactory.me().getJson());

	private Json json;

	private JsonPareType(Json json) {
		this.json = json;
	}

	public Json getJson() {
		return json;
	}

}