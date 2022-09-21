package controller.main;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {
    public static String getLastBillId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT billId FROM Payment ORDER BY billId DESC LIMIT 1");
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getString("billId");
        }
        return null;
    }

    public boolean addPayment(Payment p) throws SQLException, ClassNotFoundException {
        //System.out.println(m.getRoomId());
        Connection con = DbConnection.getInstance().getConnection();
        String query= "INSERT INTO Payment VALUES(?,?,?,?,?)";
        PreparedStatement pst= con.prepareStatement(query);
        pst.setObject(1,p.getBillId());
        pst.setObject(2,p.getBookingCost());
        pst.setObject(3,p.getMealPlanCost());
        pst.setObject(4,p.getDate());
        pst.setObject(5,p.getTotal());
        return pst.executeUpdate()>0;
    }

    public static List<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Payment");
        ResultSet rst= pst.executeQuery();

        List<Payment> payments= new ArrayList<>();

        while (rst.next()){
            payments.add(new Payment(
                    rst.getString("billId"),
                    rst.getString("bookingCost"),
                    rst.getString("mealPlanCost"),
                    rst.getString("billDate"),
                    rst.getDouble("total")
            ));
        }
        return payments;
    }

    public List<String> getAllBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM BookingDetails").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public BookingDetails getBookings(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM BookingDetails WHERE bookingId=?");
        pst.setObject(1,Id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            return new BookingDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8)
            );
        }else{
            return  null;
        }
    }

    public MealPlan getMealPlans(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM MealPlan WHERE bookingId=?");
        pst.setObject(1,Id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            return new MealPlan(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)

            );
        }else{
            return  null;
        }
    }

}
