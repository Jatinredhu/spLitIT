package com.expensesplitter.dto;

import java.util.List;

public class ExpenseRequest {
    private String groupId;
    private String description;
    private double amount;
    private Long paidById;
    private List<Long> splitAmongIds;

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Long getPaidById() { return paidById; }
    public void setPaidById(Long paidById) { this.paidById = paidById; }
    public List<Long> getSplitAmongIds() { return splitAmongIds; }
    public void setSplitAmongIds(List<Long> splitAmongIds) { this.splitAmongIds = splitAmongIds; }
}