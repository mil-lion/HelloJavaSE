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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloDatabase {

    private static final Logger LOG = Logger.getLogger(HelloDatabase.class.getName());
    
    // Apache Derby
    private static final String DERBY_JDBC_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    private static final String DERBY_JDBC_URL = "jdbc:derby://localhost:1527/sample";
    private static final String DERBY_JDBC_USERNAME = "app";
    private static final String DERBY_JDBC_PASSWORD = "app";
    // Oracle
    private static final String ORACLE_JDBC_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static final String ORACLE_JDBC_URL_THIN = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String ORACLE_JDBC_URL_OCI  = "jdbc:oracle:oci:@orcl";
    private static final String ORACLE_JDBC_URL_KPRB = "jdbc:oracle:kprb:";
    private static final String ORACLE_JDBC_USERNAME = "hr";
    private static final String ORACLE_JDBC_PASSWORD = "hr";
    // PostgreSQL
    private static final String PGSQL_JDBC_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String PGSQL_JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String PGSQL_JDBC_USERNAME = "postgres";
    private static final String PGSQL_JDBC_PASSWORD = "postgres";
    
    public static void main(String[] args) {
        HelloDatabase app = new HelloDatabase();

        // Derby
        try (Connection connection = app.connectToDatabase(DERBY_JDBC_DRIVER_CLASS, DERBY_JDBC_URL, DERBY_JDBC_USERNAME, DERBY_JDBC_PASSWORD);) {
            app.printDatabaseInfo(connection);
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.SEVERE, "Apache Derby error", ex);
        }
        // Auto Disconnect from Database - connection.close()

        // Oracle
        try (Connection connection = app.connectToDatabase(ORACLE_JDBC_DRIVER_CLASS, ORACLE_JDBC_URL_THIN, ORACLE_JDBC_USERNAME, ORACLE_JDBC_PASSWORD);) {
            app.printDatabaseInfo(connection);
            
        } catch (ClassNotFoundException | SQLException ex) {
            LOG.log(Level.SEVERE, "Oracle error", ex);
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
    private Connection connectToDatabase(String driverClass, String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Печать информации о СУБД и драйва
     * @param connection соединение с СУБД
     * @throws SQLException ошибка SQL
     */
    private void printDatabaseInfo(Connection connection) throws SQLException {
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println(dbMetaData.getDatabaseProductName() + " " + dbMetaData.getDatabaseProductVersion());
        System.out.println(dbMetaData.getDriverName() + " " + dbMetaData.getDriverVersion());
    }
    
}
