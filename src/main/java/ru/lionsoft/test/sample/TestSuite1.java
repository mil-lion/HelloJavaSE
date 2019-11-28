/*
 * File:    TestSuite1.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2019 г. 22:15:43
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.test.sample;

import ru.lionsoft.test.Context;
import ru.lionsoft.test.annotations.AfterTestCase;
import ru.lionsoft.test.annotations.AfterTestSuite;
import ru.lionsoft.test.annotations.BeforeTestCase;
import ru.lionsoft.test.annotations.BeforeTestSuite;
import ru.lionsoft.test.annotations.TestCase;
import ru.lionsoft.test.annotations.TestContext;
import ru.lionsoft.test.annotations.TestSuite;

/**
 * Пример тестового набора
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@TestSuite
public class TestSuite1 {

    @TestContext
    private Context testContext;

    @BeforeTestSuite
    public void setUpClass() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.setUpClass()");
        System.out.println("testContext = " + testContext);
    }
    
    @AfterTestSuite
    public void tearDownClass() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.tearDownClass()");
    }
    
    @BeforeTestCase
    public void setUp() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.setUp()");
    }
    
    @AfterTestCase
    public void tearDown() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.tearDown()");
    }
    
    @TestCase(order = 4)
    public void testCase1() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.testCase1()");
    }

    @TestCase(order = 3)
    public void testCase2(@TestContext Context ctx) {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.testCase2()");
        System.out.println("ctx = " + ctx);
    }

    @TestCase(order = 2, ignore = true)
    public void testCase3() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.testCase3()");
    }

    @TestCase(order = 1)
    public void testCase4() {
        System.out.println("ru.lionsoft.test.sample.TestSuite1.testCase4()");
    }
}
