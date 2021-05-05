package com.canknow.cbp.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum {
    @JsonValue
    Integer getCode();

    String getDescription();

    String getName();

    static <T extends BaseEnum> T getByCode(Class<? extends BaseEnum> enumType, Integer code) {
        if (enumType == null || code == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();

        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            int enumCode = enumConstant.getCode();

            if (code.equals(enumCode)) {
                return enumConstant;
            }
        }
        return null;
    }

    static <T extends BaseEnum> T getByName(Class<? extends BaseEnum> enumType, String name) {
        if (enumType == null || name == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();

        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            String enumName = enumConstant.getName();

            if (name.equals(enumName)) {
                return enumConstant;
            }
        }
        return null;
    }

    static <T extends BaseEnum> T getByDescription(Class<? extends BaseEnum> enumType, String description) {
        if (enumType == null || description == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();

        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            String enumDescription = enumConstant.getDescription();

            if (description.equals(enumDescription)) {
                return enumConstant;
            }
        }
        return null;
    }
}
