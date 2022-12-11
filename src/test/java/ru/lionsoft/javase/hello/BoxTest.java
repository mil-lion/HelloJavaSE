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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Тестирование класса BoxGeneric с помощью фреймворка JUnit
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class BoxTest {
    
    public BoxTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getColor method, of class BoxGeneric.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        BoxGeneric instance = new BoxGeneric();
        Color expResult = null;
        Color result = instance.getColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColor method, of class BoxGeneric.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        Color color = null;
        BoxGeneric instance = new BoxGeneric();
        instance.setColor(color);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class BoxGeneric.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        BoxGeneric instance = new BoxGeneric(2, 3, 4);
        Number expResult = 2;
        Number result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWidth method, of class BoxGeneric.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        Number width = 2;
        BoxGeneric instance = new BoxGeneric(1, 3, 4);
        instance.setWidth(width);
        assertEquals(width, instance.getWidth());
    }

    /**
     * Test of getHeight method, of class BoxGeneric.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        BoxGeneric instance = new BoxGeneric();
        Number expResult = null;
        Number result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class BoxGeneric.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        Number height = null;
        BoxGeneric instance = new BoxGeneric();
        instance.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class BoxGeneric.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        BoxGeneric instance = new BoxGeneric();
        Number expResult = null;
        Number result = instance.getLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLength method, of class BoxGeneric.
     */
    @Test
    public void testSetLength() {
        System.out.println("setLength");
        Number length = null;
        BoxGeneric instance = new BoxGeneric();
        instance.setLength(length);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerimeter method, of class BoxGeneric.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        BoxGeneric instance = new BoxGeneric();
        double expResult = 0.0;
        double result = instance.getPerimeter();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSquareSurface method, of class BoxGeneric.
     */
    @Test
    public void testGetSquareSurface() {
        System.out.println("getSquareSurface");
        BoxGeneric instance = new BoxGeneric();
        double expResult = 0.0;
        double result = instance.getSquareSurface();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVolume method, of class BoxGeneric.
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        BoxGeneric instance = new BoxGeneric(2, 3, 4);
        double expResult = 24.0;
        double result = instance.getVolume();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of perimeter method, of class BoxGeneric.
     */
    @Test
    public void testPerimeter() {
        System.out.println("perimeter");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = BoxGeneric.perimeter(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of squareSurface method, of class BoxGeneric.
     */
    @Test
    public void testSquareSurface() {
        System.out.println("squareSurface");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = BoxGeneric.squareSurface(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of volume method, of class BoxGeneric.
     */
    @Test
    public void testVolume() {
        System.out.println("volume");
        double width = 0.0;
        double height = 0.0;
        double length = 0.0;
        double expResult = 0.0;
        double result = BoxGeneric.volume(width, height, length);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compare method, of class BoxGeneric.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        BoxGeneric b1 = null;
        BoxGeneric b2 = null;
        int expResult = 0;
        int result = BoxGeneric.compare(b1, b2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of open method, of class BoxGeneric.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        BoxGeneric instance = new BoxGeneric();
        instance.open();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class BoxGeneric.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        BoxGeneric instance = new BoxGeneric();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBox method, of class BoxGeneric.
     */
    @Test
    public void testCreateBox() {
        System.out.println("createBox");
        Number width = null;
        Number height = null;
        Number length = null;
        BoxGeneric expResult = null;
        BoxGeneric result = BoxGeneric.createBox(width, height, length);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createStandardSizeBox method, of class BoxGeneric.
     */
    @Test
    public void testCreateStandardSizeBox() {
        System.out.println("createStandardSizeBox");
        char type = ' ';
        BoxGeneric expResult = null;
        BoxGeneric result = BoxGeneric.createStandardSizeBox(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class BoxGeneric.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        BoxGeneric instance = new BoxGeneric();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class BoxGeneric.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        BoxGeneric instance = new BoxGeneric();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class BoxGeneric.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        BoxGeneric o = null;
        BoxGeneric instance = new BoxGeneric();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class BoxGeneric.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BoxGeneric instance = new BoxGeneric();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
