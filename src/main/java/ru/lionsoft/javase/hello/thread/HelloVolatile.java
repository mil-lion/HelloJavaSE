/*
 * File:    HelloVolatile.java
 * Project: HelloJavaSE
 * Date:    24 нояб. 2019 г. 00:38:18
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloVolatile {

    // без ключевого слова volatile из потока изменение поля не видно и поток ждет бесконечно
    public static /*volatile*/ boolean flag = false; 

    public static void main(String[] args) throws InterruptedException {
        Runnable whileFlagFalse = () -> {
            System.out.println(Thread.currentThread().getName() + " started...");
            while(!flag) {
            }
            System.out.println("Flag is now TRUE");
        };

        new Thread(whileFlagFalse, "WhileFlagThread").start();
        Thread.sleep(1000);
        flag = true;
    }    
}
