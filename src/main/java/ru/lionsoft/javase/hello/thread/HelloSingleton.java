/*
 * File:    HelloSingleton.java
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
public class HelloSingleton {
    
    public static void main(String[] args) throws InterruptedException {
        
        Thread[] threads = new Thread[10];
        
        System.out.println("#### Test MySingleton ####");
        MySingleton.setUp();
        System.out.println("Test Thread:");
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                   System.out.println(Thread.currentThread().getName() + ": " + MySingleton.getInstance());
                }
            }, "Thread #" + i);
        }
        ThreadUtil.startAndJoinThreads(threads);
        MySingleton mySingleton1 = MySingleton.getInstance();
        mySingleton1.business();
        MySingleton mySingleton2 = MySingleton.getInstance();
        System.out.println("mySingleton1 == mySingleton2 => " + (mySingleton1 == mySingleton2));

        System.out.println("\n#### Test MySingletonLazy ####");
        MySingletonLazy.setUp();
        System.out.println("Test Thread:");
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": " + MySingletonLazy.getInstance());
                }
            }, "Thread #" + i);
        }
        ThreadUtil.startAndJoinThreads(threads);
        MySingletonLazy mySingletonLazy1 = MySingletonLazy.getInstance();
        mySingletonLazy1.business();
        MySingletonLazy mySingletonLazy2 = MySingletonLazy.getInstance();
        System.out.println("mySingletonLazy1 == mySingletonLazy2 => " + (mySingletonLazy1 == mySingletonLazy2));

        System.out.println("\n#### Test NetBeansSingleton ####");
        NetBeansSingleton.setUp();
        System.out.println("Test Thread:");
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": " + NetBeansSingleton.getInstance());
                }
            }, "Thread #" + i);
        }
        ThreadUtil.startAndJoinThreads(threads);
        NetBeansSingleton netBeansSingleton1 = NetBeansSingleton.getInstance();
        netBeansSingleton1.business();
        NetBeansSingleton netBeansSingleton2 = NetBeansSingleton.getInstance();
        System.out.println("netBeansSingleton2 == netBeansSingleton2 => " + (netBeansSingleton1 == netBeansSingleton2));
    }

}
