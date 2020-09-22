/*
 * File:    RepositoryFactory.java
 * Project: HelloJavaSE
 * Date:    24 авг. 2020 г. 13:06:09
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 *
 * @author Igor Morenko
 */
public class RepositoryFactory {
    
    private RepositoryFactory() {
        
    }
    
    public static <R> R getRepository(Class<R> repositoryClass, Connection connection) {
        Class entityClass = repositoryClass.getTypeParameters()[0].getClass();
//        Class idClass;
        return (R) Proxy.newProxyInstance(
                repositoryClass.getClassLoader(), 
                new Class[] {repositoryClass}, 
                new RepositoryHandler(entityClass, connection));
    }
    
    private static class RepositoryHandler implements InvocationHandler {
        
        private final Class entityClass;
        private final Connection connection;

        public RepositoryHandler(Class entityClass, Connection connection) {
            this.entityClass = entityClass;
            this.connection = connection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
                
    }
}
