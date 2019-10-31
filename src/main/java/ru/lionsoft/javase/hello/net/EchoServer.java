/*
 * File:    EchoServer.java
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Пример однопользовательского эхо-сервера
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class EchoServer {

    private static final Logger LOG = Logger.getLogger(EchoServer.class.getName());
    
    private final static int SERVER_PORT = 12345; 

    public static void main(String[] args) {
        
        try {
            int port = SERVER_PORT;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            
            ServerSocket listener = new ServerSocket(port);
            System.out.println("Server started on port: " + port);
            // Start work!
            acceptLoop: while (true) {
                // Accept connect from client
                Socket client = listener.accept();
                
                String clientInfo = client.getInetAddress() + ":" + client.getPort();
                System.out.println("Connection from: " + clientInfo);
                // Get Input/Output Stream from client Socket
                try (
                        BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter wr = new PrintWriter(client.getOutputStream(), true);
                    ) {
                    String request;
                    // Get request
                    dialogLoop: while ((request = rd.readLine()) != null) {
                        System.out.println("< " + request);
                        // Check on key word: Bye! and Exit!!!
                        if (request.equalsIgnoreCase("Bye!")) {
                            // Close connection
                            wr.println("Ok. Goodby!");
                            break; // dialogLoop
                        }
                        if (request.equalsIgnoreCase("Exit!!!")) {
                            // Shutdown Server
                            wr.println("Ok. Goodby!");
                            break acceptLoop;
                        }
                        // Put answer
                        String answer = request.toUpperCase();
                        wr.println(answer);
                        System.out.println("> " + answer);
                    }
                    System.out.println("Disconnected client: " + clientInfo);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Server shutdown!");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
        
}
