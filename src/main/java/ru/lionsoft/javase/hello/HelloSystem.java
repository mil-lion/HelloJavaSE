/*
 * File:    HelloSystem.java
 * Project: HelloJavaSE
 * Date:    18 нояб. 2019 г. 22:38:46
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Примеры по работы с системными классами
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloSystem {

    private static final Logger LOG = Logger.getLogger(HelloSystem.class.getName());
        
    static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            System.out.println("Constructor Point.init()");
            this.x = x;
            this.y = y;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("Distructor Point.finalize()");
        }
        
        
    }
    
    public static void main(String[] args) {
        
        // *************** Standart I/O *****************
        System.out.println("Hello, Output!"); // stdout
        System.err.println("Hello, Error!");  // stderr
        // System.in - stdin
        
        // *************** System Properties ************
        System.out.println("user.name = " + System.getProperty("user.name"));
        System.out.println("java.version = " + Integer.getInteger("java.version"));
        System.out.println("myprop = " + System.getProperty("myprop", "Unknown"));
        System.out.println("mybool = " + Boolean.getBoolean("mybool"));
        System.out.println("### System Properties:");
        System.getProperties().forEach((p, v) -> System.out.println(p + "=" + v));

        // *************** System Enviroment ************
        System.out.println("set JAVA_HOME=" + System.getenv("JAVA_HOME"));
        System.out.println("### System Enviroment:");
        System.getenv().forEach((p, v) -> System.out.println(p + "=" + v));

        // *************** Runtime *******************
        Point p1 = new Point(1, 2);
        Runtime r = Runtime.getRuntime();
        System.out.println("free memory: " + r.freeMemory());
        p1 = null;
        r.gc(); // or System.gc()
        r.runFinalization(); // or System.runFinalization()

        // *************** Execute External Program ***********
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                r.exec("cmd.exe /c dir > c:\\temp\\dir.out");
                r.exec("cmd.exe /c set > c:\\temp\\set1.out");
            } else {
                r.exec("ls > ~/tmp/ls.out");
                r.exec("env > ~/tmp/env1.out");
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        // *************** Process Builder ***********
        ProcessBuilder pb;
        if (System.getProperty("os.name").startsWith("Windows")) {
            pb = new ProcessBuilder("cmd.exe", "/c", "set");
            pb.redirectOutput(new File("c:/temp/set3.out"));
        } else {
            pb = new ProcessBuilder("env");
            pb.redirectOutput(new File("~/tmp/env3.out"));
        }
        pb.environment().add("MyEnv1", "MyValue1");
        pb.environment().add("MyEnv2", "MyValue2");
        
        // *************** Exit from Program ***********
        System.exit(0);  // Successfull (or r.exit(0))
//        System.exit(-1); // Failure
    }
    
}
