/*
 * File:    HelloDate.java
 * Project: HelloJavaSE
 * Date:    20 сент. 2019 г. 13:08:58
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloDate {
    
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(date);
        long diff = ChronoUnit.DAYS.between(LocalDate.of(2019, 1, 1), LocalDate.now());
        System.out.println("days = " + diff);

    }
}
