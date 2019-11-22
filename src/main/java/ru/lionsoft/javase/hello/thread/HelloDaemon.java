/*
 * File:    HelloDaemon.java
 * Project: HelloJavaSE
 * Date:    21 нояб. 2019 г. 21:56:05
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 * Пример работы потока помеченного как "демон"
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloDaemon extends Thread {

    public HelloDaemon(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        System.out.printf("%s started...\n", getName());
        try {
            // Checking whether the thread is Daemon or not
            if (isDaemon()) {
                System.out.println("Daemon thread executing");
                Thread.sleep(500);
            } else {
                System.out.println("User(normal) thread executing");
                Thread.sleep(200);
            }
        } catch (InterruptedException ex) {
            System.out.printf("Thread %s has been interrupted!\n", getName());
        }
        System.out.printf("%s finished...\n", getName());
    }
    
    public static void main(String[] args) {
        /*
         * Creating two threads: by default they are 
         * user threads (non-daemon threads)
         */
        Thread t1 = new HelloDaemon("DaemonThread");
        Thread t2 = new HelloDaemon("UserThread");
        
        // Making user thread t1 to Daemon
        t1.setDaemon(true);
        
        // Starting both the threads
        t1.start();
        t2.start();
    }
    
}
