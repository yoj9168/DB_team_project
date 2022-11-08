package com.example.DB_Team_Project.Controller;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeSelectDto;
import com.example.DB_Team_Project.Service.SelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final SelectService selectService;

    @PostMapping("/employee")
    public List<Employee> select(@RequestBody EmployeeSelectDto dto){
        return selectService.select(dto);
    }

    @PostMapping("/employee/department")
    public List<String> getDepartment(){
        return selectService.getDepartment();
    }
    @PostMapping("/employee/sex")
    public List<String> getSex(){
        return selectService.getSex();
    }
    @PostMapping("/employee/name")
    public List<String> getName(){
        return selectService.getName();
    }

    @PostMapping("/employee/count")
    public int selectCount(@RequestBody EmployeeSelectDto dto){
        return selectService.selectCount(dto);
    }

    @PostMapping("/employee/delete")
    public int delete(@RequestBody EmployeeDeleteDto dto){
        return selectService.delete(dto);

    }
}
