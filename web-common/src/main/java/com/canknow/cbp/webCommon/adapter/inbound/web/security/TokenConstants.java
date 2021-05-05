package com.canknow.cbp.webCommon.adapter.inbound.web.security;

public class TokenConstants {
    public final static long EXPIRE_TIME = 60 * 24 * 60 * 1000;

    public final static String SECRET = "SHIRO+JWT";

    public final static String REQUEST_HEADER_NAME = "Authorization";
}
