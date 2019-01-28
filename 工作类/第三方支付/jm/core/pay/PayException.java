package com.cn.jm.core.pay;

@SuppressWarnings("serial")
public class PayException extends Exception {

	String errorMessage;

	public PayException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return errorMessage;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
