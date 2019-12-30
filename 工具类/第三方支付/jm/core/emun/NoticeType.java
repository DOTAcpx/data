package com.cn.jm.core.emun;

public enum NoticeType {

	/**
	 * 系统（通知所有人）
	 */
	SYSTEM(0),
	/**
	 * 个人(具体通知用户)
	 */
	PERSON(1);

	private int code;

	private NoticeType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
