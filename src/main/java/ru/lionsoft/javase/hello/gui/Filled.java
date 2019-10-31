/*
 * File:    Filled.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 22:08:52
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация классов реализующих закрашенные (залитой) фигуры
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Filled {
    /**
     * Имя закрашенной фигуры
     * @return имя фигуры, по умолчанию будет использоваться имя класса
     */
    String name() default "";
}
