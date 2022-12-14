package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.DepartmentDto;
import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeInsertDto;
import com.example.DB_Team_Project.Employee.EmployeeUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEmployeeOperationRepository implements OperationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcEmployeeOperationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(EmployeeDeleteDto dto) {
        String value = "";
        if(dto.getCondition().equals("salary")){
            value = dto.getValue();
        }
        else{
            value = "\""+dto.getValue()+"\"";
        }
        String where = dto.getCondition() + " = "+value;
        String sql = "delete from employee where "+where;
        System.out.println(sql);
        return jdbcTemplate.update(sql);
    }

    @Override
    public int update(EmployeeUpdateDto dto) {
        String sql = "update employee set ";
        String setCondition = dto.getSetCondition();
        String setValue = dto.getSetValue();
        String ssn = "\""+dto.getSsn()+"\"";
        if(!setCondition.equals("salary")){
            setValue = "\""+setValue+"\"";
        }
        sql+=setCondition+"="+setValue+",modified=CURRENT_TIMESTAMP() where ssn = "+ssn;
        return jdbcTemplate.update(sql);

    }

    @Override
    public int insert(EmployeeInsertDto dto) {
        System.out.println("insert into employee value "+dto.toString());
        return jdbcTemplate.update("insert into employee value "+dto);
    }

    @Override
    public int updateDepartment(DepartmentDto dto) {
        String sql = "update employee A inner join department B ON A.dno=B.dnumber set A.salary = ";
        sql += dto.getSetValue() + ",modified=CURRENT_TIMESTAMP() where B.dname = "+"\""+dto.getDepartment()+"\"";
        System.out.println(sql);
        return jdbcTemplate.update(sql);
    }

}
