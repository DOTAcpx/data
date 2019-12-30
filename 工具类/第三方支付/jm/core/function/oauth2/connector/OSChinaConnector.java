package com.cn.jm.core.function.oauth2.connector;

import com.alibaba.fastjson.JSONObject;
import com.cn.jm.core.function.oauth2.OauthConnector;
import com.cn.jm.core.function.oauth2.OauthUser;
import com.cn.jm.core.tool.JMToolString;

public class OSChinaConnector extends OauthConnector {

	public OSChinaConnector(String name, String appkey, String appSecret) {
		super(name, appkey, appSecret);
	}

	// DOC : http://www.oschina.net/openapi/

	// public OSChinaConnector() {
	// setClientId(OptionQuery.findValue("oauth2_oschina_appkey"));
	// setClientSecret(OptionQuery.findValue("oauth2_oschina_appsecret"));
	// setName("oschina");
	// }

	/**
	 * doc: http://www.oschina.net/openapi/docs/oauth2_authorize
	 */
	public String createAuthorizeUrl(String state) {

		StringBuilder urlBuilder = new StringBuilder("http://www.oschina.net/action/oauth2/authorize?");
		urlBuilder.append("response_type=code");
		urlBuilder.append("&client_id=" + getClientId());
		urlBuilder.append("&redirect_uri=" + getRedirectUri());
		urlBuilder.append("&state=" + state);

		return urlBuilder.toString();
	}

	protected String getAccessToken(String code) {

		StringBuilder urlBuilder = new StringBuilder("http://www.oschina.net/action/openapi/token?");
		urlBuilder.append("grant_type=authorization_code");
		urlBuilder.append("&dataType=json");
		urlBuilder.append("&client_id=" + getClientId());
		urlBuilder.append("&client_secret=" + getClientSecret());
		urlBuilder.append("&redirect_uri=" + getRedirectUri());
		urlBuilder.append("&code=" + code);

		String url = urlBuilder.toString();

		String httpString = httpGet(url);
		// {"access_token":"07a2aeb2-0790-4a36-ae24-40b90c4fcfc1",
		// "refresh_token":"bfd67382-f740-4735-b7f0-86fb9a2e49dc",
		// "uid":111634,
		// "token_type":"bearer",
		// "expires_in":604799}

		if (JMToolString.isEmpty(httpString)) {
			return null;
		}

		JSONObject json = JSONObject.parseObject(httpString);
		return json.getString("access_token");
	}

	@Override
	protected OauthUser getOauthUser(String code) {

		String accessToken = getAccessToken(code);

		String url = "http://www.oschina.net/action/openapi/user?access_token=" + accessToken + "&dataType=json";

		String httpString = httpGet(url);
		// {"gender":"male","name":"michaely","location":"北京 朝阳","id":111634,
		// "avatar":"http://static.oschina.net/uploads/user/55/111634_50.jpg?t=1414374101000",
		// "email":"fuhai999@gmail.com","url":"http://my.oschina.net/yangfuhai"}

		JSONObject json = JSONObject.parseObject(httpString);

		OauthUser user = new OauthUser();
		user.setAvatar(json.getString("avatar"));
		user.setOpenId(json.getString("id"));
		user.setNickname(json.getString("name"));
		user.setGender(json.getString("gender"));
		user.setSource(getName());

		return user;
	}

}
