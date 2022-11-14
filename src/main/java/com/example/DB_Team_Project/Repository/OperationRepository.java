package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.*;

public interface OperationRepository {

    int delete(EmployeeDeleteDto dto);
    int update(EmployeeUpdateDto dto);
    int insert(EmployeeInsertDto dto);
    int updateDepartment(DepartmentDto dto);
}
