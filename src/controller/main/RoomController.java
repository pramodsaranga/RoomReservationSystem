package controller.main;

import db.DbConnection;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    //---------------  Add New Room ---------------------
    public boolean addRoom(Room m) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query= "INSERT INTO Room VALUES(?,?,?,?)";
        PreparedStatement pst= con.prepareStatement(query);
        pst.setObject(1,m.getRoomId());
        pst.setObject(2,m.getRoomType());
        pst.setObject(3,m.getFloor());
        pst.setObject(4,m.getPrice());
        return pst.executeUpdate()>0;
    }

    public static List<Room> getAllRooms() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pst = con.prepareStatement("SELECT * FROM Room");
        ResultSet rst= pst.executeQuery();

        List<Room> rooms= new ArrayList<>();

        while (rst.next()){
            rooms.add(new Room(
                    rst.getString("roomId"),
                    rst.getString("roomType"),
                    rst.getString("floor"),
                    rst.getDouble("price")
            ));
        }
        return rooms;
    }

    //---------------- Modify Room -------------------------
    public boolean modifyRoom(Room mr) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "UPDATE Room SET roomType=?,floor=?,price=? WHERE roomId=?";

        PreparedStatement pst= con.prepareStatement(query);
        pst.setObject(1,mr.getRoomType());
        pst.setObject(2,mr.getFloor());
        pst.setObject(3,mr.getPrice());
        pst.setObject(4,mr.getRoomId());
        return pst.executeUpdate()>0;
    }

    public static Room getRooms(String Id) throws SQLException, ClassNotFoundException {
        PreparedStatement pst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room WHERE roomId=?");
        pst.setObject(1,Id);

        ResultSet rst= pst.executeQuery();
        if (rst.next()){
            return new Room(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            );
        }else{
            return  null;
        }
    }

    public List<String> getAllRoomsIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Room").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public static boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("DELETE FROM Room WHERE roomId=?");
        pst.setObject(1,id);
        return pst.executeUpdate()>0;
    }
}
