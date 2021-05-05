package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class NoUtils {
    public String getRandomString(Integer length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        return buildNo(length, base);
    }

    public String getRandomNum(Integer length) {
        String base = "0123456789";
        return buildNo(length, base);
    }

    private static String buildNo(Integer length, String base) {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }
}
