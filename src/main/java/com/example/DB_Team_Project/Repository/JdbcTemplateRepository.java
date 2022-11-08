package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateRepository implements OperationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
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
        String whereCondition = dto.getWhereCondition();
        String whereValue = dto.getWhereValue();
        if(!setCondition.equals("salary")){
            setValue = "\""+setValue+"\"";
        }
        if(!whereCondition.equals("salary")){
            whereValue = "\""+whereValue+"\"";
        }
        sql+=setCondition+"="+setValue+" where "+whereCondition + " = "+whereValue;
        return jdbcTemplate.update(sql);

    }
}
