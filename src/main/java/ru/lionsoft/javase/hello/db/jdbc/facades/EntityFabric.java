/*
 * File:    EntityFabric.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:47:40
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.facades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.annotation.PrimaryKey;
import ru.lionsoft.javase.hello.db.jdbc.annotation.Table;

/**
 * Фабрика создания сущностей
 * @author Igor Morenko <morenko at lionsoft.ru>
 * @param <E> тип сущности
 */
public class EntityFabric<E> {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(EntityFabric.class.getName());
    
    /**
     * Кэш экземпляров фабрик для сущностей 
     * (чтобы исключить повторное изучение классов)
     */
    private static final Map<Class, EntityFabric> INSTANCES = new HashMap<>();
    
    /**
     * Фабричный метод создания экземпляра фабрики для сущности
     * @param <E> тип сущности
     * @param entityClass класс сущности
     * @return экземпляр фабрики сущности
     */
    public static <E> EntityFabric<E> newInstance(Class<E> entityClass) {
        EntityFabric fabric = INSTANCES.get(entityClass);
        if (fabric == null) {
            fabric = new EntityFabric<>(entityClass);
            INSTANCES.put(entityClass, fabric);
        }
        return fabric;
    }

    // ********************* Fields ***********************
    
    /**
     * Класс сущности
     */
    private final Class<E> entityClass;
    
    /**
     * Название таблицы СУБД для сущности
     */
    private final String tableName;
    
    /**
     * Название колонки с первичным ключом
     */
    private String primaryKey;
    
    /**
     * Мапинг колонок таблицы с полями класса сущностиы
     */
    private final Map<String, Field> columns = new TreeMap<>();

    // ******************* Constructors **********************
    
    /**
     * Конструктор фабрики сущности
     * @param entityClass класс сущности
     */
    public EntityFabric(Class<E> entityClass) {
        // entity class
        this.entityClass = entityClass;
        
        // table name
        Table aTable = entityClass.getAnnotation(Table.class);
        tableName = aTable != null ? aTable.name() : entityClass.getSimpleName().toUpperCase();
        
        // column mapping
        for (Field field : entityClass.getDeclaredFields()) {
            final String fieldName = field.getName();
            
            if (fieldName.equals("serialVersionUID")) continue; // skip
            
            // column name
            String columnName = fieldName.toUpperCase();
            Column aColumn = field.getAnnotation(Column.class);
            if (aColumn != null) {
                columnName = aColumn.name();
            }
            LOG.log(Level.INFO, "column map: {0} -> {1}", new String[]{columnName, fieldName});
            
            // add map
            columns.put(columnName, field);
            
            // primary key
            if (field.getAnnotation(PrimaryKey.class) != null) {
                primaryKey = columnName;
            }
        }
        LOG.log(Level.INFO, 
                "Create fabric for Entity: {0}\n"
                + "  tableName: {1}\n"
                + "  primaryKeyColumn: {2}\n"
                + "  columns: {3}",
                new Object[]{entityClass.getName(), tableName, primaryKey, columns.keySet()});
    }
    
    // ****************** Methods **********************

    /**
     * Получить имя таблицы для сущности
     * @return имя таблицы
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Получить имя колонки табоицы первичного ключа сущности
     * @return имя колонки первичного ключа
     */
    public String getPrimaryKeyColumnName() {
        return primaryKey;
    }
    
