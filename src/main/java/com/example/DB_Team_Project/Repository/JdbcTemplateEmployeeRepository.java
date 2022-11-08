package com.example.DB_Team_Project.Repository;

import com.example.DB_Team_Project.Employee.Employee;
import com.example.DB_Team_Project.Employee.EmployeeDeleteDto;
import com.example.DB_Team_Project.Employee.EmployeeSelectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
@Repository
public class JdbcTemplateEmployeeRepository implements EmployeeRepository{

    private final JdbcTemplate jdbcTemplate;
    private boolean check[];
    private String[] attributes= new String[]{"ssn", "fname", "lname","minit","bdate", "address", "sex", "salary", "super_ssn", "dno"};
    ;
    private String selectRange;
    private String search;
    private String where;
    private String from = " from (employee as a left outer join department as b on dnumber = dno) left outer join employee as c on a.super_ssn=c.ssn ";
    private String select = "a.ssn, a.fname, a.lname, a.minit, a.bdate, a.address, a.sex, a.salary, c.fname, c.lname, c.minit, b.dname";


    @Autowired
    public JdbcTemplateEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> find(EmployeeSelectDto dto) {
        selectRange = dto.getSelectRange();
        where = checkCommand(selectRange, dto);

        System.out.println(dto.getAttribute());
        System.out.println("where : "+where);


        check = new boolean[]{false,false,false,false,false,false,false,false,false,false};

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < dto.getAttribute().size(); j++){
                if(attributes[i].equals(dto.getAttribute().get(j)))
                    check[i] = true;
            }
        }
        System.out.println("select "+select+from+where);
        return jdbcTemplate.query("select " + select + from + where,employeeRowMapper());
    }

    @Override
    public int selectCount(EmployeeSelectDto dto) {
        selectRange = dto.getSelectRange();
        String where = checkCommand(selectRange, dto);
        System.out.println("select count(*) "+ from +where);
        int rowCount = jdbcTemplate.queryForObject("select count(*) "+ from +where,Integer.class);
        return rowCount;
    }

    @Override
    public int delete(EmployeeDeleteDto dto) {
        String where = "";

        String sql = "delete employee where "+where;
        return jdbcTemplate.update(sql);
    }

    private String checkCommand(String selectRange, EmployeeSelectDto dto) {
        String where="where ";
        if(selectRange.equals("dname")){
            selectRange = "b.dname";
            search=dto.getSearch();
            search = "\""+search+"\"";
            where+=selectRange+"="+search;
            return where;
        }
        else if(selectRange.equals("default")){
            where="";
            return where;
        }
        else if(selectRange.equals("sex")){
            selectRange="a.sex";
            search = "\""+dto.getSearch()+"\"";
            where+=selectRange+"="+search;
            return where;
        }
        else if(selectRange.equals("salary")){
            selectRange="a.salary";
            search = dto.getSearch();
            where+=selectRange+">"+search;
            return where;
        }
        else if(selectRange.equals("bdate")){
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

    public List<String> getDepartment() {
        return jdbcTemplate.query("select distinct dname from employee, department where dno=dnumber", (ResultSet rs, int rowNum)->{
            String string = "";
            string+=rs.getString("Dname");
            return string;
        });
    }

    public List<String> getSex() {
        return jdbcTemplate.query("select distinct sex from employee", (ResultSet rs, int rowNum)->{
            String string = "";
            string+=rs.getString("sex");
            return string;
        });
    }
    public List<String> getName(){
        return jdbcTemplate.query("select distinct fname from employee", (ResultSet rs, int rowNum)->{
            String string = "";
            string+=rs.getString("fname");
            return string;
        });
    }
}