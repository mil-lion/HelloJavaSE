/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lionsoft.javase.hello.db.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Id;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Table;

/**
 *
 * @author Igor Morenko (emailto:imorenko@yandex.ru)
 */
public class EntityFactory<E> {
        
    private static final Map<Class, EntityFactory> cacheEntityFactories = new HashMap<>();
    
    public static <E> EntityFactory<E> getEntityFactory(Class<E> entityClass) {
        if (!cacheEntityFactories.containsKey(entityClass)) {
            cacheEntityFactories.put(entityClass, new EntityFactory<>(entityClass));
        }
        return cacheEntityFactories.get(entityClass);
    }
    
    // ********************  *************
    
    private final Class<E> entityClass;
    
    private String tableName;
    private String idColumnName;
    private final Map<String, Field> columnMap = new TreeMap<>();
    
    private EntityFactory(Class<E> entityClass) {
        this.entityClass = entityClass;
        analyzeEntityClass();
    }
    
    private void analyzeEntityClass() {
        System.out.println("Analyze Entity Class: " + entityClass.getName());
        
        tableName = entityClass.getSimpleName().toUpperCase();
        // check annotation Table
        Table annotationTable = entityClass.getAnnotation(Table.class);
        if (annotationTable != null) {
            tableName = annotationTable.name();
        }
        // analyze fields
        for (Field field : entityClass.getFields()) {
            final String fieldName = field.getName();
            String columnName = fieldName.toUpperCase();
            // check annotation Column
            Column annotationColumn = field.getAnnotation(Column.class);
            if (annotationColumn != null) {
                if (!annotationColumn.name().isEmpty()) {
                    columnName = annotationColumn.name();
                }
                // add to map
                columnMap.put(columnName, field);
            }
            // check annotation id
            Id annotationId = field.getAnnotation(Id.class);
            if (annotationId != null) {
                idColumnName = columnName;
                if (!columnMap.containsKey(columnName)) {
                    columnMap.put(columnName, field);
                }
            }
        }
        // for debug
        System.out.println("tableName: " + tableName);
        System.out.println("idColumnName: " + idColumnName);
        System.out.println("columnMap:");
        columnMap.entrySet().forEach((e) -> System.out.println(" - " + e.getKey() + " => " + e.getValue().getName()));
    }

    public E createEntity(ResultSet rs) throws SQLException {
        try {
            // new Instance
            final E entity = entityClass.getConstructor().newInstance();
            // set Fields
            for (var entry : columnMap.entrySet()) {
                final String columnName = entry.getKey();
                final Field field = entry.getValue();
                try {
                    field.set(entity, rs.getObject(columnName));
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    System.err.println("Error setField: " + ex.getMessage());
                }
            }
            return entity;
        } catch (IllegalAccessException | IllegalArgumentException 
                | InstantiationException | InvocationTargetException 
                | NoSuchMethodException | SecurityException ex) {
            System.err.println("Error createEntity: " + ex.getMessage());
        }
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }
    
    public String getSqlTextFindAll() {
        return new StringBuilder("SELECT * FROM ")
                .append(tableName)
                .toString();
    }

    public String getSqlTextCount() {
        return new StringBuilder("SELECT COUNT(*) FROM ")
                .append(tableName)
                .toString();
    }

    public String getSqlTextFindById() {
        return new StringBuilder("SELECT * FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(idColumnName)
                .append("=?")
                .toString();
    }

    public String getSqlTextDelete() {
        return new StringBuilder("DELETE FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(idColumnName)
                .append("=?")
                .toString();
    }

    public String getSqlTextInsert() {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append('(');
        int i = 0;
        for (String columnName : columnMap.keySet()) {
            if (i++ > 0) sb.append(',');
            sb.append(columnName);
        }
        sb.append(") VALUES (");
        for (i = 0; i < columnMap.size(); i++) {
            sb.append(i > 0 ? ",?" : "?");
        }
        return sb.append(')').toString();
    }

    public String getSqlTextUpdate() {
        StringBuilder sb = new StringBuilder("UPDATE ")
                .append(tableName)
                .append(" SET ");
        int i = 0;
        for (String columnName : columnMap.keySet()) {
            if (columnName.equalsIgnoreCase(idColumnName)) continue; // skip id
            if (i++ > 0) sb.append(',');
            sb.append(columnName).append("=?");
        }
        return sb.append(" WHERE ")
                .append(idColumnName)
                .append("=?")
                .toString();
    }
    
    public void setColumnValueForInsert(PreparedStatement pstmt, E entity) throws SQLException {
        int col = 1;
        for (Map.Entry<String, Field> entry : columnMap.entrySet()) {
            Field field = entry.getValue();
            try {
                pstmt.setObject(col++, field.get(entity));
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                System.err.println("Error setColumnValueForInsert: " + ex.getMessage());
            }
        }
    }

    public void setColumnValueForUpdate(PreparedStatement pstmt, E entity) throws SQLException {
        int col = 1;
        for (Map.Entry<String, Field> entry : columnMap.entrySet()) {
            final String columnName = entry.getKey();
            Field field = entry.getValue();
            
            if (columnName.equalsIgnoreCase(idColumnName)) continue; // skip id
            
            try {
                pstmt.setObject(col++, field.get(entity));
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                System.err.println("Error setColumnValueForInsert: " + ex.getMessage());
            }
        }
        // set id
        Field idField = columnMap.get(idColumnName);
        try {
            pstmt.setObject(col++, idField.get(entity));
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            System.err.println("Error setColumnValueForInsert: " + ex.getMessage());
        }
    }
}