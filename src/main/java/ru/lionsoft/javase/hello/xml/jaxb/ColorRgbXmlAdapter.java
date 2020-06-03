/*
 * File:    ColorRgbXmlAdapter.java
 * Project: HelloJavaSE
 * Date:    3 июн. 2020 г. 15:22:19
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.xml.jaxb;

import java.awt.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class ColorRgbXmlAdapter extends XmlAdapter<ColorRgbXmlAdapter.ColorValueType, Color> {

    @Override
    public Color unmarshal(ColorValueType vt) throws Exception {
        return new Color(vt.red, vt.green, vt.blue);
    }

    @Override
    public ColorValueType marshal(Color bt) throws Exception {
        return new ColorValueType(bt.getRed(), bt.getGreen(), bt.getBlue());
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ColorValueType {
        
        @XmlAttribute
        public int red;
        
        @XmlAttribute
        public int green;
        
        @XmlAttribute
        public int blue;

        public ColorValueType() {
        }

        public ColorValueType(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
        
        
    }
}
