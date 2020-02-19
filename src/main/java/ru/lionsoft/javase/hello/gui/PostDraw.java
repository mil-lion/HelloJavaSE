/*
 * File:    PostDraw.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 22:11:27
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
 * Аннотация для методов которые должны вызваться после отрисовки фигуры
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostDraw {
    
    int order() default 1;
}
