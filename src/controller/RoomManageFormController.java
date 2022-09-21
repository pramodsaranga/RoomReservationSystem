package controller;

import controller.main.RoomController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Room;
import view.tm.RoomTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomManageFormController {
    public TextField txtRoomId;
    public ComboBox<String> cmbRoomType;
    public ComboBox<String> cmbFloor;
    public TableView<RoomTm> tblAddNewRoomDetails;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colFloor;
    public TableColumn colPrice;
    public TextField txtPrice;
    public AnchorPane roomManageContext;

    ObservableList<RoomTm> tableData =FXCollections.observableArrayList();
    private static final ArrayList<String> RoomType = new ArrayList<>();

    static {
        RoomType.add("Single");
        RoomType.add("Double");
        RoomType.add("Triple");
    }

    private static final ArrayList<String> Floor = new ArrayList<>();

    static {
        Floor.add("First Floor");
        Floor.add("Second Floor");
        Floor.add("Third Floor");
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(RoomType);
        cmbRoomType.setItems(obList);

        ObservableList<String> objList = FXCollections.observableList(Floor);
        cmbFloor.setItems(objList);

        loadTableData();

        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblAddNewRoomDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getTableData(newValue);
        });

    }

   public void loadTableData() throws SQLException, ClassNotFoundException {
        List<Room> allRooms = RoomController.getAllRooms();
        ArrayList<RoomTm> rooms =new ArrayList<>();
        for (Room r : allRooms){
            rooms.add(new RoomTm(
                    r.getRoomId(),
                    r.getRoomType(),
                    r.getFloor(),
                    r.getPrice()
            ));
            tableData.clear();
            tableData.addAll(rooms);
            tblAddNewRoomDetails.setItems(tableData);
        }
    }

    public void getTableData(RoomTm rt){
        try{
            String id=tblAddNewRoomDetails.getSelectionModel().getSelectedItem().getRoomId();
            Room r= RoomController.getRooms(id);
            if (rt==null){
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }else {
                setData(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void setData(Room r){
        txtRoomId.setText(r.getRoomId());
        cmbRoomType.setValue(r.getRoomType());
        cmbFloor.setValue(r.getFloor());
        txtPrice.setText(String.valueOf(r.getPrice()));
    }

    public void addRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Room r = new Room(
                txtRoomId.getText(),cmbRoomType.getSelectionModel().getSelectedItem(),cmbFloor.getSelectionModel().getSelectedItem(),Double.parseDouble(txtPrice.getText())
        );

        if (new RoomController().addRoom(r)){
            new Alert(Alert.AlertType.CONFIRMATION,"Add New Room..").show();
            loadTableData();
            txtRoomId.clear();
            cmbRoomType.setValue("");
            cmbFloor.setValue("");
            txtPrice.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            Stage stage = (Stage)roomManageContext.getScene().getWindow();
            stage.close();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String RoomId=tblAddNewRoomDetails.getSelectionModel().getSelectedItem().getRoomId();
        Room r1 =new Room(
                txtRoomId.getText(),cmbRoomType.getSelectionModel().getSelectedItem(),cmbFloor.getSelectionModel().getSelectedItem(),Double.parseDouble(txtPrice.getText())
        );

        if (new RoomController().modifyRoom(r1)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            loadTableData();
            txtRoomId.clear();
            cmbRoomType.setValue("");
            cmbFloor.setValue("");
            txtPrice.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void removeOnAction(ActionEvent actionEvent) {
        try {
            String id = tblAddNewRoomDetails.getSelectionModel().getSelectedItem().getRoomId();
            boolean isDelete;
            isDelete = RoomController.deleteRoom(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Done..").show();
                loadTableData();
                txtRoomId.clear();
                cmbRoomType.setValue("");
                cmbFloor.setValue("");
                txtPrice.clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error..").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
