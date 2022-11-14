package com.example.DB_Team_Project.Service;

import com.example.DB_Team_Project.Employee.*;
import com.example.DB_Team_Project.Repository.JdbcEmployeeSelectRepository;
import com.example.DB_Team_Project.Repository.JdbcEmployeeOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectService {
    @Autowired
    private final JdbcEmployeeSelectRepository repository;
    @Autowired
    private final JdbcEmployeeOperationRepository opearationRepos;

    @Transactional
    public List<Employee> select(EmployeeSelectDto dto) {
        return repository.find(dto);
    }

    public int selectCount(EmployeeSelectDto dto) {
        return repository.selectCount(dto);
    }

    public List<String> getDepartment() {
        return repository.getDepartment();
    }

    public List<String> getSex() {
        return repository.getSex();
    }
    public List<String> getName(){
        return repository.getName();
    }
    public int delete(EmployeeDeleteDto dto) {
        return opearationRepos.delete(dto);
    }
    public int update(EmployeeUpdateDto dto) {
        return opearationRepos.update(dto);
    }

    public int insert(EmployeeInsertDto dto) {
        return opearationRepos.insert(dto);
    }

    public List<String> selectName(EmployeeSelectDto dto) {
        return repository.selectName(dto);
    }

    public List<String> selectDependent(DependentDto dto) {
        return repository.selectDependent(dto);
    }

    public int updateDepartment(DepartmentDto dto) {
        return opearationRepos.updateDepartment(dto);
    }
}
