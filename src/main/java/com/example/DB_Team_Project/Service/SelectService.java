package com.example.DB_Team_Project.Service;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Repository.JdbcTemplateEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectService {
    @Autowired
    private final JdbcTemplateEmployeeRepository repository;

    public List<Employee> select() {
        return repository.findAll();
    }
}
