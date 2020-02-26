/*
 * File:    PrimaryKey.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 21:47:16
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
 * Анотация для колонки таблицы являющейся первичным ключом
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    
}
