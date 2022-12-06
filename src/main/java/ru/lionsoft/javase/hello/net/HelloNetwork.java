/*
 * File:    HelloNetwork.java
 * Project: HelloJavaSE
 * Date:    31 окт. 2019 г. 21:22:40
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import ru.lionsoft.javase.hello.db.jpa.entities.Customer;

/**
 * Пример работы по сети
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloNetwork {

    private static final Logger LOG = Logger.getLogger(HelloNetwork.class.getName());
    
    public static void main(String[] args) {
        
        HelloNetwork app = new HelloNetwork();
        
        app.testUrl();
        app.testSocket();
    }

    public void testUrl() {
        System.out.println("#### test URL ####");
        try {
            URL url = new URL("https://www.yandex.ru/");
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));) {
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println("> " + line);
                }
            }
            // AutoClose Reader
//            rd.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    public void testSocket() {
        try {
            System.out.println("#### test Soscket ####");
            // Connect
            try (Socket client = new Socket("levsha.lionsoft.ru", 80);) { // 212.12.17.62
                System.out.println("LocalPort: " + client.getLocalPort());
                // Get Input/Output Stream from Socket
                try (
                        BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter wr = new PrintWriter(client.getOutputStream(), true);
                    ) 
                {
                    // Put Requet
                    wr.println("GET /index.html");
                    // Get Response (Answer)
                    String line;
                    while ((line = rd.readLine()) != null) {
                        System.out.println("> " + line);
                    }
                }
                // AutoClose Reader & Writer
            }
            // AutoClose connection
//            client.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Пример использования встроенного HTTP клиента
     * @param customer ссылка на сушность
     */
    public void updateCustomer(Customer customer) { 
        try { 
            URL url = new URL("http://www.example.com/customers"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            connection.setDoOutput(true); 
            connection.setInstanceFollowRedirects(false); 
            connection.setRequestMethod("PUT"); 
            connection.setRequestProperty("Content-Type", "application/xml"); 

            OutputStream out = connection.getOutputStream(); 
            JAXBContext.newInstance(Customer.class).createMarshaller().marshal(customer, out); 
            out.flush(); 

            connection.getResponseCode(); 
            connection.disconnect(); 
        } catch (IOException | JAXBException e) { 
            LOG.log(Level.SEVERE, null, ex);
        } 
    }
}
