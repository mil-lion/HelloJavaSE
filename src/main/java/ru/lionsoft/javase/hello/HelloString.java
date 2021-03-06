/*
 * File:    HelloString.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:19:39 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Примеры работы со строками
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
//@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class HelloString {

    private static final Logger LOG = Logger.getLogger(HelloString.class.getName());
    
    public static void main(String[] args) {
        HelloString app = new HelloString();
        
        String str1 = "Hello World!";
        String str2 = new String("Hello World!");
        String str3 = "Hello World!";
        
        System.out.println("str1 == str2 -> " + (str1 == str2));
        System.out.println("str1 == str3 -> " + (str1 == str3));
        System.out.println("str1 == HELLO -> " + (str1 == HelloApp.HELLO));
        System.out.println("str1.intern() == str2.intern() -> " + (str1.intern() == str2.intern()));
        System.out.println("str1.equals(str2) -> " + str1.equals(str2));
        System.out.println("str1.equalsIgnoreCase(str2) -> " + str1.equalsIgnoreCase(str2.toLowerCase()));
        System.out.println("str1.compareTo(str2) -> " + str1.compareTo(str2));
        System.out.println("str1.compareToIgnoreCase(str2) -> " + str1.compareToIgnoreCase(str2.toLowerCase()));
        
        int x = 123456;
        System.out.println("x = " + String.format("%09d", x));
        System.out.println("x = " + intToString1(x, 9));
        System.out.println("x = " + intToString2(x, 9));
        System.out.println("x = " + intToString3(x, 9));
        System.out.println("x = " + intToString4(x, 9));
        
//        app.testPlus();
//        app.testConcat();
//        app.testStringBuilder();
//        app.testStringBuffer();
        app.runBenchmark();
        
        String text = "\tПривет строка\n\rиз Java Standard Edition!\n";
        app.printByWord1(text);
        app.printByWord2(text);
        app.printByWord3(text);
        app.printByWord4(text);
        
    }
    
    /**
     * Функция преобразования числа к строке с ведущими нулями (Array)
     * @param x число для преобразования
     * @param n количество знаков числа
     * @return строковое представление числа с ведущими нулями
     */
    public static  String intToString1(int x, int n) {
        char[] buffer = new char[n];
        for (n--; n >= 0; n--, x /= 10) {
            buffer[n] = (char) ('0' + x % 10);
        }
        return new String(buffer);
    }

    /**
     * Функция преобразования числа к строке с ведущими нулями (String.getChars)
     * @param x число для преобразования
     * @param n количество знаков числа
     * @return строковое представление числа с ведущими нулями
     */
    public static  String intToString2(int x, int n) {
        char[] buffer = new char[n];
        Arrays.fill(buffer, '0');
        String val = String.valueOf(x);
        val.getChars(0, val.length(), buffer, n - val.length());
        return new String(buffer);
    }
    
    /**
     * Функция преобразования числа к строке с ведущими нулями (StringBuilder)
     * @param x число для преобразования
     * @param n количество знаков числа
     * @return строковое представление числа с ведущими нулями
     */
    public static  String intToString3(int x, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++, x /= 10) {
            sb.append((char)('0' + x % 10));
        }
        return sb.reverse().toString();
    }
    
    /**
     * Функция преобразования числа к строке с ведущими нулями (SubString)
     * @param x число для преобразования
     * @param n количество знаков числа
     * @return строковое представление числа с ведущими нулями
     */
    public static  String intToString4(int x, int n) {
        StringBuilder sb = new StringBuilder("000000000000000000000");
        return sb.append(x).delete(0, sb.length() - n).toString();
    }
    
    private static final String TEST_STRING = 
            " 1234567890-=!#$%^&*()_+[]{},./<>?|"
            + "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "абвгдеёжзийклмнопрстуфхцчшщьыъэюя"
            + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ";
    private static final int TEST_COUNT = 1000;
    private static final long MEGA = 1024 * 1024;
    
    /**
     * Печать информации о памяти JVM
     */
    private void printMemoryInfo() {
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory() / MEGA;
        long totalMemory = rt.totalMemory() / MEGA;
        long freeMemory = rt.freeMemory() / MEGA;
        long usageMemory = (rt.totalMemory() - rt.freeMemory()) / MEGA;
        System.out.printf("memoty info: %dM:%dM:%dM:%dM\n", maxMemory, totalMemory, usageMemory, freeMemory);
    }
    
    /**
     * Тест сложения строк
     */
    @Benchmark
    public void testPlus() {
        System.out.println("### Test Plus");
        printMemoryInfo();
        long startTime = System.currentTimeMillis();
        String str = TEST_STRING;
        for (int i = 0; i < TEST_COUNT; i++) {
            str += TEST_STRING;
        }
        System.out.println("str.length = " + str.length());
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration = " + duration + "ms");
        printMemoryInfo();
    }

    /**
     * Тест функции String.concat
     */
    @Benchmark
    public void testConcat() {
        System.out.println("### Test Concat");
        printMemoryInfo();
        long startTime = System.currentTimeMillis();
        String str = TEST_STRING;
        for (int i = 0; i < TEST_COUNT; i++) {
            str = str.concat(TEST_STRING);
        }
        System.out.println("str.length = " + str.length());
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration = " + duration + "ms");
        printMemoryInfo();
    }

    /**
     * Тест использования StringBuilder
     */
    @Benchmark
    public void testStringBuilder() {
        System.out.println("### Test StringBuilder");
        printMemoryInfo();
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(TEST_STRING);
        for (int i = 0; i < TEST_COUNT; i++) {
            sb.append(TEST_STRING);
        }
        String str = sb.toString();
        System.out.println("str.length = " + str.length());
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration = " + duration + "ms");
        printMemoryInfo();
    }

    /**
     * Тест использования StringBuilder
     */
    @Benchmark
    public void testStringBuffer() {
        System.out.println("### Test StringBuffer");
        printMemoryInfo();
        long startTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer(TEST_STRING);
        for (int i = 0; i < TEST_COUNT; i++) {
            sb.append(TEST_STRING);
        }
        String str = sb.toString();
        System.out.println("str.length = " + str.length());
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration = " + duration + "ms");
        printMemoryInfo();
    }

    /**
     * Запуск замера производительности методов (JMH)
     */
    private void runBenchmark() {
        Options options = new OptionsBuilder()
                .include(HelloString.class.getSimpleName())
                .forks(1)
                .build();
        try {
            new Runner(options).run();
        } catch (RunnerException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Печать слов из текста на каждой строке (String.split)
     * @param text исходный текст
     */
    public void printByWord1(String text) {
        System.out.println("### printByWord - way #1 (String.split)");
        int words = 0;
        for (String word : text.split("[\\s\\.\\,\\-\\[\\]{}\'\"<>!@#\\$\\^%&*_+=]")) {
            if (!word.isEmpty()) {
                words++;
                System.out.println(word);
            }
        }
        System.out.println("words = " + words);
    } 

    /** Разделители слов */
    private static final String DELIMITERS = " \t\n\r\'\".,?;:|\\[]{}()!@#$%^&*-_+=<>";
    
    /**
     * Печать слов из текста на каждой строке (StringTokenizer)
     * @param text исходный текст
     */
    public void printByWord2(String text) {
        System.out.println("### printByWord - way #2 (StringTokenizer)");
        int words = 0;
        for (StringTokenizer tokenizer = new StringTokenizer(text, DELIMITERS); tokenizer.hasMoreTokens(); ) {
            words++;
            String word = tokenizer.nextToken();
            System.out.println(word);
        }
        System.out.println("words = " + words);
    } 

    /**
     * Печать слов из текста на каждой строке (for by symbol)
     * @param text исходный текст
     */
    public void printByWord3(String text) {
        System.out.println("### printByWord - way #3 (for by symbol)");
        int words = 0;
        int beginWord = 0, endWord = 0;
        for (int i = 0; i < text.length(); i++) {
            if (DELIMITERS.indexOf(text.charAt(i)) >= 0) {
                // delimiter
                if (beginWord == endWord) {
                    // skip
                    beginWord++;
                    endWord++;
                } else {
                    // endWord
                    words++;
                    String word = text.substring(beginWord, endWord);
                    System.out.println(word);
                    beginWord = endWord =  i + 1;
                }
            } else {
                // symbol
                endWord++;
            }
        }
        if (beginWord != endWord) {
            // last word
            words++;
            String word = text.substring(beginWord, endWord);
            System.out.println(word);
        }
        System.out.println("words = " + words);
    } 

    /**
     * Печать слов из текста на каждой строке (String.replaceAll)
     * @param text исходный текст
     */
    public void printByWord4(String text) {
        System.out.println("### printByWord - way #4 (String.replaceAll)");
        text = text
                .replaceAll("[\\s\\.\\,\\-\\[\\]{}\'\"<>!@#\\$\\^%&*_+=]+", "\n")
                .replaceAll("^\n|\n$", "");
        System.out.println(text);
        int words = text.replaceAll("\\S+", "").length() + 1;
        System.out.println("words = " + words);
    } 
}
