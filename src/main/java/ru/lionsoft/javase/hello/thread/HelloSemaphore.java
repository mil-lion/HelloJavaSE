/*
 * File:    HelloSemaphore.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 01:34:45
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.Semaphore;

/**
 * Пример использования семафоров
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloSemaphore {

    static class CommonResource {

        int x = 0;
    }

    static class CountThread implements Runnable {

        private final CommonResource res;
        private final Semaphore sem;

        public CountThread(CommonResource res, Semaphore sem) {
            this.res = res;
            this.sem = sem;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            try {
                System.out.println(threadName + " ожидает разрешение");
                sem.acquire();
                System.out.println(threadName + " получил разрешение");
                res.x = 1;
                for (int i = 0; i < 5; i++) {
                    System.out.println(threadName + ": x = " + res.x);
                    res.x++;
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                System.out.println("Thread has been interrupted!");
            }
            System.out.println(threadName + " освобождает разрешение");
            sem.release();
        }
    }

    // класс философа
    static class Philosopher extends Thread {

        // семафор. ограничивающий число философов
        private final Semaphore sem; 

        // условный номер философа
        private final int id;

        // кол-во приемов пищи
        private int num = 0;
        
        // в качестве параметров конструктора передаем идентификатор философа и семафор
        public Philosopher(Semaphore sem, int id) {
            this.sem = sem;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (num < 3)// пока количество приемов пищи не достигнет 3
                {
                    // Запрашиваем у семафора разрешение на выполнение
                    sem.acquire();
                    System.out.println("Философ " + id + " садится за стол");
                    
                    // философ ест
                    sleep(500);
                    num++;

                    System.out.println("Философ " + id + " выходит из-за стола");
                    sem.release();

                    // философ гуляет
                    sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("у философа " + id + " проблемы со здоровьем");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        
        Semaphore sem = new Semaphore(1); // 1 разрешение
        CommonResource res = new CommonResource();
        for (int i = 0; i < 5; i++) {
            new Thread(new CountThread(res, sem), "Thread #" + i).start();
        }
        
        Thread.sleep(5000);
        
        /*
         * Семафоры отлично подходят для решения задач, где надо ограничивать 
         * доступ. Например, классическая задача про обедающих философов. 
         * Ее суть: есть несколько философов, допустим, пять, но одновременно 
         * за столом могут сидеть не более двух. И надо, чтобы все философы 
         * пообедали, но при этом не возникло взаимоблокировки философами друг 
         * друга в борьбе за тарелку и вилку:
         */
        Semaphore sem2 = new Semaphore(2);
        for (int i = 1; i <= 5; i++) {
            new Philosopher(sem2, i).start();
        }
    }
}
