package controller.main;

import db.DbConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public boolean addCustomer(Customer gu) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setObject(1,gu.getGuestId());
        pst.setObject(2,gu.getName());
        pst.setObject(3,gu.getAddress());
        pst.setObject(4,gu.getNic());
        pst.setObject(5,gu.getContact());
        return pst.executeUpdate()>0;
    }

    public static List<Customer> getAllGuests() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Customer");
        ResultSet rst = pst.executeQuery();
         List<Customer> customer = new ArrayList<>();

         while (rst.next()){
             customer.add(new Customer(
                     rst.getString("customerId"),
                     rst.getString("customerName"),
                     rst.getString("address"),
                     rst.getString("nic"),
                     rst.getString("contact")
             ));
         }
         return customer;
    }

    public boolean modifyGuest(Customer gs) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET customerName=?,address=?,nic=?,contact=? WHERE cutomerId=?");
        pst.setObject(1,gs.getName());
        pst.setObject(2,gs.getAddress());
        pst.setObject(3,gs.getNic());
        pst.setObject(4,gs.getContact());
        pst.setObject(5,gs.getGuestId());
        return pst.executeUpdate()>0;
    }

    public static Customer getGuest(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM Customer WHERE customerId=?");
        pst.setObject(1,id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else {
            return null;
        }
    }

    public static boolean deleteGuest(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM Customer WHERE customerId=?");
        pst.setObject(1,id);
        return pst.executeUpdate()>0;
    }
}
