/*
 * File:    DiscountCode.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:34:29
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Id;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Table;

/**
 * Сущность Скидка
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Table(name = "DISCOUNT_CODE")
public class DiscountCode implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************
    
    // discountCode
    
    @Id
    @Column(name = "DISCOUNT_CODE")
    public String code;

    // rate
    
    @Column
    public BigDecimal rate;

    // ******************** Constructors *******************
    
    public DiscountCode() {
    }

    public DiscountCode(String code) {
        this.code = code;
    }

    // ******************** Equals & HashCode *******************

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiscountCode other = (DiscountCode) obj;
        return Objects.equals(this.code, other.code);
    }

    // ******************** Cast to String *******************

    @Override
    public String toString() {
        return "DiscountCode{" 
                + "code=" + code 
                + ", rate=" + rate 
                + '}';
    }
    
}
