package com.expensesplitter.model;

import jakarta.persistence.*;
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String groupId;

    public Person() {}
    public Person(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
}