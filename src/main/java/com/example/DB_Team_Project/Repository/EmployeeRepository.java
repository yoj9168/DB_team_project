package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeSelectDto;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> find(EmployeeSelectDto dto);
    int selectCount(EmployeeSelectDto dto);
    int delete(EmployeeDeleteDto dto);
    List<String> getDepartment();
    List<String> getSex();
    List<String> getName();
}
