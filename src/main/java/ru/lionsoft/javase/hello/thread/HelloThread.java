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
 * Пример создания потоков, использование наследования класса Thread
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloThread {
    
    static class JThread extends Thread {

        public JThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            final String threadName = getName();
            System.out.printf("%s started...\n", threadName);
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                System.out.println("Thread has been interrupted!");
            }
            System.out.printf("%s finished...\n", threadName);
        }
    }
    
    static class JThreadInterrupt extends Thread {

        public JThreadInterrupt(String name) {
            super(name);
        }

        @Override
        public void run() {
            final String threadName = getName();
            System.out.printf("%s started...\n", threadName);
            int counter = 1;
            while (!isInterrupted()) {
                System.out.println("Loop #" + counter++);
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("Thread has been interrupted!");
                    System.out.println("isInterrupted = " + isInterrupted()); // false
//                    interrupt(); // повторно устанавливаем флаг прерывания потока!
                    break; // выход из цикла!
                }
            }
            System.out.printf("%s finished...\n", threadName);
        }
    }
    
    /**
     * Главный метод для запуска
     * @param args аргументы командной строки
     * @throws InterruptedException если было вызвано прерывание потока
     */
    public static void main(String[] args) throws InterruptedException {
        
        // Информация о главном потоке
        Thread t = Thread.currentThread(); // получаем главный поток
        System.out.println(t.getName()); // main
        System.out.println(t); // Thread[main,5,main]
        
        // Запускаем 10 потоков
        System.out.println("Main thread started...");
        for (int i = 0; i < 10; i++) {
            new JThread("JThread #" + i).start();
        }
        System.out.println("Main thread finished...");
        
        Thread.sleep(1000);
        
        // Запуск потока без ожидания его завершения
        System.out.println("Main thread started...");
        new JThread("JThread").start();
        System.out.println("Main thread finished...");

        Thread.sleep(1000);
        
        // Запускаем поток и ждем его завершения
        System.out.println("Main thread started...");
        t = new JThread("JThread"); // создаем поток
        t.start(); // запускаем поток
        t.join(); // ждем завершения потока
        System.out.println("Main thread finished...");

        Thread.sleep(1000);

        // Запускаем поток и прерываем его вызовом метода interrupt()
        System.out.println("Main thread started...");
        t = new JThreadInterrupt("JThreadInterrupt");
        t.start();
        Thread.sleep(1000);
        t.interrupt(); // вызов прерывания потока
        t.join();
        System.out.println("Main thread finished...");
    }
}
