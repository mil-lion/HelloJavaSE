/*
 * File:    Customer.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:10:52
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

import java.io.Serializable;
import java.util.Objects;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Id;

/**
 * Сущность Клиент
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************
    
    // id
    
    @Id
    @Column(name = "CUSTOMER_ID")
    public Integer id;

    // discountCode
    
    @Column(name = "DISCOUNT_CODE")
    public String discountCode;

    // zip
    
    @Column
    public String zip;

    // name

    @Column
    public String name;

    // addressline1

    @Column
    public String addressline1;

    // addressline2

    @Column
    public String addressline2;

    // city

    @Column
    public String city;

    // state

    @Column
    public String state;

     // phone
 
    @Column
    public String phone;

    // fax

    @Column
    public String fax;

    // email

    @Column
    public String email;

    // creditLimit
    
    @Column(name = "CREDIT_LIMIT")
    public Integer creditLimit;

    // ******************** Constructors *******************
    
    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public Customer(Integer id, String discountCode, String zip) {
        this.id = id;
        this.discountCode = discountCode;
        this.zip = zip;
    }
    
    // ******************** Equals & HashCode *******************
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.id, other.id);
    }

    // ******************** Cast to String *******************
    
    @Override
    public String toString() {
        return "Customer{"
                + "id=" + id
                + ", discountCode=" + discountCode
                + ", zip=" + zip
                + ", name=" + name
                + ", addressline1=" + addressline1
                + ", addressline2=" + addressline2
                + ", city=" + city
                + ", state=" + state
                + ", phone=" + phone
                + ", fax=" + fax
                + ", email=" + email
                + ", creditLimit=" + creditLimit
                + '}';
    }

}
