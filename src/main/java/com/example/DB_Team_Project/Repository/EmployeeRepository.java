package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeRepository {
    List<Employee> find(EmployeeDto dto);
}
