/*
 * File:    Repository.java
 * Project: HelloJavaSE
 * Date:    24 авг. 2020 г. 12:51:39
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.dao;

import java.util.List;

/**
 *
 * @author Igor Morenko
 * @param <E> тип сущности
 * @param <PK> тип первичного ключа сущности
 */
public interface Repository<E, PK> {
    
    void create(E entity);
    
    void update(E entity);
    
    void delete(E entity);
    
    E find(PK id);
    
    List<E> findAll();
    
    int count();
}
