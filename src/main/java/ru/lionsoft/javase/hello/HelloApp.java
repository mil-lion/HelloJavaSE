/*
 * File:    HelloApp.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 18:36:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

/**
 * Первый класс на языке Java
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloApp {
    
    /**
     * Главный метод для запуска приложения из командной строки
     * @param args аргументы программы из командной строки
     */
    public static void main(String[] args) {
        // Согласно ООП обязательно создаем экземпляр класса и уже работаем с ним
        HelloApp app = new HelloApp();
        
        app.условныеКонструкции();
        
        app.testBox();
        
        System.out.println("-8 = " + NumberUtils.intToBinaryString(-8));
        System.out.println("-8 >>> 2 = " + (-8>>>2) + " = " + NumberUtils.intToBinaryString(-8>>>2));
        
        int i = 123_456_789;
        System.out.println("i = " + String.format("%08x", i));
        i = Integer.parseInt("075bcd15", 16);
        System.out.println("i = " + i);
        System.out.println("0x075bcd15 -> " + "0x075bcd15".substring(2));
        i = Integer.parseUnsignedInt("0xff80ff40".substring(2), 16);
        System.out.println("i = " + i);
        
        // digital
        System.out.println("\nDigital: 123456");
        app.printIntegerValue(123456);
        // octal
        System.out.println("\nOctal: 0123456");
        app.printIntegerValue(0123456);
        // hex
        System.out.println("\nHex: 0x123456");
        app.printIntegerValue(0x123456);
        // binary
        System.out.println("\nBinary: 0b1011001");
        app.printIntegerValue(0b1011001);
    }

    /**
     * Метод для тестирования класса Box
     */
    public void testBox() {
        System.out.println("#### test Box:");
        Box<Short> b1 = new Box<>();
        printBox("b1", b1);
        Box<Integer> b2 = new Box<>(1, 2, 3);
        printBox("b2", b2);
        Box<Long> b3 = new Box<>(4L, 5L, 6L);
        printBox("b3", b3);
        // Using Factoty
        Box b4 = Box.createStandardSizeBox(Box.TYPE_SIZE_LARGE);
        printBox("b4", b4);
        // Using Builder
        BoxBuilder<Double> boxBuilder = new BoxBuilder<>();
        Box b5 = boxBuilder.setWidth(1.5).setHeight(2.4).setLength(3.6).createBox();
        printBox("b5", b5);
    }

    /**
     * Метод распечатки на экран информации о коробке
     * @param name имя переменной
     * @param box ссылка на коробку
     */
    public void printBox(String name, Box box) {
        System.out.println(name + " = " + box);
        System.out.println(name + ".perimeter     = " + box.getPerimeter());
        System.out.println(name + ".squareSurface = " + box.getSquareSurface());
        System.out.println(name + ".volume        = " + box.getVolume());
    }

    /**
     * Примеры условных конструкций if..then..else, switch..case..default
     */
    public void условныеКонструкции() {
        System.out.println("#### Конструкция if:"); 
        int num1 = 6;
        int num2 = 4;
        if (num1 > num2) {
            System.out.println("Первое число больше второго");
        }

        System.out.println("#### Конструкция if/else:");
        num1 = 6;
        num2 = 4;
        if (num1 > num2) {
            System.out.println("Первое число больше второго");
        } else {
            System.out.println("Первое число меньше или равно второму");
        }

        System.out.println("#### Конструкция if/else if:");
        num1 = 6;
        num2 = 8;
        if (num1 > num2) {
            System.out.println("Первое число больше второго");
        } else if (num1 < num2) {
            System.out.println("Первое число меньше второго");
        } else {
            System.out.println("Числа равны");
        }
        
        System.out.println("#### Конструкция if с несколькими условиями:");
        num1 = 8;
        num2 = 6;
        if (num1 > num2 && num1 > 7) {
            System.out.println("Первое число больше второго и больше 7");
        }

        System.out.println("#### Конструкция switch:");
        int num = 8;
        switch (num) {
            case 1: 
                System.out.println("число равно 1");
                break;
            case 8: 
                System.out.println("число равно 8");
                num++;
                break;
            case 9: 
                System.out.println("число равно 9");
                break;
            default:
                System.out.println("число не равно 1, 8, 9");
        }
        
        System.out.println("#### Конструкция switch с несколькими case:");
        num = 3;
        int output;
        switch (num) {
            case 1: 
                output = 3;
                break;
            case 2: 
            case 3: 
            case 4: 
                output = 6;
                break;
            case 5: 
                output = 12;
                break;
            default:
                output = 24;
        }
        System.out.println(output);
        
        // for JDK12 (preview)
//        System.out.println("#### Конструкция switch с несколькими case (JDK12):");
//        num = 2;
//        output = switch (num) {
//            case 1:       break 3;
//            case 2, 3, 4: break num % 2 == 0 ? 6 : 7;
//            case 5:       break 12;
//            default:      break 24;
//        };
//        System.out.println(output);
//
//        System.out.println("#### Конструкция switch с несколькими case (JDK12 lambda):");
//        num = 5;
//        output = switch (num) {
//            case 1       -> 3;
//            case 2, 3, 4 -> {
//                int odd = num % 2;
//                break odd == 0 ? 6 : 7;
//            }
//            case 5       -> 12;
//            default      -> 24;
//        };
//        System.out.println(output);

        System.out.println("#### Тернарная операция:");
        int x = 3;
        int y = 2;
        int z = x < y ? (x + y) : (x - y);
        System.out.println(z);

    }
    
    /**
     * Печать целого числа в десятиричном, восьмиричной, шестнадцатиричнойм и двоичном форматах
     * @param x чило
     */
    public void printIntegerValue(int x) {
        // Digital
        System.out.println("dig(x) = " + x);
        // Octal
        System.out.println("oct(x) = 0" + Integer.toOctalString(x));
        // Hex
        System.out.println("hex(x) = 0x" + Integer.toHexString(x));
        // Binary
        System.out.println("bin(x) = 0b" + Integer.toBinaryString(x));
    }
}