    /**
     * Создать сущность по результирующему набору из СУБД
     * @param rs результирующий набор из СУБД
     * @return созданная сущность
     */
    public E createEntity(ResultSet rs) {
        
        E entity = null;
        try {
            // create new instance
            entity = entityClass.getConstructor().newInstance();

            // set fields
            for (var entry : columns.entrySet()) {
                final String columnName = entry.getKey();
                final Field field = entry.getValue();
                try {
                    field.setAccessible(true);
                    field.set(entity, rs.getObject(columnName));
                } catch (IllegalAccessException | IllegalArgumentException | SQLException ex) {
                    LOG.log(Level.SEVERE, "Set Entity Field", ex);
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException 
                | InvocationTargetException | NoSuchMethodException |SecurityException ex) {
            LOG.log(Level.SEVERE, "Create Entity", ex);
        }
        
        return entity;
    }
    
    /**
     * Сформировать SQL запроса данных из таблицы СУБД для сущности по первичному ключу
     * @return SQL текст для выборки данных из таблицы СУБД
     */
    public String getSqlSelect() {
        return  new StringBuilder("SELECT * FROM ")
                    .append(tableName)
                    .append(" WHERE ")
                    .append(primaryKey)
                    .append("=?")
                    .toString();        
    }
    
    /**
     * Сформировать SQL вставки в таблицу СУБД для сущности
     * @return SQL текст для вставки данных в таблицу СУБД
     */
    public String getSqlInsert() {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(tableName).append(" (");
        int i = 0;
        for (String columnName : columns.keySet()) {
            if (i++ > 0) sb.append(',');
            sb.append(columnName);
        }
        sb.append(") VALUES (");
        for (i = 0; i < columns.size(); i++) {
            if (i > 0)
                sb.append(",?");
            else
                sb.append('?');
        }
        return sb.append(")").toString();
    }

    /**
     * Сформировать SQL обновления данных в таблице СУБД для сущности
     * @return SQL текст для обнавления данных в таблице СУБД
     */
    public String getSqlUpdate() {
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(tableName).append(" SET ");
        int i = 0;
        for (String columnName : columns.keySet()) {
            if (i++ > 0) sb.append(',');
            sb.append(columnName).append("=?");
        }
        return  sb.append(" WHERE ")
                    .append(primaryKey)
                    .append("=?")
                    .toString();        
    }
    
    /**
     * Сформировать SQL удаление данных из таблицы СУБД для сущности
     * @return SQL текст для удаление данных из таблицы СУБД
     */
    public String getSqlDelete() {
        return  new StringBuilder("DELETE FROM ")
                    .append(tableName)
                    .append(" WHERE ")
                    .append(primaryKey)
                    .append("=?")
                    .toString();        
    }
    
    /**
     * Установить параметры SQL запроса на вставку данных в таблицу СУБД
     * @param pstmt ссылка на параметризованный курсор
     * @param entity сущность
     * @throws SQLException ошибка SQL
     */
    public void setParameterForInsert(PreparedStatement pstmt, E entity) throws SQLException {
        int i = 1;
        for (var entry : columns.entrySet()) {
            final Field field = entry.getValue();
            field.setAccessible(true);
            try {
                pstmt.setObject(i++, field.get(entity));
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                LOG.log(Level.SEVERE, "Set Parameter for Prepared Statement INSERT", ex);
            }
        }
    }

    /**
     * Установить параметры SQL запроса на обновление данных в таблице СУБД
     * @param pstmt ссылка на параметризованный курсор
     * @param entity сущность
     * @throws SQLException ошибка SQL
     */
    public void setParameterForUpdate(PreparedStatement pstmt, E entity) throws SQLException {
        int i = 1;
        for (var entry : columns.entrySet()) {
            final Field field = entry.getValue();
            field.setAccessible(true);
            try {
                pstmt.setObject(i++, field.get(entity));
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                LOG.log(Level.SEVERE, "Set Parameter for Prepared Statement UPDATE", ex);
            }
        }
        Field primaryKeyFiled = columns.get(primaryKey);
        try {
            pstmt.setObject(i++, primaryKeyFiled.get(entity));
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, "Set Parameter for Prepared Statement UPDATE", ex);
        }
    }
    
    /**
     * Установить параметры SQL запроса на удаление данных из таблицы СУБД
     * @param pstmt ссылка на параметризованный курсор
     * @param entity сущность
     * @throws SQLException ошибка SQL
     */
    public void setParameterForDelete(PreparedStatement pstmt, E entity) throws SQLException {
        Field primaryKeyFiled = columns.get(primaryKey);
        try {
            pstmt.setObject(1, primaryKeyFiled.get(entity));
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, "Set Parameter for Prepared Statement DELETE", ex);
        }
    }
}
