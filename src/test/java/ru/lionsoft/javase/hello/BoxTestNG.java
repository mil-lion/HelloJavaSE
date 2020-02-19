/*
 * File:    BoxTest.java
 * Project: HelloJavaSE
 * Date:    29 окт. 2019 г. 22:25:29
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.awt.Color;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Тестирование класса Box с помощью фреймворка TestNG
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class BoxTestNG {
    
    public BoxTestNG() {
    }
    
    /**
     * Метод выполныется перед созданием экземпляра класса
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * Метод выполныется после уничтожения экземпляра класса
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Метод выполныется перед каждым запуском метода помеченного @Test
     */
    @BeforeMethod
    public void setUp() {
    }
    
    /**
     * Метод выполныется после каждого выполнениия метода помеченного @Test
     */
    @AfterMethod
    public void tearDown() {
    }

    /**
     * Test of getColor method, of class Box.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Box instance = new Box();
        Color expResult = null;
        Color result = instance.getColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColor method, of class Box.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        Color color = null;
        Box instance = new Box();
        instance.setColor(color);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class Box.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Box instance = new Box(2, 3, 4);
        Number expResult = 2;
        Number result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWidth method, of class Box.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        Number width = 2;
        Box instance = new Box(1, 3, 4);
        instance.setWidth(width);
        assertEquals(width, instance.getWidth());
    }

    /**
     * Test of getHeight method, of class Box.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Box instance = new Box();
        Number expResult = null;
        Number result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class Box.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        Number height = null;
        Box instance = new Box();
        instance.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class Box.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        Box instance = new Box();
        Number expResult = null;
        Number result = instance.getLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLength method, of class Box.
     */
    @Test
    public void testSetLength() {
        System.out.println("setLength");
        Number length = null;
        Box instance = new Box();
        instance.setLength(length);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerimeter method, of class Box.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        Box instance = new Box();
        double expResult = 0.0;
        double result = instance.getPerimeter();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSquareSurface method, of class Box.
     */
    @Test
    public void testGetSquareSurface() {
        System.out.println("getSquareSurface");
        Box instance = new Box();
        double expResult = 0.0;
        double result = instance.getSquareSurface();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVolume method, of class Box.
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        Box instance = new Box(2, 3, 4);
        double expResult = 24.0;
        double result = instance.getVolume();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of perimeter method, of class Box.
     */
    @Test
    public void testPerimeter() {
        System.out.println("perimeter");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = Box.perimeter(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of squareSurface method, of class Box.
     */
    @Test
    public void testSquareSurface() {
        System.out.println("squareSurface");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = Box.squareSurface(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of volume method, of class Box.
     */
    @Test
    public void testVolume() {
        System.out.println("volume");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = Box.volume(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compare method, of class Box.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Box b1 = null;
        Box b2 = null;
        int expResult = 0;
        int result = Box.compare(b1, b2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of open method, of class Box.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        Box instance = new Box();
        instance.open();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Box.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        Box instance = new Box();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBox method, of class Box.
     */
    @Test
    public void testCreateBox() {
        System.out.println("createBox");
        Number width = null;
        Number height = null;
        Number length = null;
        Box expResult = null;
        Box result = Box.createBox(width, height, length);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createStandardSizeBox method, of class Box.
     */
    @Test
    public void testCreateStandardSizeBox() {
        System.out.println("createStandardSizeBox");
        char type = ' ';
        Box expResult = null;
        Box result = Box.createStandardSizeBox(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Box.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Box instance = new Box();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Box.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Box instance = new Box();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Box.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Box o = null;
        Box instance = new Box();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Box.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Box instance = new Box();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
