/*
 * File:    MySingleton.java
 * Project: HelloJavaSE
 * Date:    12 авг. 2020 г. 00:38:18
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 *
 * @author Igor Morenko (emailto:imorenko@yandex.ru)
 */
public class MySingleton {
    
    private MySingleton() {
        System.out.println("@@@@ MySingleton Created!!!");
    }
    
    private static final MySingleton INSTANCE = new MySingleton();

    public static MySingleton getInstance() {
        return INSTANCE;
    }
    
    public static void setUp() {
        System.out.println("@@@@ MySingleton.setUp()");
    }
    
    public void business() {
        System.out.println("@@@@ MySingleton.business()");
    }
}
