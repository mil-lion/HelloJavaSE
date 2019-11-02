/*
 * File:    HelloSynchronized.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 01:11:33
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 * Пример синхронизации доступа к общим ресурсам
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloSynchronized {
    
    static class CommonResource {
        int x = 0;
        
        public synchronized void increment(String threadName) {
            x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s: x = %d\n", threadName, x);
                x++;
                try {Thread.sleep(100);} catch (InterruptedException ex) {}
            }
        }
    }
    
    static class CountThreadNoSync implements Runnable {

        private final CommonResource res;

        public CountThreadNoSync(CommonResource res) {
            this.res = res;
        }
        
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            res.x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s: x = %d\n", threadName, res.x);
                res.x++;
                try {Thread.sleep(100);} catch (InterruptedException ex) {}
            }
        }
    }

    static class CountThreadSyncBlock implements Runnable {

        private final CommonResource res;

        public CountThreadSyncBlock(CommonResource res) {
            this.res = res;
        }
        
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            synchronized (res) {
                res.x = 1;
                for (int i = 1; i < 5; i++) {
                    System.out.printf("%s: x = %d\n", threadName, res.x);
                    res.x++;
                    try {Thread.sleep(100);} catch (InterruptedException ex) {}
                }
            }
        }
    }
    
    static class CountThreadSyncMethod implements Runnable {

        private final CommonResource res;

        public CountThreadSyncMethod(CommonResource res) {
            this.res = res;
        }
        
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            res.increment(threadName);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        CommonResource res = new CommonResource();

        System.out.println("##### Test 0 - No Synchronize #####");
        for (int i = 0; i < 6; i++) {
            new Thread(new CountThreadNoSync(res), "Test0 Thread #" + i).start();
        }
        
        System.out.println("\n##### Test 1 - Synchronize Block #####");
        for (int i = 0; i < 6; i++) {
            new Thread(new CountThreadSyncBlock(res), "Test1 Thread #" + i).start();
        }

        Thread.sleep(3000);

        System.out.println("\n##### Test 2 - Synchronize Method #####");
        for (int i = 0; i < 6; i++) {
            new Thread(new CountThreadSyncMethod(res), "Test2 Thread #" + i).start();
        }

        Thread.sleep(3000);

        System.out.println("\n##### Test 3 - Two Resource - SyncBlock  #####");
        CommonResource res2 = new CommonResource();
        for (int i = 0; i < 6; i++) {
            new Thread(new CountThreadSyncBlock(i % 2 == 0 ? res : res2), "Test3 Thread #" + i).start();
        }

        Thread.sleep(3000);

        System.out.println("\n##### Test 4 - Two Resource - SyncMethod #####");
        for (int i = 0; i < 6; i++) {
            new Thread(new CountThreadSyncMethod(i % 2 == 0 ? res : res2), "Test4 Thread #" + i).start();
        }
    }
}
