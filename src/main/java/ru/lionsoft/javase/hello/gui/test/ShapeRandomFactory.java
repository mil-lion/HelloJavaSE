/*
 * File:    ShapeRandomFactory.java
 * Project: HelloJavaSE
 * Date:    6 дек. 2018 г. 22:43:22
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.test;

import java.awt.Color;
import java.util.Date;
import java.util.Random;
import ru.lionsoft.javase.hello.gui.ShapeDraw;
import ru.lionsoft.javase.hello.gui.shapes.AbstractShape;
import ru.lionsoft.javase.hello.gui.shapes.Circle;
import ru.lionsoft.javase.hello.gui.shapes.FillCircle;
import ru.lionsoft.javase.hello.gui.shapes.FillOval;
import ru.lionsoft.javase.hello.gui.shapes.FillRectangle;
import ru.lionsoft.javase.hello.gui.shapes.FillSquare;
import ru.lionsoft.javase.hello.gui.shapes.Line;
import ru.lionsoft.javase.hello.gui.shapes.Oval;
import ru.lionsoft.javase.hello.gui.shapes.Rectangle;
import ru.lionsoft.javase.hello.gui.shapes.Square;
import ru.lionsoft.javase.hello.gui.shapes.Text;

/**
 * Класс генерирующий произвольные фигуры
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class ShapeRandomFactory {
    
    private ShapeRandomFactory() {
    }
    
    public static ShapeRandomFactory getInstance() {
        return ShapeRandomFactoryHolder.INSTANCE;
    }
    
    private static class ShapeRandomFactoryHolder {

        private static final ShapeRandomFactory INSTANCE = new ShapeRandomFactory();
    }
    
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;
    private static final String[] STRINGS = { 
        "Hello", "Hello World!", "Vivat Cesar!", "Viavat Imperator", "Java SE 8",
        "Привет", "Привет Мир!", "Виват Цезарь!", "Виват Император!", (new Date()).toString()
    };
    
    public ShapeDraw randomShape() {
        Random r = new Random();
        ShapeDraw shape;
        
        int order = r.nextInt(10);
        Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        int x = r.nextInt(WINDOW_WIDTH * 2 / 3);
        int y = r.nextInt(WINDOW_HEIGHT * 2 / 3);
        int x2 = r.nextInt(WINDOW_WIDTH);
        int y2 = r.nextInt(WINDOW_HEIGHT);
        int width = r.nextInt(WINDOW_WIDTH / 3);
        int height = r.nextInt(WINDOW_HEIGHT / 3);
        String text = STRINGS[r.nextInt(STRINGS.length - 1)];
        
        switch (r.nextInt(10)) {
            case 0:
                shape = new Line(color, x, y, x2, y2);
                break;
            case 1:
                shape = new Rectangle(color, x, y, width, height);
                break;
            case 2:
                shape = new FillRectangle(color, x, y, width, height);
                break;
            case 3:
                shape = new Oval(color, x, y, width, height);
                break;
            case 4:
                shape = new FillOval(color, x, y, width, height);
                break;
            case 5:
                shape = new Circle(color, x, y, width / 2);
                break;
            case 6:
                shape = new FillCircle(color, x, y, width / 2);
                break;
            case 7:
                shape = new Square(color, x, y, width);
                break;
            case 8:
                shape = new FillSquare(color, x, y, width);
                break;
            case 9:
            default:
                shape = new Text(color, x, y, text);
        }
        ((AbstractShape)shape).setOrder(order);
        return shape;
    }
    
    
}
