/*
 * File:    Address.java
 * Project: HelloJavaSE
 * Date:    1 нояб. 2019 г. 00:15:32
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jpa.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Embeddable
public class Address implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(length = 30)
    private String addressline1;

    @Column(length = 30)
    private String addressline2;

    @Column(length = 25)
    private String city;

    @Column(length = 2)
    private String state;

    
    public Address() {
    }

    public Address(String addressline1, String addressline2, String city, String state) {
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.state = state;
    }
    
    
    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    
    @Override
    public String toString() {
        return "Address{" 
                + "addressline1=" + addressline1 
                + ", addressline2=" + addressline2 
                + ", city=" + city 
                + ", state=" + state 
                + '}';
    }

}
