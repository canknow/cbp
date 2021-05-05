package com.canknow.cbp.common.domain.authorization;

public enum LoginResultType {
    SUCCESS,
    INVALID_USER_NAME,
    INVALID_PASSWORD,
    USER_IS_NOT_ACTIVE,
    USER_EMAIL_IS_NOT_CONFIRMED,
    UNKNOWN_EXTERNAL_LOGIN,
    LOCKED_OUT,
    USER_PHONE_NUMBER_IS_NOT_CONFIRMED
}
