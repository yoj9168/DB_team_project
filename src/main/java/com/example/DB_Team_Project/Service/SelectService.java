package com.example.DB_Team_Project.Service;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDto;
import com.example.DB_Team_Project.Repository.JdbcTemplateEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectService {
    @Autowired
    private final JdbcTemplateEmployeeRepository repository;
    @Transactional
    public List<Employee> select(EmployeeDto dto) {
        return repository.find(dto);
    }

    public int selectCount(EmployeeDto dto) {
        return repository.selectCount(dto);
    }
}
