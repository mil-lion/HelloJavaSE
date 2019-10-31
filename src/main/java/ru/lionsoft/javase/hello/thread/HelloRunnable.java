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
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloRunnable {

    static class MyThread implements Runnable {

        public void run() {
            final String currentThreadName = Thread.currentThread().getName();

            System.out.printf("%s started... \n", currentThreadName);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
            }
            System.out.printf("%s finished... \n", currentThreadName);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("#### Runnable Interface:");
        System.out.println("Main thread started...");
        Thread myThread = new Thread(new MyThread(), "MyThread");
        myThread.start();
        System.out.println("Main thread finished...");

        Thread.sleep(1000);

        System.out.println("\n#### Anonymouse class for Thread:");

        System.out.println("Main thread started...");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final String currentThreadName = Thread.currentThread().getName();
                System.out.printf("%s started... \n", currentThreadName);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
                System.out.printf("%s finished... \n", currentThreadName);
            }
        };
        Thread t = new Thread(r, "MyThread");
        t.start();
        System.out.println("Main thread finished...");

        Thread.sleep(1000);

        System.out.println("\n#### Anonymouse class for Thread (lyambda):");
        System.out.println("Main thread started...");
        r = () -> {
            final String currentThreadName = Thread.currentThread().getName();
            System.out.printf("%s started... \n", currentThreadName);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
            }
            System.out.printf("%s finished... \n", currentThreadName);
        };
        t = new Thread(r, "MyThread");
        t.start();
        System.out.println("Main thread finished...");
    }
}
