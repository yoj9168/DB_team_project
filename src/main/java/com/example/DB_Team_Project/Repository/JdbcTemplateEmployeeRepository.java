package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcTemplateEmployeeRepository implements EmployeeRepository{

    private final JdbcTemplate jdbcTemplate;
    private boolean check[];
    private String[] attributes;
    private String selectRange;
    private String search;
    private String where;

    @Autowired
    public JdbcTemplateEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> find(EmployeeDto dto) {
        selectRange = dto.getSelectRange();
        where = checkCommand(selectRange, dto);

        System.out.println(dto.getAttribute());
        System.out.println("where : "+where);


        check = new boolean[]{false,false,false,false,false,false,false,false,false,false};
        attributes= new String[]{"ssn", "fname", "lname","minit","bdate", "address", "sex", "salary", "super_ssn", "dno"};
        String select = "a.ssn, a.fname, a.lname, a.minit, a.bdate, a.address, a.sex, a.salary, c.fname, c.lname, c.minit, b.dname";

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < dto.getAttribute().size(); j++){
                if(attributes[i].equals(dto.getAttribute().get(j)))
                    check[i] = true;
            }
        }
        System.out.println("select "+select+" from (employee as a join department as b on dnumber = dno) join employee as c on a.super_ssn=c.ssn " +
                where);
        return jdbcTemplate.query("select "+select+" from (employee as a join department as b on dnumber = dno) join employee as c on a.super_ssn=c.ssn " +
                where,employeeRowMapper());
    }

    private String checkCommand(String selectRange, EmployeeDto dto) {
        String where="where ";
        if(selectRange.equals("dname")){
            selectRange = "b.dname";
//            selectRange = "";
//            selectRange+="b."+ dto.getSelectRange();
            search = "\""+dto.getSearch()+"\"";
            where+=selectRange+"="+search;
            return where;
        }
        else if(selectRange.equals("default")){
            where="";
            return where;
        }
        else if(selectRange.equals("sex")){
            selectRange="a.sex";
//            selectRange="";
//            selectRange+="a."+dto.getSelectRange();
            search = "\""+dto.getSearch()+"\"";
            where+=selectRange+"="+search;
            return where;
        }
        else if(selectRange.equals("salary")){
            selectRange="a.salary";
//            selectRange="";
//            selectRange+="a."+dto.getSelectRange();
            search = dto.getSearch();
            where+=selectRange+">"+search;
            return where;
        }
        else if(selectRange.equals("bdate")){
//            selectRange="";
//            selectRange+="a."+dto.getSelectRange();
            selectRange="a.bdate";
            search=dto.getSearch();
            where+=selectRange+" LIKE " + "\""+"_____"+search+"___"+"\"";
            return where;
        }

        else if(selectRange.equals("ssn")){
            selectRange="c.Fname";
            search=dto.getSearch();
            where+=selectRange+"="+"\""+search+"\"";
            return where;
        }
        return null;
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