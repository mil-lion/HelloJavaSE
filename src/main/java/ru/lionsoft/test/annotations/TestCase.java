/*
 * File:    TestCase.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2019 г. 22:09:20
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
 * Анотация для задания на тестирования
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase {
    
    /**
     * Игнорировать тест кейс
     * @return {@code true} если нужно пропустить тест
     */
    boolean ignore() default false;
    
    /**
     * Порядковый номер отработки теста
     * @return порядковый номер теста
     */
    int order() default 1;
    
}
