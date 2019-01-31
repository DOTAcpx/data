package com.cn.jm.web.core;

public class JMMessage {

	/**
	 * 操作成功
	 */
	public static final String SUCCESS = "操作成功";
	/**
	 * 操作失败
	 */
	public static final String ERROR = "操作失败";
	/**
	 * 参数错误
	 */
	public static final String PARAM_ERROR = "参数错误";
	/**
	 * 参数为空
	 */
	public static final String PARAM_NULL = "参数为空";
	/**
	 * 参数过长
	 */
	public static final String PARAM_TOO_LONG = "参数过长";
	/**
	 * 参数过短
	 */
	public static final String PARAM_TOO_SHORT = "参数过短";

	/**
	 * 用户不存在
	 */
	public static final String NOT_ACCOUNT = "用户不存在";
	/**
	 * 用户已存在
	 */
	public static final String HAS_ACCOUNT = "用户已存在";
	/**
	 * 验证码错误
	 */
	public static final String CODE_ERROR = "验证码错误";
	/**
	 * 请不要频繁刷新验证码
	 */
	public static final String CODE_OFTEN = "请不要频繁刷新验证码";
	/**
	 * 手机号码错误
	 */
	public static final String PHONE_ERROR = "手机号码错误";
	/**
	 * 密码错误
	 */
	public static final String PASSWORD_ERROR = "密码错误";
	/**
	 * 密码过短
	 */
	public static final String PASSWORD_SHORT_ERROR = "密码过短";
	/**
	 * 第三方登录授权失败
	 */
	public static final String AUTHORIZE_ERROR = "第三方登录授权失败";
	/**
	 * 该手机号已绑定了其它授权账号
	 */
	public static final String HAS_AUTHORIZE = "该手机号已绑定了其它授权账号";
	/**
	 * 需要绑定手机号
	 */
	public static final String NOT_PHONE = "需要绑定手机号";
	/**
	 * 时间过期，请重新授权
	 */
	public static final String AUTHORIZE_OUT_TIME = "时间过期，请重新授权";
	/**
	 * 该手机号已被冻结
	 */
	public static final String ACCOUNT_FZ = "该账号已被冻结";
	/**
	 * 余额不足
	 */
	public static final String NOT_BALANCE = "余额不足";
	/**
	 * 充值失败
	 */
	public static final String RECHARGE_ERROR = "充值失败";
	/**
	 * 充值标题
	 */
	public static final String RECHARGE = "RECHARGE"; 
	/**
	 * 提现失败
	 */
	public static final String WITHDRAW_ERROR = "提现失败";
	/**
	 * 提现成功
	 */
	public static final String WITHDRAW_SUCCESS = "提现成功";

	/**
	 * 充值成功
	 */
	public static final String RECHARGE_SUCCESS = "充值成功";

	/**
	 * 充值失败
	 */
	public static final String PAY_ERROR = "付款失败";

	/**
	 * 充值成功
	 */
	public static final String PAY_SUCCESS = "付款成功";

	/**
	 * 产品不存在
	 */
	public static final String PRODUCT_NULL = "产品已经删除";

	/**
	 * 身份过期
	 */
	public static final String SESSION_ID_NULL = "身份已经过期请重新登录";

	/**
	 * 对方正在忙碌
	 */
	public static final String IS_BUSY = "对方正在忙碌中";

	/**
	 * 用户已被禁封
	 */
	public static final String ACCOUNT_IS_BAN = "用户已被禁封";

	/**
	 * 已拉黑不能关注，请取消拉黑
	 */
	public static final String IS_BLACK = "已拉黑不能关注，请取消拉黑";

	/**
	 * 已关注不能拉黑，请取消关注
	 */
	public static final String IS_FLLOW = "已关注不能拉黑，请取消关注";
	/**
	 * 参数过短
	 */
	public static final String PARAM_TOO_SHORE = "参数过短";
	
	/**
	 * who 评论了 who
	 */
	public static final String WHO_COMMENT_WHO = "评论了";
	/**
	 * who 点赞了 who
	 */
	public static final String WHO_ZAN_WHO = "点赞了";

}
