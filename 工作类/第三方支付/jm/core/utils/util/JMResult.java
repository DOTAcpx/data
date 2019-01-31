package com.cn.jm.core.utils.util;

import java.util.List;

import com.cn.jm.core.utils.json.JsonPareType;
import com.cn.jm.web.core.JMMessage;
import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;
import com.jfinal.json.Json;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Model;

public class JMResult {

	public static final int LOGIN_ERROR = 2;
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	// 默认失败
	private int code = 0;
	private String desc;
	private Object data;

	private Controller controller;

	public static JMResult create() {
		return new JMResult();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDesc() {
		return desc;
	}

	public Object getData() {
		return data;
	}

	public JMResult code(int code) {
		this.code = code;
		return this;
	}

	public JMResult desc(String desc) {
		this.desc = desc;
		return this;
	}

	public JMResult data(Object data) {
		this.data = data;
		return this;
	}

	public JMResult controller(Controller controller) {
		this.controller = controller;
		return this;
	}

	@Override
	public String toString() {
		return JsonKit.toJson(this);
	}

	public static JMResult loginError() {
		return JMResult.create().code(JMResult.LOGIN_ERROR).desc(JMMessage.SESSION_ID_NULL);
	}

	public static JMResult fail(Object data, String desc) {
		return JMResult.create().code(JMResult.FAIL).data(data).desc(desc);
	}

	public static JMResult fail(String desc) {
		return JMResult.create().code(JMResult.FAIL).desc(desc);
	}

	public static JMResult success(Object data, String desc) {
		return JMResult.create().code(JMResult.SUCCESS).data(data).desc(desc);
	}

	public static JMResult success(Object data) {
		return JMResult.create().code(JMResult.SUCCESS).data(data).desc(JMMessage.SUCCESS);
	}

	/**
	 * 
	 * @Description: 将Id转成string 字段为stringId
	 * @param data
	 * @return
	 */
	public static <T extends Model<T>> JMResult successStringtoIds(List<T> data) {
		for (Model<T> obj : data) {
			Long id = obj.getLong("id");
			obj.put("stringId", String.valueOf(id));
		}
		return JMResult.create().code(JMResult.SUCCESS).data(data).desc(JMMessage.SUCCESS);
	}

	public static JMResult success(String desc) {
		return JMResult.create().code(JMResult.SUCCESS).desc(desc);
	}

	public void renderJson() {
		Json json = JsonPareType.MY_JFINAL_JSON.getJson();
		controller.renderJson(json.toJson(this));
	}

	public void renderJson(Controller controller) {
		Json json = JsonPareType.MY_JFINAL_JSON.getJson();
		controller.renderJson(json.toJson(this));
	}

	public void renderJsonI18nDesc(Controller controller) {
		String key = controller.getPara("language");

		Res resEn = I18NUtils.getRes(key);
		String desc = this.getDesc();
		// 直接获取数据
		try {
			desc = resEn.get(desc);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(controller.getRequest().getRequestURL()+"---"+desc);
		}
		this.setDesc(desc);

		Json json = JsonPareType.MY_JFINAL_JSON.getJson();
		controller.renderJson(json.toJson(this));
	}

	public void renderJsonI18nDesc(Controller controller, JsonPareType type) {
		String key = controller.getPara("language");

		Res resEn = I18NUtils.getRes(key);
		String desc = this.getDesc();
		// 直接获取数据
		desc = resEn.get(desc);
		this.setDesc(desc);

		Json json = type.getJson();
		controller.renderJson(json.toJson(this));
	}

	@SuppressWarnings("unused")
	public void render(String view) {
		if (this == null) {
			throw new UnsupportedOperationException("XPResult 的值不能为空");
		} else {
			controller.render(view);
		}
	}

}
