/*
 * File:    MicroMarketFacade.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 22:28:00
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.facades;

import java.sql.Connection;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.entities.MicroMarket;

/**
 * Фасад для сущности Магазин
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class MicroMarketFacade extends AbstractFacade<MicroMarket> {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(MicroMarketFacade.class.getName());

    /**
     * Конструктор фасада для сущности
     * @param connection соединение с СУБД
     */
    public MicroMarketFacade(Connection connection) {
        super(MicroMarket.class, connection);
    }
    
}
