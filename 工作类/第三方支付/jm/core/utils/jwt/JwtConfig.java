package com.cn.jm.core.utils.jwt;

import com.cn.jm.core.tool.JMToolString;

public class JwtConfig {

    private String httpHeaderName = "jwt";
    private String secret;
    private long validityPeriod = 0;

    public String getHttpHeaderName() {
        return httpHeaderName;
    }

    public void setHttpHeaderName(String httpHeaderName) {
        this.httpHeaderName = httpHeaderName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(long validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public boolean isEnable() {
        return JMToolString.isNotEmpty(secret);
    }


}
