package jdbcpackage;
import com.veracity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/veracitydb", "root", "mypassword");
        Statement statement = connection.createStatement();
        Scanner input = new Scanner(System.in);
        int userchoice=1;
        while(userchoice!=0) {
            recordsRowset(statement);
            System.out.println("Enter your choice :");
            System.out.println("1:For Insert, 2:For Update, 3:For Delete, 4.For Delete All, 5:For Select, 6:For Select All");
            int choice = input.nextInt();
            // int updateCount = statement.executeUpdate("delete from employee");
            switch (choice) {
                case 1:
                    System.out.println("enter employee id");
                    int id = input.nextInt();
                    System.out.println("enter employee name");
                    String name = input.next();
                    System.out.println("enter employee salary");
                    int salary = input.nextInt();
                    System.out.println("sal=" + salary);
                    int result = statement.executeUpdate("insert into employee values (" + id + ",'" + name + "'," + salary + ")");
                    if (result == 1)
                        System.out.println("Successfully inserted");
                    else
                        System.out.println("Insert fail");
                    recordsRowset(statement);
                    break;

                case 2:
                    System.out.println("enter id of the employee:");
                    id = input.nextInt();
                    System.out.println("enter the new salary:");
                    int newsalary = input.nextInt();
                    PreparedStatement ps = connection.prepareStatement("update employee set salary = ? where id = ?");
                    ps.setInt(1, newsalary);
                    ps.setInt(2, id);
                    result = ps.executeUpdate();
                    if (result == 1)
                        System.out.println("Successfully updated the salary");
                    else
                        System.out.println("Update fail");
                    recordsRowset(statement);
                    break;

                case 3:
                    System.out.println("enter id of the employee:");
                    id = input.nextInt();
                    PreparedStatement psdel = connection.prepareStatement("delete from employee where id=?");
                    psdel.setInt(1, id);
                    result = psdel.executeUpdate();
                    if (result == 1)
                        System.out.println("Successfully deleted");
                    else
                        System.out.println("Delete fail");
                    recordsRowset(statement);
                    break;

                case 4:
                    PreparedStatement psdelall = connection.prepareStatement("delete from employee");
                    psdelall.executeUpdate();
                   /*result= qpsdelall.executeUpdate();
                     if (result == 1)
                        System.out.println("Successfully deleted all records");
                    else
                        System.out.println("delete all failed");*/
                    recordsRowset(statement);
                    break;

                case 5:
                    System.out.println("enter id of the employee:");
                    id = input.nextInt();
                    PreparedStatement pssel = connection.prepareStatement("select * from employee where id=?");
                    pssel.setInt(1, id);
                    ResultSet resultset = pssel.executeQuery();
                    while (resultset.next()) {
                        id = resultset.getInt("id");
                        name = resultset.getString("name");
                        salary = resultset.getInt("salary");
                        Employee e = new Employee(id, name, salary);
                        List al = new ArrayList();
                        al.add(e);
                        display(al);
                    }
                    break;

                case 6:
                    recordsRowset(statement);

                default:
                    System.out.println("choose 1 to 6");
            }
                boolean toggle=false;
            while(!toggle) {
                System.out.println("Do you want to continue: 1.Yes 0:NO");
                int userip = input.nextInt();
                if (userip == 1 || userip == 0) {
                    userchoice = userip;
                    toggle=true;
                }
                else {
                    System.out.println("enter 1 or 0");
                    toggle = false;
                }

            }
        }
            connection.close();

    }

    public static void recordsRowset(Statement statement)throws Exception
    {
        ResultSet resultset = statement.executeQuery("select * from employee");
        List<Employee> employeeList = new ArrayList<>();
        while(resultset.next()){
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            int salary = resultset.getInt("salary");
            employeeList.add(new Employee(id,name,salary));
        }
        display(employeeList);
    }

    public static void display(List<Employee> list){
        System.out.println("------Table Rowset------------");
        System.out.println("Id"+"  "+"Name"+"  "+"Salary");
        for (Employee employee:list) {
            System.out.println(employee.getId()+" "+employee.getName()+"  "+employee.getSalary());
        }
        System.out.println("------------------------------");
        }
    }
