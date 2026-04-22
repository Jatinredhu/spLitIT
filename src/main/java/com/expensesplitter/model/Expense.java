package com.expensesplitter.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupId;
    private String description;
    private double amount;

    @ManyToOne
    private Person paidBy;

    @ManyToMany
    private List<Person> splitAmong;

    public Expense() {}
    public Long getId() { return id; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Person getPaidBy() { return paidBy; }
    public void setPaidBy(Person paidBy) { this.paidBy = paidBy; }
    public List<Person> getSplitAmong() { return splitAmong; }
    public void setSplitAmong(List<Person> splitAmong) { this.splitAmong = splitAmong; }
}