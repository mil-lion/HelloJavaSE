/*
 * File:    HelloExchanger.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 17:31:13
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.concurrent.Exchanger;

/**
 * Пример обмена информацией между потоками
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloExchanger {
    
    /**
     * Поток посылает сообщение Hello Java!
     */
    static class PutThread implements Runnable {

        private final Exchanger<String> exchanger;
        private String message;

        public PutThread(Exchanger<String> ex) {
            this.exchanger = ex;
            message = "Hello World!";
        }

        @Override
        public void run() {
            try {
                System.out.println("PutThread has sended: " + message);
                message = exchanger.exchange(message);
                System.out.println("PutThread has received: " + message);
            } catch (InterruptedException ex) {
                System.out.println("PutThread has been interrupted!");
            }
        }
    }

    /**
     * Поток получает сообщение и передает в ответ HelloWorld!
     */
    static class GetThread implements Runnable {

        private final Exchanger<String> exchanger;
        private String message;

        public GetThread(Exchanger<String> ex) {
            this.exchanger = ex;
            message = "Java Rulez!";
        }

        @Override
        public void run() {
            try {
                System.out.println("GetThread has sended: " + message);
                message = exchanger.exchange(message);
                System.out.println("GetThread has received: " + message);
            } catch (InterruptedException ex) {
                System.out.println("GetThread has been interrupted!");
            }
        }
    }

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>(); // обменник
        new Thread(new PutThread(exchanger), "PutThread").start();
        new Thread(new GetThread(exchanger), "GetThread").start();
        
    }
}
