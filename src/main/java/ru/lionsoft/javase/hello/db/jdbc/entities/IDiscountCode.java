/*
 * File:    IDiscountCode.java
 * Project: HelloJavaSE
 * Date:    30 окт. 2019 г. 21:01:14
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

import java.math.BigDecimal;

/**
 * Интерфейс описывающий сущность из базы данных - Скидка
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface IDiscountCode {
    
    String getDiscountCode();
    void setDiscountCode(String code);
    
    BigDecimal getRate();
    void setRate(BigDecimal rate);
    
}
