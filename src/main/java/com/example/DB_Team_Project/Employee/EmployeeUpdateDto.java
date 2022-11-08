package com.example.DB_Team_Project.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmployeeUpdateDto {
    private String setCondition;
    private String setValue;
    private String whereCondition;
    private String whereValue;

}
