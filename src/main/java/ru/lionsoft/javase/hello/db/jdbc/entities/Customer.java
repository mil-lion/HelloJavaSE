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
import ru.lionsoft.javase.hello.db.jdbc.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.annotation.PrimaryKey;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************
    
    // id
    
    @PrimaryKey
    @Column(name = "CUSTOMER_ID")
    private Integer id;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    // discountCode
    
    @Column(name = "DISCOUNT_CODE")
    private String discountCode;

    /**
     * Get the value of discountCode
     *
     * @return the value of discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * Set the value of discountCode
     *
     * @param discountCode new value of discountCode
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    // zip
    
    private String zip;

    /**
     * Get the value of zip
     *
     * @return the value of zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Set the value of zip
     *
     * @param zip new value of zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    // name
    
    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    // addressline1
    
    private String addressline1;

    /**
     * Get the value of addressline1
     *
     * @return the value of addressline1
     */
    public String getAddressline1() {
        return addressline1;
    }

    /**
     * Set the value of addressline1
     *
     * @param addressline1 new value of addressline1
     */
    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    // addressline2
    
    private String addressline2;

    /**
     * Get the value of addressline2
     *
     * @return the value of addressline2
     */
    public String getAddressline2() {
        return addressline2;
    }

    /**
     * Set the value of addressline2
     *
     * @param addressline2 new value of addressline2
     */
    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    // city
    
    private String city;

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    // state
    
    private String state;

    /**
     * Get the value of state
     *
     * @return the value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the value of state
     *
     * @param state new value of state
     */
    public void setState(String state) {
        this.state = state;
    }

    // phone
    
    private String phone;

    /**
     * Get the value of phone
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the value of phone
     *
     * @param phone new value of phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // fax
    
    private String fax;

    /**
     * Get the value of fax
     *
     * @return the value of fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Set the value of fax
     *
     * @param fax new value of fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    // email
    
    private String email;

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    // creditLimit
    
    @Column(name = "CREDIT_LIMIT")
    private Integer creditLimit;

    /**
     * Get the value of creditLimit
     *
     * @return the value of creditLimit
     */
    public Integer getCreaditLimit() {
        return creditLimit;
    }

    /**
     * Set the value of creditLimit
     *
     * @param creditLimit new value of creditLimit
     */
    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    // ******************** Constructors *******************
    
    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
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
