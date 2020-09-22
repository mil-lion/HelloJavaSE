/*
 * File:    CustomerFacade.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 22:25:46
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.entities.Customer;

/**
 * Фасад для сущности Клиент
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class CustomerFacade extends AbstractFacade<Customer, Integer> {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(CustomerFacade.class.getName());
    
    /**
     * Конструктор фасада для сущности
     * @param connection соединение с СУБД
     */
    public CustomerFacade(Connection connection) {
        super(Customer.class, connection);
    }
    
    /**
     * Выбрать клиентов по имени
     * @param name имя клмента
     * @return список клиентов
     * @throws SQLException ошибка SQL
     */
    public List<Customer> findByName(String name) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        final String sqlText = "SELECT * FROM customer WHERE name LIKE ?";
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    customers.add(factory.createEntity(rs));
                }
            }
        }
        return customers;
    }
}
