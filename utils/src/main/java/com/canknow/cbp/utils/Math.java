package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Math {
    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 提供精确的加法运算。.
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public double add(double value1, double value2) {
        // 必须转换成String
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。.
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。.
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。.
     *
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数的商
     */
    public double div(double value1, double value2) {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。.
     *
     * @param value1    被除数
     * @param value2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public double div(double value1, double value2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。.
     *
     * @param value     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return bigDecimal.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double add(BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        return bigDecimal.add(bigDecimal2).add(bigDecimal3).doubleValue();
    }

    public double add(BigDecimal preDepositPrice, BigDecimal finalPrice) {
        return preDepositPrice.add(finalPrice).doubleValue();
    }
}
