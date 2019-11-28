/*
 * File:    TestRun.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2019 г. 22:06:41
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.lionsoft.test.annotations.AfterTestCase;
import ru.lionsoft.test.annotations.AfterTestSuite;
import ru.lionsoft.test.annotations.BeforeTestCase;
import ru.lionsoft.test.annotations.BeforeTestSuite;
import ru.lionsoft.test.annotations.TestCase;
import ru.lionsoft.test.annotations.TestContext;
import ru.lionsoft.test.annotations.TestSuite;

/**
 * Класс для запуска тестов
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class TestRun {
    
    private static final Context ctx = new Context();
    
    /**
     * Основная программа приложения
     * @param args аргументы командной строки
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        List<String> testSuites = new ArrayList<>();
        if (args.length > 0) {
            testSuites.addAll(Arrays.asList(args));
        } else {
            testSuites.add("ru.lionsoft.test.sample.TestSuite1");
        }
        
        // обрабатываем наборы тестов
        for (String testSuiteClass : testSuites) {
            // Создаем класс для тестового набора
            Object testSuite = Class.forName(testSuiteClass) // загружаем класс по имени
                    .getConstructor() // получить контруктор по умолчанию
                    .newInstance(); // создаем экземпляр класса

            // Имя набора тестов
            String testSuiteName = testSuite.getClass().getSimpleName();
            TestSuite testSuiteAnnotation = testSuite.getClass().getAnnotation(TestSuite.class);
            if (testSuiteAnnotation != null && !testSuiteAnnotation.name().isEmpty()) {
                testSuiteName = testSuiteAnnotation.name();
            }
            System.out.println("Run Test Suite: " + testSuiteName);
            
            // Методы для набора тестов
            Method beforeTestSuite = null;
            Method afterTestSuite = null;
            Method beforeTestCase = null;
            Method afterTestCase = null;
            List<Method> testCases = new ArrayList<>();
            
            // Изучаем класс
            // - поля
            for (Field filed : testSuite.getClass().getDeclaredFields()) {
                if (filed.getAnnotation(TestContext.class) != null) {
                    filed.setAccessible(true);
                    filed.set(testSuite, ctx);
                }
            }
            // - методы
            for (Method method : testSuite.getClass().getMethods()) {
                for (Annotation methodAnnotation : method.getAnnotations()) {
                    Class methodAnnotationClass = methodAnnotation.annotationType();
                    if (methodAnnotationClass == BeforeTestSuite.class) {
                        beforeTestSuite = method;
                    } else if (methodAnnotationClass == AfterTestSuite.class) {
                        afterTestSuite = method;
                    } else if (methodAnnotationClass == BeforeTestCase.class) {
                        beforeTestCase = method;
                    } else if (methodAnnotationClass == AfterTestCase.class) {
                        afterTestCase = method;
                    } else if (methodAnnotationClass == TestCase.class) {
                        testCases.add(method);
                    } 
                }
            }
            
            // Сортируем тесты по порядку (order)
            testCases.sort((Method m1, Method m2) -> {
                int order1 = m1.getAnnotation(TestCase.class).order();
                int order2 = m2.getAnnotation(TestCase.class).order();
                return Integer.compare(order1, order2);
            });
            
            // Вполняем методы
            invokeMethod(beforeTestSuite, testSuite);
            for (Method testCaseMethod : testCases) {
                if (testCaseMethod.getAnnotation(TestCase.class).ignore()) {
                    System.out.println("Test " + testCaseMethod.getName() + " skiped!");
                    continue;
                }
                
                invokeMethod(beforeTestCase, testSuite);
                invokeMethod(testCaseMethod, testSuite);
                invokeMethod(afterTestCase, testSuite);
            }
            invokeMethod(afterTestSuite, testSuite);
        }
    }

    private static void invokeMethod(Method method, Object obj) {
        if (method == null) return;
        
        try {
            if (method.getParameterCount() == 0) {
                method.invoke(obj);
            } else if (method.getParameterCount() == 1) {
                for (Annotation annotation : method.getParameterAnnotations()[0]) {
                    if (annotation instanceof TestContext) {
                        method.invoke(obj, ctx);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
