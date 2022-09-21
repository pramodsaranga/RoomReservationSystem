package controller;

import com.jfoenix.controls.JFXComboBox;
import controller.main.IncomeController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeFormController {
    public JFXComboBox<String> cmbDate;
    public JFXComboBox <String>cmbMonth;
    public JFXComboBox <String>cmbYear;
    public TableView tblIncome;
    public TableColumn colId;
    public TableColumn colBookingCost;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colMealCost;
    public Label lblDailyTotal;
    public Label lblMonthlyTotal;
    public Label lblYearlyTotal;

    public void initialize() throws SQLException, ClassNotFoundException {

        loadDates();
        loadMonths();
        loadYears();

        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    ArrayList<Payment> yearlyDetails = new ArrayList<>();
                    try {

                        yearlyDetails = new IncomeController().getYearlyDetails(newValue);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    calculateIncome(yearlyDetails);

                    colId.setCellValueFactory(new PropertyValueFactory<>("billId"));
                    colBookingCost.setCellValueFactory(new PropertyValueFactory<>("bookingCost"));
                    colMealCost.setCellValueFactory(new PropertyValueFactory<>("mealPlanCost"));
                    colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                    colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

                    tblIncome.setItems(FXCollections.observableArrayList(yearlyDetails));

                });

            cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Payment> MonthlyDetails = new ArrayList<>();
                try {

                    MonthlyDetails = new IncomeController().getMonthlyDetails(newValue);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                calculateMonthIncome(MonthlyDetails);

                colId.setCellValueFactory(new PropertyValueFactory<>("billId"));
                colBookingCost.setCellValueFactory(new PropertyValueFactory<>("bookingCost"));
                colMealCost.setCellValueFactory(new PropertyValueFactory<>("mealPlanCost"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


                tblIncome.setItems(FXCollections.observableArrayList(MonthlyDetails));
            });

            cmbDate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Payment> DailyDetails = new ArrayList<>();
                try {

                    DailyDetails = new IncomeController().getDailyDetails(newValue);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                calculateDailyIncome(DailyDetails);

                colId.setCellValueFactory(new PropertyValueFactory<>("billId"));
                colBookingCost.setCellValueFactory(new PropertyValueFactory<>("bookingCost"));
                colMealCost.setCellValueFactory(new PropertyValueFactory<>("mealPlanCost"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

                tblIncome.setItems(FXCollections.observableArrayList(DailyDetails));
            });
        }

    private void loadYears() throws SQLException, ClassNotFoundException {
        List<String> years = new IncomeController()
                .getYears();
        cmbYear.getItems().addAll(years);
    }
    private void loadMonths() throws SQLException, ClassNotFoundException {
        List<String> months = new IncomeController()
                .getMonth();
        cmbMonth.getItems().addAll(months);
    }
    private void loadDates() throws SQLException, ClassNotFoundException {
        List<String> dates = new IncomeController()
                .getDates();
        cmbDate.getItems().addAll(dates);
    }

    private void calculateIncome(ArrayList<Payment> temp){
        double tPrice=0.0;
        for(Payment r : temp){
            tPrice+= r.getTotal();
        }
        lblYearlyTotal.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateMonthIncome(ArrayList<Payment> temp){
        double tPrice=0.0;
        for(Payment r : temp){
            tPrice+= r.getTotal();
        }
        lblMonthlyTotal.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateDailyIncome(ArrayList<Payment> temp){
        double tPrice=0.0;
        for(Payment r : temp){
            tPrice+= r.getTotal();
        }
        lblDailyTotal.setText(String.valueOf(tPrice)+" /=");
    }

    public void PrintDateIncome(ActionEvent actionEvent) {

    }

    public void PrintMonthlyReport(ActionEvent actionEvent) {

    }

    public void printYearlyReport(ActionEvent actionEvent) {

    }

    }
