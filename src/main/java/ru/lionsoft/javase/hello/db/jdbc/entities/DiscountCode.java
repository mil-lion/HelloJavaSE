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
import ru.lionsoft.javase.hello.db.jdbc.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.annotation.PrimaryKey;
import ru.lionsoft.javase.hello.db.jdbc.annotation.Table;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Table(name = "DISCOUNT_CODE")
public class DiscountCode implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************
    
    // discountCode
    
    @PrimaryKey
    @Column(name = "DISCOUNT_CODE")
    private String code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    // rate
    
    private BigDecimal rate;

    /**
     * Get the value of rate
     *
     * @return the value of rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Set the value of rate
     *
     * @param rate new value of rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

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
