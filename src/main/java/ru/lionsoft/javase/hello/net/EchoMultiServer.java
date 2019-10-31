/*
 * File:    EchoMultiServer.java
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
 * Пример многоопользовательского эхо-сервера
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class EchoMultiServer extends Thread {

    // Журнал
    private static final Logger LOG = Logger.getLogger(EchoMultiServer.class.getName());
    
    private final static int SERVER_PORT = 12345; 

    /**
     * Главный метод программы
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        
        int port = SERVER_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        
        try {
            ServerSocket listener = new ServerSocket(port);
            System.out.println("Server started on port: " + port);
            while (true) {
                // Accept connection
                Socket clientSocket = listener.accept();
                
                // Create new Thread for client
                Thread thread = new EchoMultiServer(clientSocket);
                thread.start();

                System.out.println("Count: " + count);
            }
//            System.out.println("Server shutdown!");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    // Count of Threads (shared resource!)
    private static volatile int count = 0;
        
    // Client Socket
    private final Socket client;

    /**
     * Конструктор
     * @param clientSocket сетевое соединение клиента 
     */
    public EchoMultiServer(Socket clientSocket) {
        this.client = clientSocket;
    }

    // Основной метод потока
    @Override
    public void run() {
        synchronized (getClass()) {
            count++;
        }
        try {
            String clientInfo = client.getInetAddress() + ":" + client.getPort();
            System.out.println("Connection from client: " + clientInfo);
            // Get Input/Output Stream from Socket
            try (
                    BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter wr = new PrintWriter(client.getOutputStream(), true);
                ) {
                String request;
                // Get request
                while ((request = rd.readLine()) != null) {
                    System.out.println(clientInfo + " < " + request);
                    // Special command!
                    if (request.equalsIgnoreCase("Bye!")) {
                        // Bye command!
                        wr.println("Ok. Goodby!");
                        break;
                    }
                    if (request.equalsIgnoreCase("Exit!!!")) {
                        // Shutdown Server command!
                        wr.println("Ok. Goodby!");
                        System.out.println("Server Shutdown!");
                        System.exit(0); // Exit from program
                    }
                    // Put answer
                    String answer = request.toUpperCase();
                    wr.println(answer);
                    System.out.println(clientInfo + " > " + answer);
                }
            }
            System.out.println("Disconnected client: " + clientInfo);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        synchronized (getClass()) {
            count--;
        }
    }    
}
