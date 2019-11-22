/*
 * File:    HelloExecutor.java
 * Project: HelloJavaSE
 * Date:    22 нояб. 2019 г. 10:00:10
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Примеры использования интерфейса Executor
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Executor interface
        Runnable task1 = () -> System.out.println("Task executed");
        Executor executor = (runnable) -> {
            new Thread(runnable).start();
        };
        executor.execute(task1);

        // Executor Service
        Callable<String> task2 = () -> Thread.currentThread().getName();
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Future result = service.submit(task2);
            System.out.println(result.get());
        }
        service.shutdown();
    }
}
