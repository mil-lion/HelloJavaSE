/*
 * File:    NumberUtils.java
 * Project: HelloJavaSE
 * Date:    27 нояб. 2018 г. 23:53:40
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

/**
 * Класс набора утилит по работе с числами
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class NumberUtils {

    /**
     * Преобразование {@code byte} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String byteToBinaryString(byte value) {
        String str = "00000000" + Integer.toBinaryString(value);
        return str.substring(str.length() - Byte.SIZE);
    }

    public static String byteToBinaryString2(byte value) {
        char[] buffer = new char[Byte.SIZE];
        int mask = 0x80;
        for (int i = 0; i < buffer.length; i++, mask >>>= 1) {
            buffer[i] = (value & mask) == 0 ? '0' : '1';
        }
        return new String(buffer);
    }

    /**
     * Преобразование {@code short} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String shortToBinaryString(short value) {
        String str = "0000000000000000" + Integer.toBinaryString(value);
        return str.substring(str.length() - Short.SIZE);
    }

    /**
     * Преобразование {@code int} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String intToBinaryString(int value) {
        String str = "00000000000000000000000000000000" + Integer.toBinaryString(value);
        return str.substring(str.length() - Integer.SIZE);
    }

    /**
     * Преобразование {@code long} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String longToBinaryString(long value) {
        String str = 
                "0000000000000000000000000000000000000000000000000000000000000000"
                + Long.toBinaryString(value);
        return str.substring(str.length() - Long.SIZE);
    }

    /**
     * Преобразование {@code float} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String floatToBinaryString(float value) {
        return intToBinaryString(Float.floatToIntBits(value));
    }
    
    /**
     * Преобразование {@code double} аргумента в строку в бинарном формате
     * @param value аргумент
     * @return строка в бинарном формате
     */
    public static String doubleToBinaryString(double value) {
        return longToBinaryString(Double.doubleToLongBits(value));
    }

}
