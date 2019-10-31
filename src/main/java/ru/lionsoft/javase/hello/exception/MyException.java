/*
 * File:    MyException.java
 * Project: HelloJavaSE
 * Date:    29 окт. 2019 г. 21:23:47
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.exception;

import java.util.ResourceBundle;

/**
 * Класс определяющий мою ошибку
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class MyException extends Exception {

    private final int errorCode;
    
    /**
     * Creates a new instance of <code>MyException</code> without detail
     * message.
     * @param errorCode код ошибки
     */
    public MyException(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Constructs an instance of <code>MyException</code> with the specified
     * detail message.
     *
     * @param errorCode код ошибки
     * @param msg the detail message.
     */
    public MyException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * Получить код ошибки
     * @return код ошибки
     */
    public int getErrorCode() {
        return errorCode;
    }
    
    /**
     * Получить описание ошибки
     * @return описание ошибки
     */
    public String getErrorMessage() {
        return ResourceBundle
                .getBundle("ru.lionsoft.javase.hello.exception.errors")
                .getString(String.format("err-%03d", errorCode));
    }
    
}
