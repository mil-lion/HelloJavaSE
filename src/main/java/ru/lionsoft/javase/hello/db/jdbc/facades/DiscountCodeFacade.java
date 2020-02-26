/*
 * File:    DiscountCodeFacade.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 22:26:56
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.facades;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.entities.DiscountCode;

/**
 * Фасад для сущности Скидка
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class DiscountCodeFacade extends AbstractFacade<DiscountCode> {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(DiscountCodeFacade.class.getName());
    
    /**
     * Конструктор фасада для сущности
     * @param connection соединение с СУБД
     */
   public DiscountCodeFacade(Connection connection) {
        super(DiscountCode.class, connection);
    }
 
    /**
     * Выбрать скидки по размеру скидки
     * @param rate размер скидки
     * @return список скидок
     * @throws SQLException ошибка SQL
     */
    public List<DiscountCode> findByRate(BigDecimal rate) throws SQLException {
        List<DiscountCode> discountCodes = new ArrayList<>();
        final String sqlText = "SELECT * FROM discount_code WHERE rate = ?";
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            pstmt.setBigDecimal(1, rate);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    discountCodes.add(fabric.createEntity(rs));
                }
            }
        }
        return discountCodes;
    }
    
}
