package com.task.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique= true, name = "person_id")
    Integer personId;
    @Column(unique= true, name = "person_name")
    String personName;
    @ManyToMany(mappedBy = "personList")
    List<Company> companyList;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

}
