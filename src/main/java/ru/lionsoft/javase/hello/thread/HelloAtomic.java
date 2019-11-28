/*
 * File:    HelloAtomic.java
 * Project: HelloJavaSE
 * Date:    24 нояб. 2019 г. 00:46:33
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloAtomic {
    
    public static int value = 0;
    public static AtomicInteger atomic = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                value++;
                atomic.incrementAndGet();
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
        Thread.sleep(300);
        System.out.println(value);
        System.out.println(atomic.get());
    }
}
