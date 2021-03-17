package jdbcpackage;


import com.veracity.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/veracitydb","root","mypassword");
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate("insert into employee values (101,'Sam',5000)");
        result = statement.executeUpdate("insert into employee values (102,'Liam',7000)");
        result = statement.executeUpdate("insert into employee values (103,'Ram',8000)");
        ResultSet resultset = statement.executeQuery("select * from employee");
        List<Employee> employeeList = new ArrayList<>();
        while(resultset.next()){
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            int salary = resultset.getInt("salary");
            employeeList.add(new Employee(id,name,salary));
        }
        display(employeeList);
        connection.close();
    }
    public static void display(List<Employee> list){
        System.out.println("Id"+"  "+"Name"+"  "+"Salary");
        for (Employee employee:list) {
            System.out.println(employee.getId()+" "+employee.getName()+"  "+employee.getSalary());
        }

    }
}