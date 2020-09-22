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
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Column;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Id;
import ru.lionsoft.javase.hello.db.jdbc.orm.annotation.Table;

/**
 * Сущность Магазин
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Table(name = "MICRO_MARKET")
public class MicroMarket implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // ******************** Properties *******************

    // zipCode
    
    @Id
    @Column(name = "ZIP_CODE")
    public String zipCode;

    // radius

    @Column
    public Double radius;

    // areaLength
    
    @Column(name = "AREA_LENGTH")
    public Double areaLength;

    // areaWidth
    
    @Column(name = "AREA_WIDTH")
    public Double areaWidth;

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
