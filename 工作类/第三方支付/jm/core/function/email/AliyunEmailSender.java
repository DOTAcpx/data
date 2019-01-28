package com.cn.jm.core.function.email;

import com.jfinal.log.Log;

/**
 * 暂未实现
 */
public class AliyunEmailSender implements IEmailSender {
	private static final Log logger = Log.getLog(AliyunEmailSender.class);

	/**
	 * 文档：
	 * https://help.aliyun.com/document_detail/directmail/api-reference/sendmail
	 * -related/SingleSendMail.html?spm=5176.docdirectmail/api-reference/
	 * sendmail-related/BatchSendMail.6.118.Qd9yth
	 */
	@Override
	public void send(Email email) {

	}
}
