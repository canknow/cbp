package com.canknow.cbp.base.utils;

import com.canknow.cbp.base.application.dto.EnumDto;
import com.canknow.cbp.base.enums.BaseEnum;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

public class BaseEnumUtil {
    private static final Map<String, Map<Integer, EnumDto<? extends BaseEnum>>> ENUM_MAP = new LinkedHashMap<>();

    /**
     * 通过code获取枚举
     *
     * @param baseEnumType
     * @param code
     * @return
     */
    public static BaseEnum getEnum(Class<? extends BaseEnum> baseEnumType, Integer code) {
        EnumDto<? extends BaseEnum> EnumDto = getEnumDto(baseEnumType, code);
        if (EnumDto == null) {
            return null;
        }
        return EnumDto.getBaseEnum();
    }

    /**
     * 通过code获取枚举DTO
     *
     * @param baseEnumType
     * @param code
     * @return
     */
    public static EnumDto<? extends BaseEnum> getEnumDto(Class<? extends BaseEnum> baseEnumType, Integer code) {
        Map<Integer, EnumDto<? extends BaseEnum>> map = getMap(baseEnumType);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.get(code);
    }

    /**
     * 判断code在枚举中是否存在
     *
     * @param baseEnumType
     * @param code
     * @return
     */
    public static boolean exists(Class<? extends BaseEnum> baseEnumType, Integer code) {
        EnumDto<? extends BaseEnum> EnumDto = getEnumDto(baseEnumType, code);
        if (EnumDto == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断code在枚举中是否不存在
     *
     * @param baseEnumType
     * @param code
     * @return
     */
    public static boolean notExists(Class<? extends BaseEnum> baseEnumType, Integer code) {
        return !exists(baseEnumType, code);
    }

    /**
     * 通过code获取描述
     *
     * @param baseEnumType
     * @param code
     * @return
     */
    public static String getDescription(Class<? extends BaseEnum> baseEnumType, Integer code) {
        EnumDto<? extends BaseEnum> EnumDto = getEnumDto(baseEnumType, code);
        if (EnumDto == null) {
            return null;
        }
        return EnumDto.getDescription();
    }

    /**
     * 通过类型获取枚举Map
     *
     * @param baseEnumType
     * @return
     */
    public static Map<Integer, EnumDto<? extends BaseEnum>> getMap(Class<? extends BaseEnum> baseEnumType) {
        return ENUM_MAP.get(baseEnumType.getName());
    }

    /**
     * 通过类型获取枚举code集合
     *
     * @param baseEnumType
     * @return
     */
    public static Set<Integer> getCodeSet(Class<? extends BaseEnum> baseEnumType) {
        Map<Integer, EnumDto<? extends BaseEnum>> map = getMap(baseEnumType);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.keySet();
    }

    /**
     * 通过类型获取枚举desc集合
     *
     * @param baseEnumType
     * @return
     */
    public static Collection<EnumDto<? extends BaseEnum>> getDescList(Class<? extends BaseEnum> baseEnumType) {
        Map<Integer, EnumDto<? extends BaseEnum>> map = getMap(baseEnumType);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.values();
    }

    public static Map<String, Map<Integer, EnumDto<? extends BaseEnum>>> getEnumMap() {
        return ENUM_MAP;
    }

    public static List<EnumDto<? extends BaseEnum>> getEnums(String name) {
        return (List<EnumDto<? extends BaseEnum>>) ENUM_MAP.get(name).values();
    }
}
