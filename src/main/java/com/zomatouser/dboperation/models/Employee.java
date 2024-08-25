package com.zomatouser.dboperation.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employeeNumber")
    private int employeeNumber;
}
