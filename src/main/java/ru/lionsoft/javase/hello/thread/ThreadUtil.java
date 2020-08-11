/*
 * File:    ThreadUtil.java
 * Project: HelloJavaSE
 * Date:    11 авг. 2020 г. 09:37:39
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.thread;

import java.util.Arrays;

/**
 *
 * @author Igor Morenko
 */
public class ThreadUtil {
    
    public static void startAndJoinThreads(Thread[] threads) {
        // start all threads
        Arrays.stream(threads).filter(t -> t != null)
                .forEach(t -> t.start());
        // join all threads
        Arrays.stream(threads).filter(t -> t != null)
                .forEach(t -> {
                    try { t.join(); } catch (InterruptedException ex) {}
                });
    }
}
