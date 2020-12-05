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
    
    public static final String HELLO  = "Hello World!";
    
    /**
     * Главный метод для запуска приложения из командной строки
     * @param args аргументы программы из командной строки
     */
    public static void main(String[] args) {
        // Согласно ООП обязательно создаем экземпляр класса и уже работаем с ним
        HelloApp app = new HelloApp();
        
        app.условныеКонструкции();
        app.циклы();
        app.testVarArgs();
        app.testBox();
        app.testBoxGeneric();

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

    private void циклы() {
        System.out.println("#### конструкция while");
        int i = 0;
        while (i < 5) System.out.println(i++);

        i = 5;
        while (i > 0) {
            System.out.println(i);
            i--;
        }

        System.out.println("#### конструкция do-while");
        i = 0;
        do {
            System.out.println(i++);
        } while (i < 5);

        System.out.println("#### конструкция for");
        for (i = 0; i < 5; i++)
            System.out.println(i);

        for (int x = 0, y = 5; x < 5; x++, y -= 2)
            System.out.println("x = " + x + ", y = " + y);

        System.out.println("#### конструкция for-each");
        int[] array = { 1, 2, 3, 4, 5 };
        for (int x : array) {
            System.out.println(x);
        }

        System.out.println("#### конструкция break-continue");
        yearLoop: for (int year = 2010; year <= 2021; year++) {
            monthLoop: for (int month = 1; month <= 12; month++) {
                System.out.println(year + "." + month);
//                if (year < 2020) break;
                if (year < 2020) continue yearLoop;
                if (year == 2020 && month == 3) break yearLoop;
            }
        }
    }

    /**
     * Метод для тестирования методов с переменным кол-вом аргументов
     */
    private void testVarArgs() {
        System.out.println("#### test VarArgs:");
        vaTest(10);
        vaTest(1, 2, 3);
        vaTest(true, false, true);
//        app.vaTest(); // Ошибка: vaTest(int...) или vaTest(boolean...)
//        app.vaTest(null); // Ошибка: vaTest(int...) или vaTest(boolean...)
        vaTest("Test", 4, 3, 6);
        vaTest("Test2");
        vaTest(null, null);
    }

    /**
     * Метод для тестирования класса Box
     */
    public void testBox() {
        System.out.println("#### test Box:");
        
        Box b1 = new Box();
        printBox("b1", b1);
        
        Box b2 = new Box(1, 2, 3);
        printBox("b2", b2);
        
        Box b3 = new Box(10);
        printBox("b3", b3);
        
        Box b4 = new Box(5, 10);
        printBox("b4", b4);
        
        Box b5 = new Box(Box.TypeSize.Medium);
        printBox("b5", b5);
        
        // Using Factoty
        Box b6 = BoxFactory.createStandardBox(Box.TYPE_SIZE_LARGE);
        printBox("b6", b6);
        
        // Using Builder
        BoxBuilder builder = new BoxBuilder();
        Box b7 = builder
                .setWidth(2)
                .setHeight(3)
                .setLength(5)
                .createBox();
        printBox("b7", b7);
    }

    /**
     * Метод для тестирования класса BoxGeneric
     */
    public void testBoxGeneric() {
        System.out.println("#### test BoxGeneric:");

        BoxGeneric<Short> b1 = new BoxGeneric<>();
        printBox("b1", b1);

        BoxGeneric<Integer> b2 = new BoxGeneric<>(1, 2, 3);
        printBox("b2", b2);

        BoxGeneric<Long> b3 = new BoxGeneric<>(4L, 5L, 6L);
        printBox("b3", b3);

//        BoxGeneric<String> b4 = new BoxGeneric<>("ширина", "высота", "длина"); // error
//        printBox("b4 ", b4);
    }

    /**
     * Метод распечатки на экран информации о коробке
     * @param name имя переменной
     * @param box ссылка на коробку
     */
    public void printBox(String name, Box box) {
        System.out.println(name + " = " + box);
        System.out.println(name + ".perimeter     = " + box.getPerimeter() + " m");
        System.out.println(name + ".squareSurface = " + box.getSquareSurface() + " m\u00b2");
        System.out.println(name + ".volume        = " + box.getVolume() + " m\u00b3");
    }

    /**
     * Метод распечатки на экран информации о коробке
     * @param name имя переменной
     * @param box ссылка на коробку
     */
    public void printBox(String name, BoxGeneric box) {
        System.out.println(name + " = " + box);
        System.out.println(name + ".perimeter     = " + box.getPerimeter() + " m");
        System.out.println(name + ".squareSurface = " + box.getSquareSurface() + " m\u00b2");
        System.out.println(name + ".volume        = " + box.getVolume() + " m\u00b3");
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
        
        // for JDK12+ (--enable-preview), JDK14+        
        System.out.println("#### Конструкция rule switch (JDK12+ lambda):");
        switch (num) {
            case 1 -> System.out.println("число равно 1");
            case 8 -> { 
                System.out.println("число равно 8");
                num++;
            }
            case 9 -> System.out.println("число равно 9");
            default -> System.out.println("число не равно 1, 8, 9");
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
        
        // for JDK12 (--enable-preview)
//        System.out.println("#### Конструкция switch с несколькими case (JDK12):");
//        num = 2;
//        output = switch (num) {
//            case 1:       break 3;
//            case 2, 3, 4: break num % 2 == 0 ? 6 : 7;
//            case 5:       break 12;
//            default:      break 24;
//        };
//        System.out.println(output);

        // for JDK12+ (--enable-preview), JDK14+
        System.out.println("#### Конструкция switch expression с несколькими case (JDK12+ lambda):");
        num = 5;
        output = switch (num) {
            case 1       -> 3;
            case 2, 3, 4 -> num % 2 == 0 ? 6 : 7;
            case 5       -> 12;
            default      -> 24;
        };
        System.out.println(output);

        System.out.println("#### Тернарная операция:");
        int x = 3;
        int y = 2;
        int z = x < y ? (x + y) : (x - y);
        System.out.println(z);

        System.out.println("#### Примеры switch:");
        int month = (int) (Math.random() * 12 + 1);
        System.out.println("daysOfMonth1(" + month + ") = " + daysOfMonth1(month));
        System.out.println("daysOfMonth2(" + month + ") = " + daysOfMonth2(month));

        System.out.println("daysOfMonth1(2, 2019) = " + daysOfMonth1(2, 2019));
        System.out.println("daysOfMonth1(2, 2020) = " + daysOfMonth1(2, 2020));
        System.out.println("daysOfMonth1(2, 2021) = " + daysOfMonth1(2, 2021));
        System.out.println("daysOfMonth2(2, 2019) = " + daysOfMonth2(2, 2019));
        System.out.println("daysOfMonth2(2, 2020) = " + daysOfMonth2(2, 2020));
        System.out.println("daysOfMonth2(2, 2021) = " + daysOfMonth2(2, 2021));
        
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
    
    /**
     * Пример метода с переменным числом параметров
     * @param v массив параметров
     */
    public void vaTest(int... v) {
        System.out.println("ru.lionsoft.javase.hello.HelloApp.vaTest()");
        System.out.println("Number of args: " + v.length);
        System.out.println("Contents:");
        for (int x : v) {
            System.out.println(x);
        }
    }

    public void vaTest(boolean... v) {
        System.out.println("ru.lionsoft.javase.hello.HelloApp.vaTest()");
        System.out.println("Number of args: " + v.length);
        System.out.println("Contents:");
        for (boolean x : v) {
            System.out.println(x);
        }
    }

    public void vaTest(String msg, int... v) {
        System.out.println("ru.lionsoft.javase.hello.HelloApp.vaTest()");
        System.out.println("msg: " + msg);
        if (v == null) {
            System.out.println("v: null");
            return;
        }
        System.out.println("Number of args: " + v.length);
        System.out.print("Contents: ");
        for (int x : v) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
    
    /**
     * Функция получения количества дней в месяце
     * @param month номер месяца
     * @return количество дней в месяце
     */
    public static int daysOfMonth1(int month) {
        switch(month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 0;
        }
    }

    /**
     * Функция получения количества дней в месяце
     * @param month номер месяца
     * @return количество дней в месяце
     * @since JDK14
     */
    public static int daysOfMonth2(int month) {
        // use switch expression
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 0;
        };
    }

    /**
     * Функция получения количества дней в месяце
     * @param month номер месяца
     * @param year год
     * @return количество дней в месяце
     */
    public static int daysOfMonth1(int month, int year) {
        switch(month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
//                if (year % 4 == 0)
//                    return 29;
//                else
//                    return 28;
                return year % 4 == 0 ? 29 : 28;
            default:
                return 0;
        }
    }

    /**
     * Функция получения количества дней в месяце
     * @param month номер месяца
     * @param year год
     * @return количество дней в месяце
     * @since JDK14
     */
    public static int daysOfMonth2(int month, int year) {
        // use switch expression
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
//            case 2 -> year % 4 == 0 ? 29 : 28;
            case 2 ->  {
                if (year % 4 == 0)
                    yield 29;
                else
                    yield 28;
            }
            default -> 0;
        };
    }

}
