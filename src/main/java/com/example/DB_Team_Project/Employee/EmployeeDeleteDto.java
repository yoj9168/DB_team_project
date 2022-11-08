package com.example.DB_Team_Project.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmployeeDeleteDto {
    private String condition;
    private String value;

    @Builder
    public EmployeeDeleteDto(String condition, String value){
        this.condition = condition;
        this.value = value;
    }
}
