package controller.main;

import db.DbConnection;
import model.BookingDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    public static String getLastBookingId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT bookingId FROM BookingDetails ORDER BY bookingId DESC LIMIT 1");
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getString("bookingId");
        }
        return null;
    }

    public boolean addBookingDetails(BookingDetails bd) throws SQLException, ClassNotFoundException {
      Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement pst = con.
                    prepareStatement("INSERT INTO BookingDetails VALUES(?,?,?,?,?,?,?,?)");
            pst.setObject(1,bd.getBookingId());
            pst.setObject(2,bd.getRoomId());
            pst.setObject(3,bd.getGuestId());
            pst.setObject(4,bd.getCheckingInDate());
            pst.setObject(5,bd.getCheckingInTime());
            pst.setObject(6,bd.getCheckingOutDate());
            pst.setObject(7,bd.getCheckingOutTime());
            pst.setObject(8,bd.getCost());
            if (pst.executeUpdate() > 0) {

                    con.commit();
                    return true;

            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public static List<BookingDetails> getAllBookings() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM BookingDetails");
        ResultSet rst= pst.executeQuery();

        List<BookingDetails> bookings= new ArrayList<>();

        while (rst.next()){
            bookings.add(new BookingDetails(
                    rst.getString("bookingId"),
                    rst.getString("roomId"),
                    rst.getString("CustomerId"),
                    rst.getString("checkingIn"),
                    rst.getString("checkingInTime"),
                    rst.getString("checkingOut"),
                    rst.getString("checkingOutTime"),
                    rst.getDouble("cost")
            ));
        }
        return bookings;
    }

    public static BookingDetails getBookings(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM BookingDetails WHERE bookingId=?");
        pst.setObject(1,id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            System.out.println(rst.getString(1));
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

    public boolean modifyBooking(BookingDetails bd) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "UPDATE BookingDetails SET roomId=?,guestId=?,checkingIn=?,checkingInTime=?,checkingOut=?,checkingOutTime=?,cost=? WHERE bookingId=?";

        PreparedStatement pst= con.prepareStatement(query);
        pst.setObject(1,bd.getRoomId());
        pst.setObject(2,bd.getGuestId());
        pst.setObject(3,bd.getCheckingInDate());
        pst.setObject(4,bd.getCheckingInTime());
        pst.setObject(5,bd.getCheckingOutDate());
        pst.setObject(6,bd.getCheckingOutTime());
        pst.setObject(7,bd.getCost());
        pst.setObject(8,bd.getBookingId());
        return pst.executeUpdate()>0;
    }

    public static boolean deleteBooking(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM BookingDetails WHERE bookingId=?");
        pst.setObject(1,id);
        return pst.executeUpdate()>0;
    }

}
