package com.expensesplitter.repository;

import com.expensesplitter.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByGroupId(String groupId);
}