package com.expensesplitter.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense_group")
public class Group {
    @Id
    private String id;

    public Group() { this.id = UUID.randomUUID().toString(); }
    public String getId() { return id; }
}