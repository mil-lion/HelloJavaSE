/*
 * File:    CustomerRepository.java
 * Project: HelloJavaSE
 * Date:    24 авг. 2020 г. 13:02:12
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.repositories;

import java.util.List;
import ru.lionsoft.javase.hello.db.jdbc.dao.Repository;
import ru.lionsoft.javase.hello.db.jdbc.entities.Customer;

/**
 * Репозиторий сущностей Клиент
 * @author Igor Morenko
 */
public interface CustomerRepository extends Repository<Customer, Integer> {
    
    List<Customer> findByName(String name);
}
