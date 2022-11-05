package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class JdbcTemplateEmployeeRepository implements EmployeeRepository{

    private final JdbcTemplate jdbcTemplate;
    private boolean check[];
    private String[] attributes;

    @Autowired
    public JdbcTemplateEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> find(List<String> attribute) {
        check = new boolean[]{false,false,false,false,false,false,false,false,false,false};
        attributes= new String[]{"ssn", "fname", "lname","minit","bdate", "address", "sex", "salary", "super_ssn", "dno"};
        String select = "a.ssn, a.fname, a.lname, a.minit, a.bdate, a.address, a.sex, a.salary, c.fname, c.lname, c.minit, b.dname";

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < attribute.size(); j++){
                if(attributes[i].equals(attribute.get(j)))
                    check[i] = true;
            }
        }
        System.out.println("select "+select+" from (employee as a join department as b on dnumber = dno) join employee as c on a.super_ssn=c.ssn");
        return jdbcTemplate.query("select "+select+" from (employee as a join department as b on dnumber = dno) join employee as c on a.super_ssn=c.ssn",employeeRowMapper());
    }

    private RowMapper<Employee> employeeRowMapper() {
        return (rs, rowNum) -> {
            Employee employee;
            String ssn = null, name = null, bdate = null, address = null, sex = null, super_ssn = null, dname = null;
            double salary = 0;
            if(check[0]){
                ssn = rs.getString("Ssn");
            }
            if(check[1] && check[2] && check[3]){
                name = rs.getString("Fname")+rs.getString("Minit")+rs.getString("Lname");
            }
            if(check[4]){
                bdate = rs.getString("Bdate");
            }
            if(check[5]){
                address = rs.getString("Address");
            }
            if(check[6]){
                sex = rs.getString("Sex");
            }
            if(check[7]){
                salary = rs.getDouble("Salary");
            }
            if(check[8]){
                super_ssn = rs.getString("c.Fname")+rs.getString("c.Minit")+rs.getString("c.Lname");
            }
            if(check[9]){
                dname = rs.getString("Dname");
            }
            employee = Employee.builder()
                    .ssn(ssn)
                    .name(name)
                    .date(bdate)
                    .address(address)
                    .sex(sex)
                    .salary(salary)
                    .super_ssn(super_ssn)
                    .dno(dname)
                    .build();
            return employee;
        };
    }

}