/*
 * File:    HelloCyclicBarrier.java
 * Project: HelloJavaSE
 * Date:    21 нояб. 2019 г. 23:00:52
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Пример использования циклического барьера (CyclicBarrier)
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloCyclicBarrier {

    private static final int THREAD_COUNT = 3;

    public static void main(String[] args) {
        Runnable action = () -> System.out.println("На старт!");
        CyclicBarrier berrier = new CyclicBarrier(THREAD_COUNT, action);
        Runnable task = () -> {
            final String threadName = Thread.currentThread().getName();
            try {
                System.out.println(threadName + " Started...");
                berrier.await();
                System.out.println(threadName + " Finished");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        System.out.println("Limit: " + berrier.getParties());
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(task, "Task #" + i).start();
        }
    }

}
