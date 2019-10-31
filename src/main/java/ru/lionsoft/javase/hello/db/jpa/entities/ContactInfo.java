/*
 * File:    ContactInfo.java
 * Project: HelloJavaSE
 * Date:    1 нояб. 2019 г. 00:20:49
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
public class ContactInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(length = 12)
    private String phone;

    @Column(length = 12)
    private String fax;

    @Column(length = 40)
    private String email;

    
    public ContactInfo() {
    }

    public ContactInfo(String phone, String fax, String email) {
        this.phone = phone;
        this.fax = fax;
        this.email = email;
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    @Override
    public String toString() {
        return "ContactInfo{" 
                + "phone=" + phone 
                + ", fax=" + fax 
                + ", email=" + email 
                + '}';
    }

}
