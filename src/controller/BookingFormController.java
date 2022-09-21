package controller;

import com.jfoenix.controls.JFXTextField;
import controller.main.BookingController;
import controller.main.CustomerController;
import controller.main.RoomController;
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
import model.Customer;
import model.Room;
import view.tm.BookingDetailsTm;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookingFormController {
    public JFXTextField txtGuestName;
    public JFXTextField txtGuestAddress;
    public JFXTextField txtNIC;
    public JFXTextField txtContact;
    public ComboBox<String> cmbRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtCost;
    public TableView<BookingDetailsTm> tblBookingDetails;
    public TableColumn colBookingId;
    public TableColumn colRoomId;
    public TableColumn colGuestId;
    public TableColumn colCheckingInDate;
    public TableColumn colCheckingInTime;
    public TableColumn colCheckingOutDate;
    public TableColumn colCheckingOutTime;
    public TableColumn colCost;
    public TextField txtGuestId;
    public Label lblBookingId;
    public Label lblBookingNum;
    public Label lblDate;
    public Label lblTime;
    public Label lblCheckingOutTime;
    public DatePicker datePickerOut;
    public Label lblTotalCost;

    ObservableList<BookingDetailsTm> tableData = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        lastBookingId();
        loadDateAndTime();
        loadTableData();

        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colCheckingInDate.setCellValueFactory(new PropertyValueFactory<>("checkingInDate"));
        colCheckingInTime.setCellValueFactory(new PropertyValueFactory<>("checkingInTime"));
        colCheckingOutDate.setCellValueFactory(new PropertyValueFactory<>("checkingOutDate"));
        colCheckingOutTime.setCellValueFactory(new PropertyValueFactory<>("checkingOutTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        loadRoomIds();

        cmbRoomId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setRoomData( newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });

        tblBookingDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getTableData(newValue);
        });
    }

    public void lastBookingId(){
        try {
            String bookId = BookingController.getLastBookingId();
            String finalId = "B-001";

            if (bookId != null) {

                String[] splitString = bookId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "B-00" + id;
                } else if (id < 100) {
                    finalId = "B-0" + id;
                } else {
                    finalId = "B-" + id;
                }
                lblBookingNum.setText(finalId);
            } else {
                lblBookingNum.setText(finalId);
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

    public void setData(Customer g) {
        txtGuestId.setText(g.getGuestId());
        txtGuestName.setText(g.getName());
        txtGuestAddress.setText(g.getAddress());
        txtNIC.setText(g.getNic());
        txtContact.setText(g.getContact());
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<BookingDetails> allBookings = BookingController.getAllBookings();
        ArrayList<BookingDetailsTm> bookingDetails =new ArrayList<>();
        for (BookingDetails bd : allBookings){
            bookingDetails.add(new BookingDetailsTm(
                    bd.getBookingId(),
                    bd.getRoomId(),
                    bd.getGuestId(),
                    bd.getCheckingInDate(),
                    bd.getCheckingInTime(),
                    bd.getCheckingOutDate(),
                    bd.getCheckingOutTime(),
                    bd.getCost()
            ));
            tableData.clear();
            tableData.addAll(bookingDetails);
            tblBookingDetails.setItems(tableData);
            System.out.println("tableCall");
        }
    }

    private void loadRoomIds() throws SQLException, ClassNotFoundException {
        List<String> roomIds = new RoomController().getAllRoomsIds();
        cmbRoomId.getItems().addAll(roomIds);
    }


    public void guestDetailsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String guestId = txtGuestId.getText();

        Customer g1 = new CustomerController().getGuest(guestId);
        if (g1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
        }else {
            setData(g1);
        }
    }

    private void setRoomData(String roomId) throws SQLException, ClassNotFoundException {
       Room r1 = new RoomController().getRooms(roomId);
        if (r1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtRoomType.setText(r1.getRoomType());
            txtCost.setText(String.valueOf(r1.getPrice()));
        }
    }

    public void getTableData(BookingDetailsTm b){
        try{
            String id=tblBookingDetails.getSelectionModel().getSelectedItem().getBookingId();
            String guestId=tblBookingDetails.getSelectionModel().getSelectedItem().getGuestId();

            BookingDetails b1= BookingController.getBookings(id);
            Room r1 = new RoomController().getRooms(id);
            Customer g1 = new CustomerController().getGuest(guestId);
            if (b==null){
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }else {
                setData(b1,r1,g1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void setData(BookingDetails b, Room r, Customer g){
        lblBookingNum.setText(b.getBookingId());
        txtGuestId.setText(b.getGuestId());
        cmbRoomId.setValue(b.getRoomId());
        txtCost.setText(String.valueOf(b.getCost()));
        txtGuestName.setText(g.getName());
        txtGuestAddress.setText(g.getAddress());
        txtNIC.setText(g.getNic());
        txtContact.setText(g.getNic());
        txtRoomType.setText(r.getRoomType());
    }

    public void CalculateDates(){
        LocalDate date=datePickerOut.getValue();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String checkOut = date.toString();
        String checkIn = lblDate.getText();
       Double bookingCost=Double.parseDouble(txtCost.getText());
        System.out.println(checkOut);
        System.out.println(checkIn);
        if ((!checkOut.equals("") && !checkIn.equals(""))) {
            try {
                Date date1 = myFormat.parse(checkIn);
                Date date2 = myFormat.parse(checkOut);
                long diff = date2.getTime() - date1.getTime();
                System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                String dates=(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                int datesIn=Integer.parseInt(dates);
                Double tot=bookingCost*datesIn;
                lblTotalCost.setText(String.valueOf(tot));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void CalTot(MouseEvent mouseEvent) {
        CalculateDates();
    }

    public void addBookingOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LocalDate date=datePickerOut.getValue();
        BookingDetails b = new BookingDetails(
                lblBookingNum.getText(),cmbRoomId.getSelectionModel().getSelectedItem(),txtGuestId.getText(),lblDate.getText(),lblTime.getText(),
                date.toString(),lblCheckingOutTime.getText(),Double.parseDouble(lblTotalCost.getText())
        );
        if (new BookingController().addBookingDetails(b)){
            new Alert(Alert.AlertType.CONFIRMATION,"Booking Success..").show();
            loadTableData();
            lastBookingId();
            cmbRoomId.setValue("");
            txtGuestId.clear();
            lblTotalCost.setText("0");
            txtGuestName.clear();
            txtGuestAddress.clear();
            txtCost.clear();
            txtNIC.clear();
            txtContact.clear();
            txtRoomType.clear();

        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
    }
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LocalDate date=datePickerOut.getValue();
        BookingDetails b=new BookingDetails(
                lblBookingNum.getText(),cmbRoomId.getSelectionModel().getSelectedItem(),txtGuestId.getText(),
                lblDate.getText(),lblTime.getText(),date.toString(),lblCheckingOutTime.getText(),
                Double.parseDouble(lblTotalCost.getText())
        );

        if (new BookingController().modifyBooking(b)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            loadTableData();
            cmbRoomId.setValue("");
            txtGuestId.clear();
            lblTotalCost.setText("");
            txtGuestName.clear();
            txtGuestAddress.clear();
            txtCost.clear();
            txtNIC.clear();
            txtContact.clear();
            txtRoomType.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void removeOnAction(ActionEvent actionEvent) {
        try {
            String id = tblBookingDetails.getSelectionModel().getSelectedItem().getBookingId();
            boolean isDelete;
            isDelete = BookingController.deleteBooking(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Done..").show();

                cmbRoomId.setValue("");
                txtGuestId.clear();
                lblTotalCost.setText("");
                txtGuestName.clear();
                txtGuestAddress.clear();
                txtCost.clear();
                txtNIC.clear();
                txtContact.clear();
                txtRoomType.clear();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error..").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
