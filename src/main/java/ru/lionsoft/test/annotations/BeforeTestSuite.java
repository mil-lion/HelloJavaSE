/*
 * File:    BeforeTestCase.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2019 г. 22:18:15
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
 * Аннотация метода запускаемого перед набором тестов
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeTestSuite {
    
}
