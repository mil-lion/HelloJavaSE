/*
 * File:    HelloLocker.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 17:35:47
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Пример синхронизации работы потоков с общим ресурсом используя класс ReentrantLock
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloLocker {
    
    /**
     * Класс общий ресурс
     */
    static class CommonResource {

        int x = 0;
    }

    /**
     * Класс запускаемый в потоке
     */
    static class CountThread implements Runnable {

        private final CommonResource res;
        private final Lock locker;

        CountThread(CommonResource res, Lock lock) {
            this.res = res;
            this.locker = lock;
        }

        @Override
        public void run() {

            final String threadName = Thread.currentThread().getName();
            try {
                locker.lock(); // устанавливаем блокировку
                res.x = 1;
                for (int i = 1; i < 5; i++) {
                    System.out.printf("%s: x = %d\n", threadName, res.x);
                    res.x++;
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted!");
            } finally {
                locker.unlock(); // снимаем блокировку
            }
        }
    }

    public static void main(String[] args) {
        CommonResource commonResource = new CommonResource(); // общий ресурс
        Lock locker = new ReentrantLock(); // создаем заглушку
        for (int i = 0; i < 5; i++) {
            new Thread(new CountThread(commonResource, locker), "Thread #" + i).start();
        }
    }
}
