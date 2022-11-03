package com.example.DB_Team_Project.Controller;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Service.SelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final SelectService selectService;

    @GetMapping("/")
    public List<Employee> select(){
       return selectService.select();
    }


}
