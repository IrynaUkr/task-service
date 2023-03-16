package com.task.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    Integer companyId;

    @Column(name = "company_name")
    String companyName;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "person_company",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")}
    )
    List<Person> personList;

}
