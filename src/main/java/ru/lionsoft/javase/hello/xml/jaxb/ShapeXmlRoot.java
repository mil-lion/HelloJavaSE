/*
 * File:    ShapeXmlRoot.java
 * Project: HelloJavaSE
 * Date:    5 дек. 2018 г. 20:52:48
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.xml.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.lionsoft.javase.hello.gui.shapes.AbstractShape;
import ru.lionsoft.javase.hello.gui.shapes.Circle;
import ru.lionsoft.javase.hello.gui.shapes.FillCircle;
import ru.lionsoft.javase.hello.gui.shapes.FillOval;
import ru.lionsoft.javase.hello.gui.shapes.FillRectangle;
import ru.lionsoft.javase.hello.gui.shapes.Line;
import ru.lionsoft.javase.hello.gui.shapes.Oval;
import ru.lionsoft.javase.hello.gui.shapes.Rectangle;
import ru.lionsoft.javase.hello.gui.shapes.Text;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement(name = "shapes")
@XmlType(name = "shapes")
@XmlSeeAlso({AbstractShape.class, Line.class, Text.class, 
    Rectangle.class, FillRectangle.class, 
    Oval.class, FillOval.class, 
    Circle.class, FillCircle.class})
public class ShapeXmlRoot {
    
    private List<AbstractShape> shapes;

    @XmlElementRef
    public List<AbstractShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<AbstractShape> shapes) {
        this.shapes = shapes;
    }

    public ShapeXmlRoot() {
    }

    public ShapeXmlRoot(List shapes) {
        this.shapes = shapes;
    }
    
    @Override
    public String toString() {
        return "ShapeXmlRoot{" + "shapes=" + shapes + '}';
    }
    
}
