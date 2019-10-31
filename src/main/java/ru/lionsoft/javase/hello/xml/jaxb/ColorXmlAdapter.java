/*
 * File:    ColorXmlAdapter.java
 * Project: HelloJavaSE
 * Date:    5 дек. 2018 г. 21:20:21
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.xml.jaxb;

import java.awt.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class ColorXmlAdapter extends XmlAdapter<String, Color> {

    private static final String BLACK   = "black";
    private static final String BLUE    = "blue";
    private static final String CYAN    = "cyan";
    private static final String DARK_GRAY = "darkGray";
    private static final String GRAY    = "gray";
    private static final String GREEN   = "green";
    private static final String LIGHT_GRAY = "lightGray";
    private static final String MAGENTA = "magenta";
    private static final String ORANGE  = "orange";
    private static final String PINK    = "pink";
    private static final String RED     = "red";
    private static final String WHITE   = "white";
    private static final String YELLOW  = "yellow";
    
    @Override
    public Color unmarshal(String vt) throws Exception {
        if (vt.startsWith("#")) {
            // Decode Hex Color
            int rgb = Integer.parseUnsignedInt(vt.substring(1), 16);
            return new Color(rgb);
        }
        switch (vt) {
            case BLACK:      return Color.BLACK;
            case BLUE:       return Color.BLUE;
            case CYAN:       return Color.CYAN;
            case DARK_GRAY:  return Color.DARK_GRAY;
            case GRAY:       return Color.GRAY;
            case GREEN:      return Color.GREEN;
            case LIGHT_GRAY: return Color.LIGHT_GRAY;
            case MAGENTA:    return Color.MAGENTA;
            case ORANGE:     return Color.ORANGE;
            case PINK:       return Color.PINK;
            case RED:        return Color.RED;
            case WHITE:      return Color.WHITE;
            case YELLOW:     return Color.YELLOW;
        }
        
        return Color.BLACK;
    }

    @Override
    public String marshal(Color bt) throws Exception {
        if (bt.equals(Color.BLACK)) return BLACK;
        if (bt.equals(Color.BLUE))  return BLUE;
        if (bt.equals(Color.CYAN))  return CYAN;
        if (bt.equals(Color.DARK_GRAY)) return DARK_GRAY;
        if (bt.equals(Color.GRAY))  return GRAY;
        if (bt.equals(Color.GREEN)) return GREEN;
        if (bt.equals(Color.LIGHT_GRAY)) return LIGHT_GRAY;
        if (bt.equals(Color.MAGENTA)) return MAGENTA;
        if (bt.equals(Color.ORANGE)) return ORANGE;
        if (bt.equals(Color.PINK))   return PINK;
        if (bt.equals(Color.RED))    return RED;
        if (bt.equals(Color.WHITE))  return WHITE;
        if (bt.equals(Color.YELLOW)) return YELLOW;
        // Alpha, Red, Green, Blue
        return String.format("#%08X", bt.getRGB());
    }
    
}
