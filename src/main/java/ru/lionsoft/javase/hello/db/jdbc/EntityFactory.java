/*
 * File:    EntityFactory.java
 * Project: HelloJavaSE
 * Date:    12 сент. 2019 г. 23:03:58
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Завод по созданию сущностей для базы данных
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class EntityFactory {
    
    private EntityFactory() {
    }
    
    public static EntityFactory getInstance() {
        return EntityFactoryHolder.INSTANCE;
    }
    
    private static class EntityFactoryHolder {

        private static final EntityFactory INSTANCE = new EntityFactory();
    }
    
    public static <T> T createEntity(ResultSet rs, Class<T> entityClass) {
        
        return (T)Proxy.newProxyInstance(
                entityClass.getClassLoader(), // class loader
                new Class<?>[] {entityClass}, // interfaces
                new EntityHandler(rs));       // handler
    }
    
    private static class EntityHandler implements InvocationHandler {

        private final Map<String, Object> columns = new HashMap<>();
        
        public EntityHandler(ResultSet rs) {
            // extract columns value
            try {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.put(metaData.getCatalogName(i).toUpperCase(), rs.getObject(i));
                }
            } catch (SQLException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxy for: " + proxy.getClass().getSimpleName());
            String methodName = method.getName();
            System.out.println("entity call method: " + methodName);
            if (methodName.equals("toString")) {
                return columns.toString();
            } else if (methodName.startsWith("get")) {
                String columnName = convertColumnName(methodName.substring(3));
                return columns.get(columnName);
            } else if (methodName.startsWith("set")) {
                String columnName = convertColumnName(methodName.substring(3));
                columns.put(columnName, args[0]);
            }
            return null;
        }
        
        private String convertColumnName(String name) {
            StringBuilder sb = new StringBuilder(name);
            for (int i = 1; i < sb.length(); i++) {
                if (Character.isUpperCase(sb.charAt(i))) {
                    sb.insert(i, '_');
                    i++;
                }
            }
            return sb.toString().toUpperCase();
        }
        
    }
            
}
