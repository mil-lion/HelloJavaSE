/*
 * File:    HelloWait.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 01:24:12
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

/**
 * Пример использования методов wait & notify объектов
 * 
 * Рассмотрим, как мы можем использовать эти методы. 
 * Возьмем стандартную задачу из прошлой темы - "Производитель-Потребитель" 
 * ("Producer-Consumer"): пока производитель не произвел продукт, потребитель 
 * не может его купить. Пусть производитель должен произвести 5 товаров, 
 * соответственно потребитель должен их все купить. Но при этом одновременно 
 * на складе может находиться не более 3 товаров. Для решения этой задачи 
 * задействуем методы wait() и notify()
 * 
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloWait {

    // Класс Магазин, хранящий произведенные товары
    static class Store {
        private int product = 0;
        
        /**
         * Метод - купить товар
         */
        public synchronized void get() {
            while (product < 1) {
                try {
                    wait(); // ждем поступления товара на склад
                } catch (InterruptedException ex) { }
            }
            product--;
            System.out.println("Покупатель купил 1 товар");
            System.out.println("Товаров на складе: " + product);
            notify(); // сообщаем что купили товар и нужно пополнить склад
        }
        
        /**
         * Метод - поставить товар на склад
         */
        public synchronized void put() {
            while (product >= 3) {
                try {
                    wait(); // ждем когда освободится место на складе
                } catch (InterruptedException ex) { }
            }
            product++;
            System.out.println("Производитель добавил 1 товар");
            System.out.println("Товаров на складе: " + product);
            notify(); // сообщаем что поставили товар на склад и можно купить
        } 
    }
    
    // Класс Производитель (поставщик)
    static class Producer implements Runnable {

        private final Store store; // склад

        public Producer(Store store) {
            this.store = store;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                store.put();
            }
        }
        
    }
    
    // Класс Потребитель
    static class Consumer implements Runnable {

        private final Store store; // склад

        public Consumer(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5 ; i++) {
                store.get();
            }
        }
        
    }
    
    public static void main(String[] args) {
        Store store = new Store(); // общий ресурс - склад
        Producer producer = new Producer(store); // производитель
        Consumer consumer = new Consumer(store); // потребитель
        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer").start();
    }
}
