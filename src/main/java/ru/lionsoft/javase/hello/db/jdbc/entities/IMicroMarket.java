/*
 * File:    IMicroMarket.java
 * Project: HelloJavaSE
 * Date:    30 окт. 2019 г. 21:03:12
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

/**
 * Интерфейс описывающий сущность из базы данных - Микро Маркет (магазин)
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface IMicroMarket {
    
    String getZipCode();
    void setZipCode(String zip);
    
    
}
