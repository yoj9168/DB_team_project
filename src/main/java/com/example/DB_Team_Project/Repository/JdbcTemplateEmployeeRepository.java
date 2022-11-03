package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcTemplateEmployeeRepository implements EmployeeRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("select * from employee", employeeRowMapper());
    }

    @Override
    public String find() {
        try {
            return jdbcTemplate.queryForObject("select Fname from employee where Lname=?", String.class, "Wong");
        }
        catch (IncorrectResultSizeDataAccessException error) { // 쿼리문에 해당하는 결과가 없거나 2개 이상일 때
            return null;
        }
    }

    private RowMapper<Employee> employeeRowMapper() {
        return (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setSsn(rs.getString("Ssn"));
            employee.setFname(rs.getString("Fname"));
            employee.setLname(rs.getString("Lname"));
            employee.setMinit(rs.getString("Minit"));
            employee.setDate(rs.getString("Bdate"));
            employee.setAddress(rs.getString("Address"));
            employee.setSex(rs.getString("Sex"));
            employee.setSalary(Double.parseDouble(rs.getString("Salary")));
            employee.setSuper_ssn(rs.getString("Super_ssn"));
            employee.setDno(Integer.parseInt(rs.getString("Dno")));
            return employee;
        };
    }
}
