/*
 * File:    MicroMarket.java
 * Project: HelloJavaSE
 * Date:    26 февр. 2020 г. 20:34:47
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jdbc.entities;

import java.io.Serializable;
import java.util.Objects;
import ru.lionsoft.javase.hello.db.jdbc.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.annotation.PrimaryKey;
import ru.lionsoft.javase.hello.db.jdbc.annotation.Table;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Table(name = "MICRO_MARKET")
public class MicroMarket implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************

    // zipCode
    
    @PrimaryKey
    @Column(name = "ZIP_CODE")
    private String zipCode;

    /**
     * Get the value of zipCode
     *
     * @return the value of zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Set the value of zipCode
     *
     * @param zipCode new value of zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // radius
    
    private Double radius;

    /**
     * Get the value of radius
     *
     * @return the value of radius
     */
    public Double getRadius() {
        return radius;
    }

    /**
     * Set the value of radius
     *
     * @param radius new value of radius
     */
    public void setRadius(Double radius) {
        this.radius = radius;
    }

    // areaLength
    
    @Column(name = "AREA_LENGTH")
    private Double areaLength;

    /**
     * Get the value of areaLength
     *
     * @return the value of areaLength
     */
    public Double getAreaLength() {
        return areaLength;
    }

    /**
     * Set the value of areaLength
     *
     * @param areaLength new value of areaLength
     */
    public void setAreaLength(Double areaLength) {
        this.areaLength = areaLength;
    }

    // areaWidth
    
    @Column(name = "AREA_WIDTH")
    private Double areaWidth;

    /**
     * Get the value of areaWidth
     *
     * @return the value of areaWidth
     */
    public Double getAreaWidth() {
        return areaWidth;
    }

    /**
     * Set the value of areaWidth
     *
     * @param areaWidth new value of areaWidth
     */
    public void setAreaWidth(Double areaWidth) {
        this.areaWidth = areaWidth;
    }

    // ******************** Constructors *******************

    public MicroMarket() {
    }

    public MicroMarket(String zipCode) {
        this.zipCode = zipCode;
    }

    // ******************** Equals & HashCode *******************


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.zipCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MicroMarket other = (MicroMarket) obj;
        return Objects.equals(this.zipCode, other.zipCode);
    }

    // ******************** Cast to String *******************

    @Override
    public String toString() {
        return "MicroMarket{" 
                + "zipCode=" + zipCode 
                + ", radius=" + radius 
                + ", areaLength=" + areaLength 
                + ", areaWidth=" + areaWidth 
                + '}';
    }
}

