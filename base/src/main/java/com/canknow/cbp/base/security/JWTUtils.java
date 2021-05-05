package com.canknow.cbp.base.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@UtilityClass
public class JWTUtils {
    public String createTokenByUserId(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + JWTConstants.EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(JWTConstants.SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim(Claims.USER_ID, userId)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String getUserIdFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Claims.USER_ID).asString();
        }
        catch (JWTDecodeException e) {
            return null;
        }
    }

    public boolean verify(String token, String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTConstants.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(Claims.USER_ID, userId).build();
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException | UnsupportedEncodingException exception) {
            return false;
        }
    }
}
