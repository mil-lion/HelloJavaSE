/*
 * File:    ColorXmlAdapter.java
 * Project: HelloJavaSE
 * Date:    5 дек. 2018 г. 21:20:21
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.json;

import java.awt.Color;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class ColorJsonAdapter implements JsonbAdapter<Color, String> {

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
    public String adaptToJson(Color orgnl) throws Exception { 
        if (orgnl.equals(Color.BLACK)) return BLACK;
        if (orgnl.equals(Color.BLUE))  return BLUE;
        if (orgnl.equals(Color.CYAN))  return CYAN;
        if (orgnl.equals(Color.DARK_GRAY)) return DARK_GRAY;
        if (orgnl.equals(Color.GRAY))  return GRAY;
        if (orgnl.equals(Color.GREEN)) return GREEN;
        if (orgnl.equals(Color.LIGHT_GRAY)) return LIGHT_GRAY;
        if (orgnl.equals(Color.MAGENTA)) return MAGENTA;
        if (orgnl.equals(Color.ORANGE)) return ORANGE;
        if (orgnl.equals(Color.PINK))   return PINK;
        if (orgnl.equals(Color.RED))    return RED;
        if (orgnl.equals(Color.WHITE))  return WHITE;
        if (orgnl.equals(Color.YELLOW)) return YELLOW;
        // Alpha, Red, Green, Blue
        return String.format("#%08X", orgnl.getRGB());
    }

    @Override
    public Color adaptFromJson(String adptd) throws Exception {
        if (adptd.startsWith("#")) {
            // Decode Hex Color
            int rgb = Integer.parseUnsignedInt(adptd.substring(1), 16);
            return new Color(rgb);
        }
        switch (adptd) {
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
    
}
