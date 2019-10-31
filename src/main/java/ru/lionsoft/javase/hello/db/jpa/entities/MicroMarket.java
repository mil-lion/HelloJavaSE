/*
 * File:    MicroMarket.java
 * Project: HelloJavaSE
 * Date:    30 окт. 2019 г. 21:23:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jpa.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Entity
@Table(name = "MICRO_MARKET", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MicroMarket.findAll", query = "SELECT m FROM MicroMarket m"),
    @NamedQuery(name = "MicroMarket.findByZipCode", query = "SELECT m FROM MicroMarket m WHERE m.zipCode = :zipCode"),
    @NamedQuery(name = "MicroMarket.findByRadius", query = "SELECT m FROM MicroMarket m WHERE m.radius = :radius"),
    @NamedQuery(name = "MicroMarket.findByAreaLength", query = "SELECT m FROM MicroMarket m WHERE m.areaLength = :areaLength"),
    @NamedQuery(name = "MicroMarket.findByAreaWidth", query = "SELECT m FROM MicroMarket m WHERE m.areaWidth = :areaWidth")})
public class MicroMarket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ZIP_CODE", nullable = false, length = 10)
    private String zipCode;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 52)
    private Double radius;

    @Column(name = "AREA_LENGTH", precision = 52)
    private Double areaLength;

    @Column(name = "AREA_WIDTH", precision = 52)
    private Double areaWidth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "microMarket")
    private List<Customer> customers;

    
    public MicroMarket() {
    }

    public MicroMarket(String zipCode) {
        this.zipCode = zipCode;
    }

    
    @XmlAttribute
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getAreaLength() {
        return areaLength;
    }

    public void setAreaLength(Double areaLength) {
        this.areaLength = areaLength;
    }

    public Double getAreaWidth() {
        return areaWidth;
    }

    public void setAreaWidth(Double areaWidth) {
        this.areaWidth = areaWidth;
    }

    @XmlTransient
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zipCode != null ? zipCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroMarket)) {
            return false;
        }
        MicroMarket other = (MicroMarket) object;
        return !((this.zipCode == null && other.zipCode != null) 
                || (this.zipCode != null && !this.zipCode.equals(other.zipCode)));
    }

    
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
