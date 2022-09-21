package controller;

import com.jfoenix.controls.JFXTextField;
import controller.main.BookingController;
import controller.main.MealPlanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
import view.tm.MealPlanTm;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MealPlanFormController {
    public ComboBox<String> cmbMealPlanType;
    public Label lblMealPlanId;
    public ComboBox<String> cmbBookingId;
    public JFXTextField txtRoomId;
    public JFXTextField txtGuestId;
    public JFXTextField txtCheckingInDate;
    public JFXTextField txtCheckingOutDate;
    public JFXTextField txtCheckingInTime;
    public JFXTextField txtCheckingOutTime;
    public JFXTextField txtCost;
    public JFXTextField txtPrice;
    public TextField txtMealPlanPrice;
    public TextField txtExtraPrice;
    public TableView<MealPlanTm> tblMealPlan;
    public TableColumn colBookingId;
    public TableColumn colMealPlanType;
    public TableColumn colTotalPrice;
    public TableColumn colMealPlanId;
    public Label lblTotalCost;
    public Label lblDates;
    public AnchorPane melaPlanContext;

    ObservableList<MealPlanTm> tableData =FXCollections.observableArrayList();
    private static final ArrayList<String> MealPlanType = new ArrayList<>();

    static {
        MealPlanType.add("BB");
        MealPlanType.add("HB");
        MealPlanType.add("FB");
    }


    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(MealPlanType);
        cmbMealPlanType.setItems(obList);

        lastMealPlanId();
        loadTableData();
        loadBookingIds();


        colMealPlanId.setCellValueFactory(new PropertyValueFactory<>("mealPlanId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colMealPlanType.setCellValueFactory(new PropertyValueFactory<>("mealPlanType"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));


        cmbBookingId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setBookingData( newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });



        cmbMealPlanType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setMealPlanTypePriceData( newValue);
        });

        tblMealPlan.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getTableData(newValue);
        });
    }

    public void lastMealPlanId(){
        try {
            String mealPlanId = MealPlanController.getLastMealPlanId();
            String finalId = "MP-001";

            if (mealPlanId != null) {

                String[] splitString = mealPlanId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "MP-00" + id;
                } else if (id < 100) {
                    finalId = "MP-0" + id;
                } else {
                    finalId = "MP-" + id;
                }
                lblMealPlanId.setText(finalId);
            } else {
                lblMealPlanId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadBookingIds() throws SQLException, ClassNotFoundException {
        List<String> bookingIds = new MealPlanController().getAllBookingIds();
        cmbBookingId.getItems().addAll(bookingIds);
    }

    private void setBookingData(String bookingId) throws SQLException, ClassNotFoundException {
        BookingDetails bd1= new MealPlanController().getBooking(bookingId);
        if (bd1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtRoomId.setText(bd1.getRoomId());
            txtGuestId.setText(bd1.getGuestId());
            txtCheckingInDate.setText(bd1.getCheckingInDate());
            txtCheckingInTime.setText(bd1.getCheckingInTime());
            txtCheckingOutDate.setText(bd1.getCheckingOutDate());
            txtCheckingOutTime.setText(bd1.getCheckingOutTime());
            txtCost.setText(String.valueOf(bd1.getCost()));
        }
    }

    public  void setMealPlanTypePriceData(String mealType){
        if(mealType.equalsIgnoreCase("BB")){
            txtMealPlanPrice.setText("5000.00");
            CalculateDates();
        }else if(mealType.equalsIgnoreCase("HB")){
            txtMealPlanPrice.setText("10000.00");
            CalculateDates();
        }else if(mealType.equalsIgnoreCase("FB")){
            txtMealPlanPrice.setText("15000.00");
            CalculateDates();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<MealPlan> allMeals = MealPlanController.getAllMealPlan();
        ArrayList<MealPlanTm> mealPlanTms =new ArrayList<>();
        for (MealPlan m : allMeals){
            mealPlanTms.add(new MealPlanTm(
                    m.getMealPlanId(),
                    m.getBookingId(),
                    m.getGuestId(),
                    m.getMealPlanType(),
                    m.getMealPlanPrice(),
                    m.getTotal()
            ));
            tableData.clear();
            tableData.addAll(mealPlanTms);
            tblMealPlan.setItems(tableData);
        }
    }

    public void getTableData(MealPlanTm mpt){
        try{
            String id=tblMealPlan.getSelectionModel().getSelectedItem().getMealPlanId();
            String bookingId=tblMealPlan.getSelectionModel().getSelectedItem().getBookingId();

           MealPlan  mp= MealPlanController.getMealPlans(id);
            BookingDetails b1 = new BookingController().getBookings(bookingId);
            if (mp==null){
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }else {
                setData(mp,b1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void setData(MealPlan m,BookingDetails bd){
        lblMealPlanId.setText(m.getMealPlanId());
        cmbBookingId.setValue(bd.getBookingId());
        txtRoomId.setText(bd.getRoomId());
        txtGuestId.setText(bd.getGuestId());
        txtCheckingInDate.setText(bd.getCheckingInDate());
        txtCheckingInTime.setText(bd.getCheckingInTime());
        txtCheckingOutDate.setText(bd.getCheckingOutDate());
        txtCheckingOutTime.setText(bd.getCheckingOutTime());
        txtCost.setText(String.valueOf(bd.getCost()));
        cmbMealPlanType.setValue(m.getMealPlanType());
        txtMealPlanPrice.setText(String.valueOf(m.getMealPlanPrice()));

    }

    public void CalculateDates(){

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String checkIn = txtCheckingInDate.getText();
        String checkOut = txtCheckingOutDate.getText();
        Double price=Double.parseDouble( txtMealPlanPrice.getText());
        System.out.println(checkOut);
        System.out.println(checkIn);

        if ((!checkOut.equals("") && !checkIn.equals(""))) {
            try {
                Date date1 = myFormat.parse(checkIn);
                Date date2 = myFormat.parse(checkOut);
                long diff = date2.getTime() - date1.getTime();
                System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                String dates=(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                lblDates.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                int datesIn=Integer.parseInt(dates);
                Double tot=price*datesIn;
                lblTotalCost.setText(String.valueOf(tot));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMealPlanOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Double price=Double.parseDouble( txtMealPlanPrice.getText());

        Double total=price;

        MealPlan m=new MealPlan(
                lblMealPlanId.getText(),
                cmbBookingId.getSelectionModel().getSelectedItem(),
                txtGuestId.getText(),
                cmbMealPlanType.getSelectionModel().getSelectedItem(),
                Double.parseDouble(txtMealPlanPrice.getText()),total
        );

        if (new MealPlanController().addMealPlan(m)){
            new Alert(Alert.AlertType.CONFIRMATION,"Meal Plan Success..").show();
            loadTableData();
            lastMealPlanId();
            cmbBookingId.setValue("");
            txtRoomId.clear();
            txtMealPlanPrice.clear();
            txtPrice.clear();
            txtCost.clear();
            txtGuestId.clear();
            txtCheckingInDate.clear();
            txtCheckingInTime.clear();
            txtExtraPrice.clear();
            txtCheckingOutTime.clear();
            txtCheckingOutDate.clear();
            cmbMealPlanType.setValue("");
            lblDates.setText("0");

        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
    }

    public void updateMealPlanOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double price=Double.parseDouble( txtMealPlanPrice.getText());
        Double total=price;

        MealPlan mp = new MealPlan(
                lblMealPlanId.getText(),cmbBookingId.getSelectionModel().getSelectedItem(),
                txtGuestId.getText(),cmbMealPlanType.getSelectionModel().getSelectedItem(),
                Double.parseDouble(txtMealPlanPrice.getText()),total
        );

        if (new MealPlanController().modifyMealPlan(mp)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            loadTableData();
            cmbBookingId.setValue("");
            txtRoomId.clear();
            txtMealPlanPrice.clear();
            txtPrice.clear();
            txtCost.clear();
            txtGuestId.clear();
            txtCheckingInDate.clear();
            txtCheckingInTime.clear();
            txtExtraPrice.clear();
            txtCheckingOutTime.clear();
            txtCheckingOutDate.clear();
            cmbMealPlanType.setValue("");
            lblDates.setText("0");
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void removeMealPlanOnAction(ActionEvent actionEvent) {
        try {
            String id = tblMealPlan.getSelectionModel().getSelectedItem().getMealPlanId();
            boolean isDelete;
            isDelete = MealPlanController.deleteMealPlan(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Done..").show();
                loadTableData();
                cmbBookingId.setValue("");
                txtRoomId.clear();
                txtMealPlanPrice.clear();
                txtPrice.clear();
                txtCost.clear();
                txtGuestId.clear();
                txtCheckingInDate.clear();
                txtCheckingInTime.clear();
                txtExtraPrice.clear();
                txtCheckingOutTime.clear();
                txtCheckingOutDate.clear();
                cmbMealPlanType.setValue("");
                lblDates.setText("0");
            }else {
                new Alert(Alert.AlertType.ERROR,"Error..").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
