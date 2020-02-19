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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Пример работы с Callable интерфейсом и асинхронное выполнение функции
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloFuture {

    public static void main(String[] args) throws Exception {

        testFutureTask();
        testCompletableFuture();
    }
    
    public static void testFutureTask() throws Exception {
        Callable<String> task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            return "Hello World!";
        };
        FutureTask<String> future = new FutureTask<>(task);
        new Thread(future).start();
        String hello = null;
        int counter = 0;
        do {
            try {
                hello = future.get(10, TimeUnit.MILLISECONDS);
            } catch (TimeoutException ex) {
                System.err.println("Timeout! #" + ++counter);
            }
        } while (hello == null);
        System.out.println("Result: " + hello);
        
    }
    
    public static void testCompletableFuture() throws Exception {
        // CompletableFuture уже содержащий результат
        CompletableFuture<String> completed;
        completed = CompletableFuture.completedFuture("Просто значение");
        
        // CompletableFuture, запускающий (run) новый поток с Runnable, поэтому он Void
        CompletableFuture<Void> voidCompletableFuture;
        voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("run " + Thread.currentThread().getName());
        });
        
        // CompletableFuture, запускающий новый поток, результат которого возьмём у Supplier
        CompletableFuture<String> supplier;
        supplier = CompletableFuture.supplyAsync(() -> {
            System.out.println("supply " + Thread.currentThread().getName());
            return "Значение";
        });
        System.out.println(supplier.get());
    }
}
