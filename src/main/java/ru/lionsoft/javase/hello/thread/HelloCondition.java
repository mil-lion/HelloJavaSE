/*
 * File:    HelloCondition.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 17:39:28
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Пример использования условных блокировок в потоках
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloCondition {
    
    // Класс Магазин, хранящий произведенные товары
    static class Store {

        private int product = 0;
        
        private final Lock locker;
        private final Condition condition;

        public Store() {
            locker = new ReentrantLock(); // создаем блокировку
            condition = locker.newCondition(); // получаем условие, связанное с блокировкой
        }

        public void get() {

            locker.lock();
            try {
                // пока нет доступных товаров на складе, ожидаем
                while (product < 1) {
                    condition.await();
                }

                product--;
                System.out.println("Покупатель купил 1 товар");
                System.out.println("Товаров на складе: " + product);

                // сигнализируем
                condition.signalAll();
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted!");
            } finally {
                locker.unlock();
            }
        }

        public void put() {

            locker.lock();
            try {
                // пока на складе 3 товара, ждем освобождения места
                while (product >= 3) {
                    condition.await();
                }

                product++;
                System.out.println("Производитель добавил 1 товар");
                System.out.println("Товаров на складе: " + product);
                // сигнализируем
                condition.signalAll();
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted!");
            } finally {
                locker.unlock();
            }
        }
    }
    
    // класс Производитель
    static class Producer implements Runnable {

        private final Store store;

        public Producer(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                store.put();
            }
        }
    }

    // Класс Потребитель
    static class Consumer implements Runnable {

        private final Store store;

        public Consumer(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                store.get();
            }
        }
    }

    public static void main(String[] args) {
        Store store = new Store(); // общий ресурс - Склад
        Producer producer = new Producer(store); // поставщик товара
        Consumer consumer = new Consumer(store); // потребитель товара
        new Thread(producer, "ProducerThread").start();
        new Thread(consumer, "ConsumerThread").start();
    }
}
