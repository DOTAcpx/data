package com.cn.jm.core.function.oauth2.connector;

import com.cn.jm.core.function.oauth2.OauthConnector;
import com.cn.jm.core.function.oauth2.OauthUser;

public class FacebookConnector extends OauthConnector {

	public FacebookConnector(String name,String appkey, String appSecret) {
		super(name, appkey, appSecret);
	}

	@Override
	public String createAuthorizeUrl(String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected OauthUser getOauthUser(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
