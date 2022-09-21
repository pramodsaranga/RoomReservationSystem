package controller.main;

import db.DbConnection;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    public boolean addEmployee(Employee em) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Employee VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setObject(1,em.getEmployeeId());
        pst.setObject(2,em.getEmployeeName());
        pst.setObject(3,em.getEmployeeAddress());
        pst.setObject(4,em.getNic());
        pst.setObject(5,em.getBirthday());
        pst.setObject(6,em.getContact());
        pst.setObject(7,em.getPost());
        return pst.executeUpdate()>0;
    }

    public static List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst= con.prepareStatement("SELECT * FROM Employee");
        ResultSet rst = pst.executeQuery();
        List<Employee> employee= new ArrayList<>();

        while (rst.next()){
            employee.add(new Employee(
                    rst.getString("empId"),
                    rst.getString("empName"),
                    rst.getString("address"),
                    rst.getString("NIC"),
                    rst.getString("birthday"),
                    rst.getString("contact"),
                    rst.getString("post")
            ));
        }
        return employee;
    }

    public boolean modifyEmployee(Employee em) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Employee SET empName=?,address=?,NIC=?,birthday=?,contact=?,post=? WHERE empId=?");
        pst.setObject(1,em.getEmployeeName());
        pst.setObject(2,em.getEmployeeAddress());
        pst.setObject(3,em.getNic());
        pst.setObject(4,em.getBirthday());
        pst.setObject(5,em.getContact());
        pst.setObject(6,em.getPost());
        pst.setObject(7,em.getEmployeeId());
        return pst.executeUpdate()>0;
    }

    public static Employee getEmployee(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Employee WHERE empId=?");
        pst.setObject(1,id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            return new Employee(
                rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }else {
            return null;
        }
    }

    public static boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Delete From Employee Where employeeId=?");
        pst.setObject(1,id);
        return pst.executeUpdate()>0;
    }
}
