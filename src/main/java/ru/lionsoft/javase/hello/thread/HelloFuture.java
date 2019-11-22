/*
 * File:    HelloFuture.java
 * Project: HelloJavaSE
 * Date:    21 нояб. 2019 г. 23:27:01
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Пример работы с Callable интерфейсом и асинхронное выполнение функции
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloFuture {

    public static void main(String[] args) throws Exception {
        Callable<String> task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            return "Hello World!";
        };
        FutureTask<String> future = new FutureTask<>(task);
        new Thread(future).start();
        System.out.println(future.get());
    }
}
