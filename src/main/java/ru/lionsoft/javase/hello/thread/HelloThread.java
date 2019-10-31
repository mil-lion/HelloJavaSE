/*
 * File:    HelloThread.java
 * Project: HelloJavaSE
 * Date:    12 сент. 2019 г. 21:54:53
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloThread {

    /**
     *
     * @author Igor Morenko <morenko at lionsoft.ru>
     */
    static class JThread extends Thread {

        JThread(String name) {
            super(name);
        }

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
    }

    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("#### Current Thread info:");
        Thread t = Thread.currentThread(); // получаем главный (текущий) поток
        System.out.println(t.getName()); // main
        System.out.println(t); // main

        System.out.println("\n#### Thread test:");
        System.out.println("Main thread started...");
        for (int i = 1; i < 6; i++) {
            new JThread("JThread #" + i).start();
        }
        System.out.println("Main thread finished...");

        Thread.sleep(1000);

        System.out.println("\n#### Wait end of Thread:");
        System.out.println("Main thread started...");
        t = new JThread("JThread ");
        t.start();
        try {
            t.join(); // Waits for this thread to die.
        } catch (InterruptedException e) {
            System.out.printf("%s has been interrupted", t.getName());
        }
        System.out.println("Main thread finished...");

    }
}
