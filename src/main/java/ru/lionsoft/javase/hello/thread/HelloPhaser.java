/*
 * File:    HelloPhaser.java
 * Project: HelloJavaSE
 * Date:    2 нояб. 2019 г. 17:28:34
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * Пример синхронизации (фазирования) выполнения этапов в потоках
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloPhaser {
    
    static class PhaseThread implements Runnable {

        private final Phaser phaser;

        public PhaseThread(Phaser phaser) {
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            Random r = new Random();
            try {
                Thread.sleep(10);
                System.out.println(name + " выполняет фазу " + phaser.getPhase());
                Thread.sleep(100 + r.nextInt(900));
                phaser.arriveAndAwaitAdvance(); // сообщаем, что первая фаза достигнута

                Thread.sleep(10);
                System.out.println(name + " выполняет фазу " + phaser.getPhase());
                Thread.sleep(100 + r.nextInt(900));
                phaser.arriveAndAwaitAdvance(); // сообщаем, что вторая фаза достигнута

                Thread.sleep(10);
                System.out.println(name + " выполняет фазу " + phaser.getPhase());
                Thread.sleep(100 + r.nextInt(900));
                phaser.arriveAndDeregister(); // сообщаем о завершении фаз и удаляем с регистрации объекты 
            } catch (InterruptedException ex) {
                System.out.println("Thread has been interrupted!");
            }
        }
    }

    public static void main(String[] args) {
        
        Phaser phaser = new Phaser(1);
        for (int i = 0; i < 5; i++) {
            new Thread(new PhaseThread(phaser), "PhaseThread #" + i).start();
        }

        // ждем завершения фазы 0
        int phase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + phase + " завершена");
        
        // ждем завершения фазы 1
        phase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + phase + " завершена");

        // ждем завершения фазы 2
        phase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + phase + " завершена");

        phaser.arriveAndDeregister();
    }
}
