package com.expensesplitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensesplitter.model.Group;

public interface GroupRepository extends JpaRepository<Group, String> {}