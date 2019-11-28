/*
 * File:    TestSuite.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2019 г. 22:09:12
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.test.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация определяющая набор тестов
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestSuite {
    
    /**
     * Имя набора тестов
     * @return имя набора тестов
     */
    String name() default "";
}
