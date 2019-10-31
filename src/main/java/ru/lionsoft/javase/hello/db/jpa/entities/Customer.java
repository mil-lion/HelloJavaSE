/*
 * File:    Customer.java
 * Project: HelloJavaSE
 * Date:    30 окт. 2019 г. 21:23:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Entity
@Table(catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.id = :customerId"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByAddressline1", query = "SELECT c FROM Customer c WHERE c.address.addressline1 = :addressline1"),
    @NamedQuery(name = "Customer.findByAddressline2", query = "SELECT c FROM Customer c WHERE c.address.addressline2 = :addressline2"),
    @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.address.city = :city"),
    @NamedQuery(name = "Customer.findByState", query = "SELECT c FROM Customer c WHERE c.address.state = :state"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.contactInfo.phone = :phone"),
    @NamedQuery(name = "Customer.findByFax", query = "SELECT c FROM Customer c WHERE c.contactInfo.fax = :fax"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.contactInfo.email = :email"),
    @NamedQuery(name = "Customer.findByCreditLimit", query = "SELECT c FROM Customer c WHERE c.creditLimit = :creditLimit")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer id;

    @JoinColumn(name = "DISCOUNT_CODE", referencedColumnName = "DISCOUNT_CODE", nullable = false)
    @ManyToOne(optional = false)
    private DiscountCode discountCode;

    @JoinColumn(name = "ZIP", referencedColumnName = "ZIP_CODE", nullable = false)
    @ManyToOne(optional = false)
    private MicroMarket microMarket;

    @Column(length = 30)
    private String name;

    @Embedded
    private Address address = new Address();
    
    @Embedded
    private ContactInfo contactInfo = new ContactInfo();

    @Column(name = "CREDIT_LIMIT")
    private Integer creditLimit;

    
    public Customer() {
    }

    public Customer(Integer customerId) {
        this.id = customerId;
    }

    
    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public MicroMarket getMicroMarket() {
        return microMarket;
    }

    public void setMicroMarket(MicroMarket microMarket) {
        this.microMarket = microMarket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
   
    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return !((this.id == null && other.id != null) 
                || (this.id != null && !this.id.equals(other.id)));
    }

    
    @Override
    public String toString() {
        return "Customer{" 
                + "id=" + id 
                + ", discountCode=" + discountCode 
                + ", microMarket=" + microMarket 
                + ", name=" + name 
                + ", address=" + address 
                + ", contactInfo=" + contactInfo 
                + ", creditLimit=" + creditLimit 
                + '}';
    }

    
}
