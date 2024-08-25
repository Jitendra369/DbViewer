package com.zomatouser.dboperation.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customerNumber")
    private int customerNumber;

    @Column(name = "contactFirstName")
    private String contactFirstName;

    @Column(name = "contactLastName")
    private String contactLastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "addressLine1")
    private String addressLine1;

    @Column(name = "addressLine2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

}
