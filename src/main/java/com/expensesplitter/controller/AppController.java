package com.expensesplitter.controller;

import com.expensesplitter.dto.ExpenseRequest;
import com.expensesplitter.model.Expense;
import com.expensesplitter.model.Person;
import com.expensesplitter.repository.ExpenseRepository;
import com.expensesplitter.repository.PersonRepository;
import com.expensesplitter.service.SettlementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AppController {

    private final PersonRepository personRepo;
    private final ExpenseRepository expenseRepo;
    private final SettlementService settlementService;

    public AppController(PersonRepository personRepo, ExpenseRepository expenseRepo, SettlementService settlementService) {
        this.personRepo = personRepo;
        this.expenseRepo = expenseRepo;
        this.settlementService = settlementService;
    }

    // --- People ---

    @GetMapping("/people")
    public List<Person> getAllPeople() {
        return personRepo.findAll();
    }

    @PostMapping("/people")
    public Person addPerson(@RequestBody Map<String, String> body) {
        Person p = new Person(body.get("name"));
        return personRepo.save(p);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // --- Expenses ---

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @PostMapping("/expenses")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest req) {
        Person paidBy = personRepo.findById(req.getPaidById()).orElse(null);
        if (paidBy == null) return ResponseEntity.badRequest().body("Payer not found");

        List<Person> splitAmong = personRepo.findAllById(req.getSplitAmongIds());
        if (splitAmong.isEmpty()) return ResponseEntity.badRequest().body("Split members not found");

        Expense expense = new Expense();
        expense.setDescription(req.getDescription());
        expense.setAmount(req.getAmount());
        expense.setPaidBy(paidBy);
        expense.setSplitAmong(splitAmong);

        return ResponseEntity.ok(expenseRepo.save(expense));
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // --- Settlement ---

    @GetMapping("/settle")
    public List<Map<String, Object>> getSettlement() {
        return settlementService.calculateSettlements();
    }
}
