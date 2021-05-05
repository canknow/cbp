package com.canknow.cbp.webCommon.adapter.inbound.web.security;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class TokenHelper {
    public String getTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        String authorization = request.getHeader(TokenConstants.REQUEST_HEADER_NAME);
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        return request.getHeader(TokenConstants.REQUEST_HEADER_NAME).substring(7);
    }
}
