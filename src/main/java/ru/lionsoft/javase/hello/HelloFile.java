/*
 * File:    HelloFile.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:19:48 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import ru.lionsoft.javase.hello.exception.MyException;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloFile {
    
    public static void main(String[] args) {
        try {
            throw new MyException(1, "except!");
        } catch (MyException ex) {
            System.err.println("ex.message   = " + ex.getMessage());
            System.err.println("errorCode    = " + ex.getErrorCode());
            System.err.println("errorMessage = " + ex.getErrorMessage());
        }
    }
}
