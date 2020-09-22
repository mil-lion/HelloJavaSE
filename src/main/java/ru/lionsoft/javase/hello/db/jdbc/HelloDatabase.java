/*
 * File:    HelloDatabase.java
 * Project: HelloJavaSE
 * Date:    11 сент. 2019 г. 21:22:40
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.lionsoft.javase.hello.db.jdbc.entities.Customer;
import ru.lionsoft.javase.hello.db.jdbc.entities.DiscountCode;
import ru.lionsoft.javase.hello.db.jdbc.entities.MicroMarket;
import ru.lionsoft.javase.hello.db.jdbc.facades.CustomerFacade;
import ru.lionsoft.javase.hello.db.jdbc.facades.DiscountCodeFacade;
import ru.lionsoft.javase.hello.db.jdbc.facades.MicroMarketFacade;

/**
 * Пример работы с СУБД с использованием JDBC
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloDatabase {

    private static final Logger LOG = Logger.getLogger(HelloDatabase.class.getName());
    
    // Apache Derby
    static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    static final String DERBY_URL = "jdbc:derby://localhost:1527/sample";
    static final String DERBY_USERNAME = "app";
    static final String DERBY_PASSWORD = "app";
    // Oracle
    static final String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    static final String ORACLE_URL_THIN = "jdbc:oracle:thin:@localhost:1521:orcl";
    static final String ORACLE_URL_OCI  = "jdbc:oracle:oci:@orcl";
    static final String ORACLE_URL_KPRB = "jdbc:oracle:kprb:";
    static final String ORACLE_USERNAME = "hr";
    static final String ORACLE_PASSWORD = "hr";
    // PostgreSQL
    static final String PGSQL_DRIVER_CLASS = "org.postgresql.Driver";
    static final String PGSQL_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String PGSQL_USERNAME = "postgres";
    static final String PGSQL_PASSWORD = "P@ssw0rd";
    
    public static void main(String[] args) {
        HelloDatabase app = new HelloDatabase();

        // Derby
        try (Connection connection = 
                app.connectToDatabase(DERBY_DRIVER_CLASS, DERBY_URL, DERBY_USERNAME, DERBY_PASSWORD);) {
            app.printConnectionInfo(connection);
            
            app.testFacades(connection);
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.SEVERE, "Apache Derby error", ex);
        }
        // Auto Disconnect from Database - connection.close()

        // Oracle
//        try (Connection connection = 
//                app.connectToDatabase(ORACLE_DRIVER_CLASS, ORACLE_URL_THIN, ORACLE_USERNAME, ORACLE_PASSWORD);) {
//            app.printConnectionInfo(connection);
//            
//        } catch (ClassNotFoundException | SQLException ex) {
//            LOG.log(Level.SEVERE, "Oracle error", ex);
//        }
        // Auto Disconnect from Database
        
        // PostgreSQL
        try (Connection connection = 
                app.connectToDatabase(PGSQL_DRIVER_CLASS, PGSQL_URL, PGSQL_USERNAME, PGSQL_PASSWORD);) {
            app.printConnectionInfo(connection);
            
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.SEVERE, "PostgreSQL error", ex);
        }
        // Auto Disconnect from Database
        
    }

    /**
     * Подключение к СУБД
     * @param driverClass имя класса драйвера JDBC
     * @param url строка JDBC для соединения с СУБД
     * @param user имя пользователя СУБД
     * @param password парольпользователя СУБД
     * @return соединение с СУБД
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private Connection connectToDatabase(String driverClass, String url, String user, String password) 
            throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Печать информации о СУБД и драйвере
     * @param connection соединение с СУБД
     * @throws SQLException ошибка SQL
     */
    private void printConnectionInfo(Connection connection) throws SQLException {
        System.out.println("Connection Info:");
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("  Database: " + dbMetaData.getDatabaseProductName() 
                + " v." + dbMetaData.getDatabaseProductVersion());
        System.out.println("  JDBC Driver: " + dbMetaData.getDriverName() 
                + " v." + dbMetaData.getDriverVersion());
    }

    /**
     * Работа с СУБД через фасады и фабрику сущностей
     * @param connection соедиение с СУБД
     * @throws SQLException ошибка SQL
     */
    private void testFacades(Connection connection) throws SQLException {
        // Facades
        CustomerFacade customerFacade = new CustomerFacade(connection);
        DiscountCodeFacade discountCodeFacade = new DiscountCodeFacade(connection);
        MicroMarketFacade microMarketFacade = new MicroMarketFacade(connection);
        
        // select Customer
        Customer customer = customerFacade.find(1);
        System.out.println("customer: " + customer);
        
        List<Customer> customers = customerFacade.findByName("XXX%");
        System.out.println("Customers:");
        customers.forEach(System.out::println);
        
        System.out.println("customer.count: " + customerFacade.count());
        
        // select DiscountCode
        DiscountCode discountCode = discountCodeFacade.find("H");
        System.out.println("discountCode: " + discountCode);

        System.out.println("discountCode.count: " + discountCodeFacade.count());
        
        // select MicroMarket
        MicroMarket microMarket = microMarketFacade.find("94401");
        System.out.println("microMarket: " + microMarket);

        System.out.println("microMarket.count: " + microMarketFacade.count());
        
        // DML
        Random r = new Random();
        
        // insert
        int id = genId();
        Customer newCustomer = new Customer(id);
        newCustomer.discountCode = "H";
        newCustomer.zip = "94401";
        newCustomer.name = "XXX" + r.nextInt(9);
        
        customerFacade.create(newCustomer);
        connection.commit();
        
        // update
        newCustomer.email = newCustomer.name.toLowerCase() + "@mail.ru";
        
        customerFacade.update(newCustomer);
        connection.commit();
        
        // delete
        customerFacade.delete(newCustomer);
        connection.commit();
    }
    
    private int genId() {
        return (int)System.nanoTime() & 0x7fffffff;
    }
}
