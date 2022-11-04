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
    public List<Employee> findAll() {
        return jdbcTemplate.query("select * from employee", employeeRowMapper());
    }

    @Override
    public List<Employee> find(List<String> attribute) {
        check = new boolean[]{false,false,false,false,false,false,false,false,false,false};
        attributes= new String[]{"ssn", "fname", "lname","minit","bdate", "address", "sex", "salary", "super_ssn", "dno"};
        String select = "";

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < attribute.size(); j++){
                if(attributes[i].equals(attribute.get(j)))
                    check[i] = true;
            }
        }
        for(int i = 0; i < 8; i++){
            System.out.println("bool " + check[i]);
            System.out.println("check1 "+attributes[i]);
        }
        for(String i: attribute){
            System.out.println("check2 "+i);
        }
        for(String i : attribute){
            select += i;
            if(i != attribute.get(attribute.size()-1))
                select+=", ";
        }

        return jdbcTemplate.query("select " + select + " from employee",employeeRowMapper());
    }

    private RowMapper<Employee> employeeRowMapper() {
        return (rs, rowNum) -> {
            Employee employee = new Employee();

            if(check[0]){
                //employee = Employee.builder().ssn(rs.getString("Ssn")).build();
                employee.setSsn(rs.getString("Ssn"));
            }
            if(check[1] && check[2] && check[3]){
                //employee = Employee.builder().name(rs.getString(rs.getString("Fname")+rs.getString("Minit")+rs.getString("Lname"))).build();
                employee.setName(rs.getString("Fname")+rs.getString("Minit")+rs.getString("Lname"));
            }
            if(check[4]){
                //employee = Employee.builder().date(rs.getString("Bdate")).build();
                employee.setDate(rs.getString("Bdate"));
            }
            if(check[5]){
                //employee = Employee.builder().address(rs.getString("Address")).build();
                employee.setAddress(rs.getString("Address"));
            }
            if(check[6]){
                //employee = Employee.builder().sex(rs.getString("Sex")).build();
                employee.setSex(rs.getString("Sex"));
            }
            if(check[7]){
                //employee = Employee.builder().salary(Double.valueOf(rs.getString("Salary"))).build();
                employee.setSalary(Double.parseDouble(rs.getString("Salary")));
            }
            if(check[8]){
                //employee = Employee.builder().super_ssn(rs.getString("Super_ssn")).build();
                employee.setSuper_ssn(rs.getString("Super_ssn"));
            }
            if(check[9]){
                employee.setDno(Integer.parseInt(rs.getString("Dno")));
                //employee = Employee.builder().dno(Integer.parseInt(rs.getString("Dno"))).build();
            }
            return employee;
        };
    }

}
