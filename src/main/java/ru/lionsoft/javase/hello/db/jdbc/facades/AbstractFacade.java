/*
 * File:    AbstractFacade.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 22:05:30
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.orm.EntityFactory;

/**
 * Абстрактный (универсальный) фасад для сущностией СУБД
 * @author Igor Morenko <morenko at lionsoft.ru>
 * @param <E> тип сущности
 * @param <PK> тип первичного ключа сущности
 */
public abstract class AbstractFacade<E, PK> {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(AbstractFacade.class.getName());
    
    /** Класс сущности */
    protected final Class entityClass;
    
    /** Cоединение с СУБД */
    protected final Connection connection;
    
    /** Фабрика создания сущности */
    protected final EntityFactory<E> factory;

    /**
     * Конструктор фасада
     * @param entityClass класс сущности
     * @param connection соединение с СУБДы
     */
    public AbstractFacade(Class entityClass, Connection connection) {
        this.entityClass = entityClass;
        this.connection = connection;
        factory = EntityFactory.newInstance(entityClass);
    }

    /**
     * Выбрать сущность по первичному ключу
     * @param key значение первичного ключа
     * @return выбранная сущность, {@code null} если сущность не найдена
     * @throws SQLException ошибка SQL
     */
    public E find(PK key) throws SQLException {
        E entity = null;
        final String sqlText = factory.getSqlFindById();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            pstmt.setObject(1, key);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    entity = factory.createEntity(rs);
                }
            }
        }
        return entity;
    }
    
    /**
     * Выбрать все сущности
     * @return выбранные сущности
     * @throws SQLException ошибка SQL
     */
    public List<E> findAll() throws SQLException {
        List<E> entities = new ArrayList<>();
        final String sqlText = factory.getSqlFindAll();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (Statement stmt = connection.createStatement();) {
            try (ResultSet rs = stmt.executeQuery(sqlText);) {
                while (rs.next()) {
                    entities.add(factory.createEntity(rs));
                }
            }
        }
        return entities;
    }
    
    /**
     * Вставить сущность в СУБД
     * @param entity сущность
     * @return кол-во вставленных записей
     * @throws SQLException ошибка SQL
     */
    public int create(E entity) throws SQLException {
        final String sqlText = factory.getSqlInsert();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            factory.setParameterForInsert(pstmt, entity);
            return pstmt.executeUpdate();
        }
    }
    
    /**
     * Обновить сущность в СУБД
     * @param entity сущность
     * @return кол-во обновленных записей
     * @throws SQLException ошибка SQL
     */
    public int update(E entity) throws SQLException {
        final String sqlText = factory.getSqlUpdate();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            factory.setParameterForUpdate(pstmt, entity);
            return pstmt.executeUpdate();
        }
    }
    
    /**
     * Удалить сущность из СУБД
     * @param entity сущность
     * @return кол-во удаленных записей
     * @throws SQLException ошибка SQL
     */
    public int delete(E entity) throws SQLException {
        final String sqlText = factory.getSqlDelete();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlText);) {
            factory.setParameterForDelete(pstmt, entity);
            return pstmt.executeUpdate();
        }
    }
    
    /**
     * Выбрать количесвто сущность в СУБД
     * @return кол-во сущностей в СУБД
     * @throws SQLException ошибка SQL
     */
    public long count() throws SQLException {
        final String sqlText = "SELECT count(*) FROM " + factory.getTableName();
        LOG.log(Level.INFO, "sql: {0}", sqlText);
        long count = 0;
        try (Statement stmt = connection.createStatement();) {
            try (ResultSet rs = stmt.executeQuery(sqlText);) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        }
        return count;
    }
}
