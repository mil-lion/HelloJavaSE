/*
 * File:    ICistomer.java
 * Project: HelloJavaSE
 * Date:    12 сент. 2019 г. 22:55:55
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

/**
 * Интерфейс описывающий сущность из базы данных - Клиент
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface ICistomer {
    
    Integer getCistomerId();
    void setCustomerId(Integer customerId);
    
    String getName();
    void setName(String name);
    
    String getAddressline1();
    void setAddressline1(String addressline1);
    
    String getAddressline2();
    void setAddressline2(String addressline2);
    
    String getCity();
    void setCity(String city);
    
    String getState();
    void setState(String state);
    
    String getEmail();
    void setEmail(String email);
    
    String getPhone();
    void setPhone(String phone);
    
    String getFax();
    void setFax(String fax);
    
    Integer getCreaditLimit();
    void setCreditLimit(Integer creditLimit);
}
