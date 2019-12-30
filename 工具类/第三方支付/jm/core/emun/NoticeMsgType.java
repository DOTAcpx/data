package com.cn.jm.core.emun;

public enum NoticeMsgType {

	/**
	 * 系统通知
	 */
	SYSTEM(0),
	/**
	 * 订单消息
	 */
	ORDER(1),
	/**
	 * 账单
	 */
	BILL(2),
	/**
	 * 聊天消息
	 */
	CHAT(3),
	/**
	 * 收藏
	 */
	COLLECTION(4),
	/**
	 * 评论 
	 */
	COMMENT(5);
	private int code;

	private NoticeMsgType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
