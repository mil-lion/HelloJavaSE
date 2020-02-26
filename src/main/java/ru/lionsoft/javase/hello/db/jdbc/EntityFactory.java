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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Завод (singletone) по созданию сущностей для базы данных
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class EntityFactory {

    /** Журнал */
    private static final Logger LOG = Logger.getLogger(EntityFactory.class.getName());
    
    private EntityFactory() {
    }
    
    public static EntityFactory getInstance() {
        return EntityFactoryHolder.INSTANCE;
    }
    
    private static class EntityFactoryHolder {

        private static final EntityFactory INSTANCE = new EntityFactory();
    }
    
    /**
     * Создает классы сущностей (прокси) по выборке из БД
     * @param <T> тип сущности
     * @param rs выборка из БД
     * @param entityClass интерфейс сущности
     * @return ссылка на прокси-обект для сущности Т
     */
    public static <T> T createEntity(ResultSet rs, Class<T> entityClass) {
        
        return (T)Proxy.newProxyInstance(
                entityClass.getClassLoader(), // class loader
                new Class<?>[] {entityClass}, // interfaces
                new EntityHandler(rs));       // handler
    }
    
    /**
     * Класс обработчик методов для проксируемых интерфейсов
     */
    private static class EntityHandler implements InvocationHandler {

        // Хранилище для полей сущности
        private final Map<String, Object> columns = new HashMap<>();
        
        public EntityHandler(ResultSet rs) {
            // extract columns value to map
            try {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.put(metaData.getColumnName(i).toUpperCase(), rs.getObject(i));
                }
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }

        // Обработчик методов для проксируемых сущностей
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("proxy for: " + proxy.getClass().getSimpleName());
            String methodName = method.getName();
//            System.out.println("entity call method: " + methodName);
            if (methodName.equals("toString")) {
                return columns.toString();
            } else if (methodName.startsWith("get")) {
                String columnName = propertyToColumnName(methodName);
                return columns.get(columnName);
            } else if (methodName.startsWith("set")) {
                String columnName = propertyToColumnName(methodName);
                columns.put(columnName, args[0]);
            }
            return null;
        }
        
        private String propertyToColumnName(String propertyName) {
            StringBuilder sb = new StringBuilder(propertyName);
            sb.delete(0, 3); // delete set/get
            for (int i = 1; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.insert(i++, '_');
                } else {
                    sb.setCharAt(i, c);
                }
            }
            return sb.toString();
        }
        
    }
            
}
