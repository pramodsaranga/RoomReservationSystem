package controller.main;

import db.DbConnection;
import model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeController {
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> year = new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT YEAR(billDate) FROM Payment");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            if(isYearExists(rst.getString(1),year)){

            }else {
                year.add(rst.getString(1));
            }
        }
        return year;
    }

    private boolean isYearExists(String string , ArrayList<String> year){
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String>month= new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT MONTH(billDate) FROM Payment");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            if(isYearExists(rst.getString(1),month)){

            }else {
                month.add(rst.getString(1));
            }
        }
        return month;
    }


    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        ArrayList<String>dates= new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT DATE (billDate) FROM Payment");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            if(isYearExists(rst.getString(1),dates)){

            }else {
                dates.add(rst.getString(1));
            }
        }
        return dates;
    }

    public ArrayList<Payment> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<Payment> yearlyDetails = new ArrayList<>();

        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Payment WHERE YEAR(billDate) ='"+year+"'");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            Payment s = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            yearlyDetails.add(s);
        }
        return yearlyDetails;
    }

    public ArrayList<Payment> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<Payment> MonthlyDetails= new ArrayList<>();

        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Payment WHERE MONTH (billDate) ='"+month+"'");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            Payment s = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            MonthlyDetails.add(s);
        }
        return MonthlyDetails;
    }

    public ArrayList<Payment> getDailyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<Payment> DailyDetails= new ArrayList<>();

        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Payment WHERE DATE (billDate) ='"+month+"'");

        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
          Payment s = new Payment(
                   rst.getString(1),
                   rst.getString(2),
                   rst.getString(3),
                   rst.getString(4),
                   rst.getDouble(5)
                   );
            DailyDetails.add(s);
        }
        return DailyDetails;
    }
}
