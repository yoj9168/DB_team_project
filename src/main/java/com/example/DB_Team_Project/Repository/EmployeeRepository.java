package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();
    String find();
}
