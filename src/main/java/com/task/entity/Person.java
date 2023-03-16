package com.task.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    Integer personId;
    @Column(name = "person_name")
    String personName;
    @ManyToMany(mappedBy = "personList")
    List<Company> companyList;
}
