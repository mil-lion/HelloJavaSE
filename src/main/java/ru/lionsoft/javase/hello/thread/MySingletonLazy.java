/*
 * File:    MySingletonLazy.java
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
public class MySingletonLazy {
    
    private MySingletonLazy() {
        System.out.println("@@@@ MySingletonLazy Created!!!");
    }
    
    private static MySingletonLazy instance = null;

    // Lazy Load and Thread Safe
    public static /*!!!synchronized - излишняя блокировка метода!!!*/ MySingletonLazy getInstance() {
        if (instance == null) { // double check
            synchronized(MySingletonLazy.class) {
                if (instance == null) {
                    instance = new MySingletonLazy();
                }
            }
        }
        return instance;
    }
    
    public static void setUp() {
        System.out.println("@@@@ MySingletonLazy.setUp()");
    }
    
    public void business() {
        System.out.println("@@@@ MySingletonLazy.business()");
    }
}
