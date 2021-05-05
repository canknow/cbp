package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class PhoneUtil {
    /**
     * 手机号码长度
     */
    private static final int PHONE_LENGTH = 11;

    /**
     * 脱敏*号
     */
    private static final String ASTERISK = "****";

    /**
     * 手机号码脱敏
     * 截取手机号码前三位，后4为，中间4位使用*号代替
     * 18812345678
     * 188****5678
     */
    public String desensitize(String phone) {
        // 校验手机号码
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        if (phone.length() != PHONE_LENGTH) {
            log.error("手机号码不合法：" + phone);
            return phone;
        }

        String before = phone.substring(0,3);
        String after = phone.substring(7,11);
        return before + "****" + after;
    }
}
