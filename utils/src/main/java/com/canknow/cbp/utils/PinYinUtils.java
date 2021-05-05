package com.canknow.cbp.utils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class PinYinUtils {
    public static String getShortPinYin(String... params) {
        StringBuilder buffer = new StringBuilder();
        for (String param : params) {
            if (StringUtils.isNotBlank(param)) {
                buffer.append(param);
            }
        }
        if (StringUtils.isBlank(buffer.toString())) {
            return "";
        }
        String pinyin = "";

        try {
            pinyin = PinyinHelper.getShortPinyin(buffer.toString());
        }
        catch (PinyinException e) {
            log.warn("转换成拼音失败:{}", e.getMessage());
        }
        return pinyin;
    }
}
