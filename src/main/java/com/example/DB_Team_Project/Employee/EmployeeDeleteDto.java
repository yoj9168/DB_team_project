package com.example.DB_Team_Project.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmployeeDeleteDto {
    private String ssn;
    private String name;
    private String date;
    private String address;
    private String sex;
    private double salary;
    private String super_ssn;
    private String dno;

    @Builder
    public EmployeeDeleteDto(String ssn, String name, String date, String address, String sex, Double salary, String super_ssn, String dno){
        this.ssn = ssn;
        this.name = name;
        this.date = date;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.super_ssn = super_ssn;
        this.dno = dno;
    }

}
