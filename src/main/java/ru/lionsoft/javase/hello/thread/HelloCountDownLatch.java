/*
 * File:    HelloCountDownLatch.java
 * Project: HelloJavaSE
 * Date:    21 нояб. 2019 г. 22:51:59
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Пример использования CountDownLatch (защелка с отчетом)
 * Используется для одновременного старта нескольких потоков
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloCountDownLatch {

    private static final int THREAD_COUNT = 3;
    
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        Runnable task = () -> {
            final String threadName = Thread.currentThread().getName();
            try {
                countDownLatch.countDown();
                System.out.println(threadName + " Countdown: " + countDownLatch.getCount());
                countDownLatch.await();
                System.out.println(threadName + " Started...");
                Thread.sleep(100 + new Random().nextInt(400));
                System.out.println(threadName + " Finished...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(task, "Thread #" + i).start();
        }
    }
}
