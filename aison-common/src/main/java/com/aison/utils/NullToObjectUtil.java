package com.aison.utils;

import java.math.BigDecimal;

/**
 * @auther lmd
 * @date 2020/5/25
 * @Company 晶粮
 */
public class NullToObjectUtil {
    /**
     * null转空字符串
     *
     * @param nullString
     * @return
     */
    public static String nullString(String nullString) {
        return null == nullString ? "" : nullString;
    }

    public static Double nullToZero(Double doubleNum) {
        return null == doubleNum ? 0D : doubleNum;
    }

    public static BigDecimal nullToZero(Long price) {
        return null == price ? BigDecimal.ZERO : new BigDecimal(price);
    }

    public static BigDecimal nullToZero(BigDecimal price) {
        return null == price ? BigDecimal.ZERO : price;
    }

    public static Integer nullToZero(Integer price) {
        return null == price ? 0 : price;
    }

    public static BigDecimal nullToZero(String price) {
        return null == price ? BigDecimal.ZERO : new BigDecimal(price);
    }

    public static Boolean nullToFalse(Boolean bool){
        return null == bool ? false : bool;
    }

    public static Boolean nullToTrue(Boolean bool){
        return null == bool ? true : bool;
    }
}
