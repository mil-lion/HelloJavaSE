/*
 * File:    HelloRunnable.java
 * Project: HelloJavaSE
 * Date:    12 сент. 2019 г. 21:57:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 * Пример использования интерфейса Runnable
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloRunnable {

    static class MyThread implements Runnable {

        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            System.out.printf("%s started...\n", threadName);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.printf("Thread %s has been interrupted!\n", threadName);
            }
            System.out.printf("%s finished...\n", threadName);
        }
    }

    static class MyThreadLoop implements Runnable {

        private boolean isActive; // флаг активности потока

        public MyThreadLoop() {
            isActive = true;
        }
        
        public void disable() {
            isActive = false;
        }
        
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            System.out.printf("%s started...\n", threadName);
            int counter = 1; // счетчик циклов
            while (isActive) {
                System.out.println("Loop #" + counter++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.printf("Thread %s has been interrupted!\n", threadName);
                }
            }
            System.out.printf("%s finished...\n", threadName);
        }
    }

    static class MyThreadInterrupt implements Runnable {

        @Override
        public void run() {
            final Thread currentThread = Thread.currentThread(); // текущий поток
            final String threadName = currentThread.getName();
            System.out.printf("%s started...\n", threadName);
            int counter = 1; // счетчик циклов
            try {
                while (!currentThread.isInterrupted()) {
                    System.out.println("Loop #" + counter++);
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                System.out.printf("Thread %s has been interrupted!\n", threadName);
            }
            System.out.printf("%s finished...\n", threadName);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("#### Runnable Interface:");
        System.out.println("Main thread started...");
        Thread t = new Thread(new MyThread(), "MyThread");
        t.start();
        System.out.println("Main thread finished...");
        
        Thread.sleep(1000);
        
        System.out.println("\n#### Anonymous class for Thread:");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.printf("%s started...\n", threadName);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    System.out.println("Thread has been interrupted!");
                }
                System.out.printf("%s finished...\n", threadName);
            }
        };

        System.out.println("Main thread started...");
        new Thread(r, "AnonymousThread").start();
        System.out.println("Main thread finished...");
        
        Thread.sleep(1000);
        
        System.out.println("\n#### Anonymous class for Thread (lyambda):");
        System.out.println("Main thread started...");
        new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("%s started...\n", threadName);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    System.out.println("Thread has been interrupted!");
                }
                System.out.printf("%s finished...\n", threadName);
            }, "LambdaThread").start();
        System.out.println("Main thread finished...");
        
        Thread.sleep(1000);
        
        System.out.println("\n#### Thread with Loop:");
        System.out.println("Main thread started...");
        MyThreadLoop myThread = new MyThreadLoop();
        t = new Thread(myThread, "MyThreadLoop");
        t.setPriority(1);
        t.start();
        Thread.sleep(1000);
        myThread.disable(); // останавливаем поток
        t.join(); // ждем завершения потока
        System.out.println("Main thread finished...");
        
        Thread.sleep(1000);
        
        System.out.println("\n#### Interrupted Thread:");
        System.out.println("Main thread started...");
        t = new Thread(new MyThreadInterrupt(), "MyThreadInterrupt");
        t.start();
        Thread.sleep(1000);
        t.interrupt(); // прерываем поток
        t.join(); // ждем завершения потока
        System.out.println("Main thread finished...");
    }
}
