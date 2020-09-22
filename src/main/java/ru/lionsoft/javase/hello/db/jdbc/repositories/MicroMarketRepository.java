/*
 * File:    MicroMarketRepository.java
 * Project: HelloJavaSE
 * Date:    24 авг. 2020 г. 13:04:26
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.repositories;

import ru.lionsoft.javase.hello.db.jdbc.dao.Repository;
import ru.lionsoft.javase.hello.db.jdbc.entities.MicroMarket;

/**
 * Репозиторий сущностей Магазин
 * @author Igor Morenko
 */
public interface MicroMarketRepository extends Repository<MicroMarket, String> {
    
}
