/*
 * File:    CustomerRepository.java
 * Project: HelloJavaSE
 * Date:    24 авг. 2020 г. 13:02:12
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.repositories;

import java.math.BigDecimal;
import java.util.List;
import ru.lionsoft.javase.hello.db.jdbc.dao.Repository;
import ru.lionsoft.javase.hello.db.jdbc.entities.DiscountCode;

/**
 * Репозиторий сущностей Скидка
 * @author Igor Morenko
 */
public interface DiscountCodeRepository extends Repository<DiscountCode, String> {
    
    List<DiscountCode> findByRate(BigDecimal rate);
}
