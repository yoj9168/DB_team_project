package com.example.DB_Team_Project.Department;

import com.example.DB_Team_Project.Employee.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {
    private String dname;
    private int dnumber;
    private String mgr_ssn;
    private String mgr_start_date;
    private Employee employee;

    @Builder
    public Department(String dname, int dnumber, Employee employee){
        this.dname = dname;
        this.dnumber = dnumber;
        this.employee = employee;
    }

    public Department() {

    }
}
