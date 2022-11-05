package com.example.DB_Team_Project.Employee;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.example.DB_Team_Project.Department.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Employee {
    private String ssn;
    private String name;
    private String date;
    private String address;
    private String sex;
    private double salary;
    private String super_ssn;
    private String dno;

    @Builder
    public Employee(String ssn, String name, String date, String address, String sex, Double salary, String super_ssn, String dno){
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
