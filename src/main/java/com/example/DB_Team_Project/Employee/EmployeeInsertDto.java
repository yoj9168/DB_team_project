package com.example.DB_Team_Project.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmployeeInsertDto {
    private String ssn;
    private String fname;

    private String lname;

    private String minit;
    private String date;
    private String address;
    private String sex;
    private double salary;
    private String super_ssn;
    private int dno;

    @Override
    public String toString() {
        return "(" +
                '\'' + fname + '\'' +
                "," +'\'' + minit + '\'' +
                "," +'\'' + lname + '\'' +
                "," +'\'' + ssn + '\'' +
                "," +'\'' + date + '\'' +
                "," +'\'' + address + '\'' +
                "," +'\'' + sex + '\'' +
                "," + salary +
                "," +'\'' + super_ssn + '\'' +
                "," + dno +
                ',' + "CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";
    }
}
