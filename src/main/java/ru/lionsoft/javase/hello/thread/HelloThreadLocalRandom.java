/*
 * File:    HelloThreadLocalRandom.java
 * Project: HelloJavaSE
 * Date:    12 авг. 2020 г. 00:38:18
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Igor Morenko (emailto:imorenko@yandex.ru)
 */
public class HelloThreadLocalRandom {

    public static void main(String[] args) throws InterruptedException {

        int boundedRandomValue = ThreadLocalRandom.current().nextInt(0, 100);
        System.out.println("boundedRandomValue: " + boundedRandomValue);

        long startTime = System.currentTimeMillis();
        testRandom();
        System.out.println("duration: " + (System.currentTimeMillis() - startTime) + " ms");
        
        startTime = System.currentTimeMillis();
        testThreadLocalRandom();
        System.out.println("duration: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    private static void testRandom() throws InterruptedException {
        System.out.println("#### Test Random ####");
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<Integer>> callables = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            callables.add(() -> {
                return random.nextInt();
            });
        }
        executor.invokeAll(callables);
    }

    private static void testThreadLocalRandom() throws InterruptedException {
        System.out.println("#### Test ThreadLocalRandom ####");
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            callables.add(() -> {
                return ThreadLocalRandom.current().nextInt();
            });
        }
        executor.invokeAll(callables);
    }
}
