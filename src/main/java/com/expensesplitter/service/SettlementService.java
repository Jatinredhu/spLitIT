package com.expensesplitter.service;

import com.expensesplitter.model.Expense;
import com.expensesplitter.model.Person;
import com.expensesplitter.repository.ExpenseRepository;
import com.expensesplitter.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettlementService {
    private final ExpenseRepository expenseRepo;
    private final PersonRepository personRepo;

    public SettlementService(ExpenseRepository expenseRepo, PersonRepository personRepo) {
        this.expenseRepo = expenseRepo;
        this.personRepo = personRepo;
    }

    public List<Map<String, Object>> calculateSettlements(String groupId) {
        List<Person> people = personRepo.findByGroupId(groupId);
        List<Expense> expenses = expenseRepo.findByGroupId(groupId);

        Map<Long, Double> balance = new HashMap<>();
        for (Person p : people) balance.put(p.getId(), 0.0);

        for (Expense e : expenses) {
            int count = e.getSplitAmong().size();
            if (count == 0) continue;
            double share = e.getAmount() / count;

            Long payerId = e.getPaidBy().getId();
            balance.merge(payerId, e.getAmount(), Double::sum);

            for (Person p : e.getSplitAmong()) {
                balance.merge(p.getId(), -share, Double::sum);
            }
        }

        Map<Long, String> nameMap = new HashMap<>();
        for (Person p : people) nameMap.put(p.getId(), p.getName());

        List<Map.Entry<Long, Double>> creditors = new ArrayList<>();
        List<Map.Entry<Long, Double>> debtors = new ArrayList<>();

        for (Map.Entry<Long, Double> entry : balance.entrySet()) {
            if (entry.getValue() > 0.001) creditors.add(entry);
            else if (entry.getValue() < -0.001) debtors.add(entry);
        }

        List<Map<String, Object>> settlements = new ArrayList<>();
        int i = 0, j = 0;
        while (i < creditors.size() && j < debtors.size()) {
            Map.Entry<Long, Double> creditor = creditors.get(i);
            Map.Entry<Long, Double> debtor = debtors.get(j);

            double amount = Math.min(creditor.getValue(), -debtor.getValue());
            amount = Math.round(amount * 100.0) / 100.0;

            Map<String, Object> settlement = new LinkedHashMap<>();
            settlement.put("from", nameMap.get(debtor.getKey()));
            settlement.put("to", nameMap.get(creditor.getKey()));
            settlement.put("amount", amount);
            settlements.add(settlement);

            creditor.setValue(creditor.getValue() - amount);
            debtor.setValue(debtor.getValue() + amount);

            if (creditor.getValue() < 0.001) i++;
            if (debtor.getValue() > -0.001) j++;
        }

        return settlements;
    }
}