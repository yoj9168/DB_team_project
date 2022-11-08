package com.example.DB_Team_Project.Employee;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeSelectDto {
    private List<String> attribute;
    private String selectRange;
    private String search;

    @Builder
    public EmployeeSelectDto(List<String> attribute, String selectRange, String search){
        this.attribute = attribute;
        this.selectRange = selectRange;
        this.search = search;
    }
}
