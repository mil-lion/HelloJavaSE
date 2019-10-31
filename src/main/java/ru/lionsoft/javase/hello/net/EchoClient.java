/*
 * File:    EchoClient.java
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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс сетевой клиент для эхо-сервера
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class EchoClient {

    private static final Logger LOG = Logger.getLogger(EchoClient.class.getName());

    private static final String SERVER_ADDRESS = "localhost"; 
    private static final int SERVER_PORT = 12345; 
    
    private static final int DIALOG_COUNT = 10;
    
    public static void main(String[] args) {
        
        String[] requests = {
            "Hello Server!", 
            "Ia am Client!",
            "How are you?", 
            "What is you name?", 
            "All right!"
//            , "Bye!"
//            , "Exit!!!"
        };
        
        String serverAddress = args.length > 0 ? args[0] : SERVER_ADDRESS;
        int serverPort = args.length > 1 ? Integer.parseInt(args[1]) : SERVER_PORT;
        
        try {
            // Connect to server
            System.out.printf("Connect to server: %s:%d\n", serverAddress, serverPort);
            try (Socket client = new Socket(serverAddress, serverPort);) {
                // Print client info
                System.out.println("LocalAddress: " + client.getLocalAddress());
                System.out.println("LocalPort: " + client.getLocalPort());
                // Get Input/Output Stream from Socket
                try (
                        BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter wr = new PrintWriter(client.getOutputStream(), true);
                    ) {

                    for (int i = 0; i < DIALOG_COUNT; i++) {
                        // Dialog with server
                        for (String request : requests) {
                            // Put Requet
                            System.out.println("> " + request);
                            wr.println(request);
                            
                            // Get Response (Answer)
                            String answer = rd.readLine();
                            System.out.println("< " + answer);
                            
                            // sleep
                            Thread.sleep(1000);
                        }
                    }
                }
                // AutoClose Reader & Writer
            }
            // AutoClose connection
//            client.close();
        } catch (IOException | InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
    }
}
