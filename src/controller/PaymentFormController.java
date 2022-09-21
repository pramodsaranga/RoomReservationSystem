package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.main.PaymentController;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.BookingDetails;
import model.MealPlan;
import model.Payment;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.PaymentTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PaymentFormController {
    public ComboBox<String> cmbBookingId;
    public JFXTextField txtRoomId;
    public JFXTextField txtGuestId;
    public JFXTextField txtCheckingInDate;
    public JFXTextField txtCheckingInTime;
    public JFXTextField txtCheckingOutDate;
    public JFXTextField txtCheckingOutTime;
    public JFXTextField txtBookingCost;
    public JFXTextField txtMealPlanId;
    public JFXTextField txtMealPlanType;
    public JFXTextField txtMealPlanPrice;
    public TableView tblPaymentDetails;
    public TableColumn colBillId;
    public TableColumn colBookingCost;
    public TableColumn colMealPlanCost;
    public TableColumn colDate;
    public TableColumn colTotal;
    public Label lblTotal;
    public Label lblBillId;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnPrint;

    ObservableList<PaymentTm> tableData = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        lastBillId();
        loadBookingIds();
        loadDateAndTime();
        loadTableData();

        colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colBookingCost.setCellValueFactory(new PropertyValueFactory<>("bookingCost"));
        colMealPlanCost.setCellValueFactory(new PropertyValueFactory<>("mealPlanCost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbBookingId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setBookingData( newValue);
                        CalculateTotal();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });
    }

    public void lastBillId(){
        try {
            String paymentId = PaymentController.getLastBillId();
            String finalId = "Bill-001";

            if (paymentId != null) {

                String[] splitString = paymentId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "Bill-00" + id;
                } else if (id < 100) {
                    finalId = "Bill-0" + id;
                } else {
                    finalId = "Bill-" + id;
                }
                lblBillId.setText(finalId);
            } else {
                lblBillId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void loadBookingIds() throws SQLException, ClassNotFoundException {
        List<String> bookingIds = new PaymentController().getAllBookingIds();
        cmbBookingId.getItems().addAll(bookingIds);
    }

    private void setBookingData(String bookingId) throws SQLException, ClassNotFoundException {
        BookingDetails bd1 = new PaymentController().getBookings(bookingId);
        MealPlan m=new PaymentController().getMealPlans(bookingId);
        if (bd1 == null && m==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtRoomId.setText(bd1.getRoomId());
            txtGuestId.setText(bd1.getGuestId());
            txtCheckingInDate.setText(bd1.getCheckingInDate());
            txtCheckingInTime.setText(bd1.getCheckingInTime());
            txtCheckingOutDate.setText(bd1.getCheckingOutDate());
            txtCheckingOutTime.setText(bd1.getCheckingOutTime());
            txtBookingCost.setText(String.valueOf(bd1.getCost()));
            txtMealPlanId.setText(m.getMealPlanId());
            txtMealPlanType.setText(m.getMealPlanType());
            txtMealPlanPrice.setText(String.valueOf(m.getTotal()));
        }
    }

    public  void CalculateTotal(){
        Double bookingCost=Double.parseDouble(txtBookingCost.getText());
        Double mealCost=Double.parseDouble(txtMealPlanPrice.getText());
        Double total =bookingCost+mealCost;
        lblTotal.setText(String.valueOf(total));
    }

    public void onPrintBill(MouseEvent mouseEvent) {
    }

    public void onPayment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Payment p=new Payment(
                lblBillId.getText(),txtBookingCost.getText(),txtMealPlanPrice.getText(),lblDate.getText(),
                Double.parseDouble(lblTotal.getText())
        );
        if (new PaymentController().addPayment(p)){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment Success..").show();
            loadTableData();
            lastBillId();

    }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<Payment> allPayments = PaymentController.getAllPayments();
        ArrayList<PaymentTm> payments =new ArrayList<>();
        for (Payment p : allPayments){
            payments.add(new PaymentTm(
                    p.getBillId(),
                    p.getBookingCost(),
                    p.getMealPlanCost(),
                    p.getDate(),
                    p.getTotal()
            ));
            tableData.clear();
            tableData.addAll(payments);
            tblPaymentDetails.setItems(tableData);
        }
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Report/PaymentDetailReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Here we can set the DBConnection to the data source because this report use mysql to get data*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
