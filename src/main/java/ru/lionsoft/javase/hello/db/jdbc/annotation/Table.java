/*
 * File:    Table.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:09:25
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
 * Анотация для указания имени таблицы СУБД
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    
    /**
     * Имя таблицы СУБД
     * @return имя таблицы
     */
    String name();
}
