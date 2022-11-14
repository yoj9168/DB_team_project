package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.DependentDto;
import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeInsertDto;
import com.example.DB_Team_Project.Employee.EmployeeUpdateDto;

public interface OperationRepository {

    int delete(EmployeeDeleteDto dto);
    int update(EmployeeUpdateDto dto);
    int insert(EmployeeInsertDto dto);
    int updateDepartment(EmployeeUpdateDto dto);
}
