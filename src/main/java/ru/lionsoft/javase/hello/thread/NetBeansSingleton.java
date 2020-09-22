/*
 * File:    NetBeansSingleton.java
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
public class NetBeansSingleton {
    
    static {
        System.out.println("@@@@ NetBeansSingleton Class Loaded!!!");
    }
    
    private NetBeansSingleton() {
        System.out.println("@@@@ NetBeansSingleton Created!!!");
    }
    
    private static class NetBeansSingletonHolder {
        static {
            System.out.println("@@@@ NetBeansSingletonHolder Class Loaded!!!");
        }
        private static final NetBeansSingleton INSTANCE = new NetBeansSingleton();
    }

    // Lazy Load and Thread Safe
    public static NetBeansSingleton getInstance() {
        return NetBeansSingletonHolder.INSTANCE;
    }
    
    public static void setUp() {
        System.out.println("@@@@ NetBeansSingleton.setUp()");
    }
    
    public void business() {
        System.out.println("@@@@ NetBeansSingleton.business()");
    }
}
