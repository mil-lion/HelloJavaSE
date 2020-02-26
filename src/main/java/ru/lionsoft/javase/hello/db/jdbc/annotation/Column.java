/*
 * File:    Column.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:08:05
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотация для указания имени колонки в СУБД
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    
    /**
     * Имя колонки таблицы СУБД
     * @return имя колонки
     */
    String name();
}
