/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lionsoft.javase.hello.db.jdbc;

import java.sql.*;
import ru.lionsoft.javase.hello.db.jdbc.entities.Customer;
import ru.lionsoft.javase.hello.db.jdbc.entities.MicroMarket;
import ru.lionsoft.javase.hello.db.jdbc.facades.CustomerFacade;
import ru.lionsoft.javase.hello.db.jdbc.facades.DiscountCodeFacade;
import ru.lionsoft.javase.hello.db.jdbc.facades.MicroMarketFacade;

/**
 *
 * @author Igor Morenko (emailto:imorenko@yandex.ru)
 */
public class HelloFacade {

    // Apache Derby
    public static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    public static final String DERBY_URL = "jdbc:derby://localhost:1527/sample";
    public static final String DERBY_USERNAME = "app";
    public static final String DERBY_PASSWORD = "app";
    
    public static void main(String[] args) {
        try {
            // 1. Register JDBC Database Driver
            Class.forName(DERBY_DRIVER_CLASS);
            
            // 2. Connect to Database
            try (Connection connection = DriverManager.getConnection(DERBY_URL, DERBY_USERNAME, DERBY_PASSWORD);) {
                printConnectionInfo(connection);
            
                CustomerFacade customerFacade = new CustomerFacade(connection);
                DiscountCodeFacade discountCodeFacade = new DiscountCodeFacade(connection);
                MicroMarketFacade microMarketFacade = new MicroMarketFacade(connection);
                
                System.out.println("Customer count = " + customerFacade.count());
                System.out.println("DiscountCode count = " + discountCodeFacade.count());
                System.out.println("MicroMarket count = " + microMarketFacade.count());
                
                System.out.println("\nCustomers: ");
                customerFacade.findAll().forEach(System.out::println);
                System.out.println("\nCustomer #1: " + customerFacade.find(1));
                System.out.println("\nCustomers Name = %Comp% : ");
                customerFacade.findByName("%Comp%").forEach(System.out::println);
                
                System.out.println("\nDiscount Codes: ");
                discountCodeFacade.findAll().forEach(System.out::println);
                System.out.println("\nDiscountCode #H: " + discountCodeFacade.find("H"));
                
                System.out.println("\nMicro Markets: ");
                microMarketFacade.findAll().forEach(System.out::println);
                System.out.println("\nMicroMarket #95117: " + microMarketFacade.find("95117"));
                
                // DML
                int customerId = genId();
                Customer newCustomer = new Customer(customerId);
                newCustomer.discountCode = "H";
                newCustomer.zip = "95117";
                newCustomer.name = "XXX";
                
                customerFacade.create(newCustomer);
                System.out.println("Customer created.");
                
                newCustomer.name = "XXX Update";
                newCustomer.email = "xxx@mail.ru";
                customerFacade.update(newCustomer);
                System.out.println("Customer updated.");
                
                customerFacade.delete(newCustomer);
                System.out.println("Customer deleted.");
                
                connection.commit();
            }
            // Auto Disconnect from Database
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
    private static void printConnectionInfo(final Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println("Database: " + metaData.getDatabaseProductName() 
                + " v." + metaData.getDatabaseProductVersion());
        System.out.println("Driver:   " + metaData.getDriverName() 
                + " v." + metaData.getDriverVersion());
    }

    private static int genId() {
        return (int) (System.nanoTime() & 0x7fffffff);
    }

}
