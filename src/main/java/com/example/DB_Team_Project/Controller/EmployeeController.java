package com.example.DB_Team_Project.Controller;

import com.example.DB_Team_Project.Employee.*;
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
    @PostMapping("/employee/selectName")
    public List<String> selectName(@RequestBody EmployeeSelectDto dto){
        return selectService.selectName(dto);
    }

    @PostMapping("/employee/delete")
    public int delete(@RequestBody EmployeeDeleteDto dto){
        return selectService.delete(dto);
    }

    @PostMapping("/employee/update")
    public int update(@RequestBody EmployeeUpdateDto dto){
        return selectService.update(dto);
    }

    @PostMapping("/employee/insert")
    public int insert(@RequestBody EmployeeInsertDto dto){return selectService.insert(dto);}

    @PostMapping("/employee/selectDependent")
    public List<String> selectDependent(@RequestBody DependentDto dto){
        return selectService.selectDependent(dto);
    }
    @PostMapping("/employee/updateDepartment")
    public int updateDepartment(@RequestBody DepartmentDto dto){
        return selectService.updateDepartment(dto);
    }
}
