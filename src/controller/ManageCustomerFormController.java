package controller;

import controller.main.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import view.tm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageCustomerFormController {

    public TextField txtGuestId;
    public TextField txtGuestAddress;
    public TextField txtContact;
    public TextField txtGuestName;
    public TextField txtNIC;
    public TableView<CustomerTm> tblGuest;
    public TableColumn colGuestId;
    public TableColumn colGuestName;
    public TableColumn colGuestAddress;
    public TableColumn colNIC;
    public TableColumn colContact;
    public AnchorPane manageCustomerContext;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C][-][0-9]{3,20}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-Z  a-z -9 ]{3,}$");
    Pattern NICPattern = Pattern.compile("^[0-9]{9,}[V]$");
    Pattern contactPattern = Pattern.compile("^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$");

    private void storeValidations() {
        map.put(txtGuestId,idPattern);
        map.put(txtGuestName, namePattern);
        map.put(txtGuestAddress,addressPattern);
        map.put(txtNIC,NICPattern);
        map.put(txtContact, contactPattern);
    }

    private Object validate() {

        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.getParent().setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green");
        }
        return true;
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        loadTableData();
        storeValidations();

        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colGuestAddress.setCellValueFactory(new PropertyValueFactory<>("guestAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblGuest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getTableData(newValue);
        });

    }
    ObservableList<CustomerTm> tableData= FXCollections.observableArrayList();

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<Customer> allCustomers = CustomerController.getAllGuests();
        ArrayList<CustomerTm> guests = new ArrayList<>();
        for (Customer g : allCustomers){
            guests.add(new CustomerTm(
                    g.getGuestId(),
                    g.getName(),
                    g.getAddress(),
                    g.getNic(),
                    g.getContact()
            ));
            tableData.clear();
            tableData.addAll(guests);
            tblGuest.setItems(tableData);
        }
    }

    public void getTableData(CustomerTm ng){
        try{
            String id=tblGuest.getSelectionModel().getSelectedItem().getGuestId();
            Customer g1= CustomerController.getGuest(id);
            if (ng==null){
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }else {
                setData(g1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setData(Customer g){
        txtGuestId.setText(g.getGuestId());
        txtGuestName.setText(g.getName());
        txtGuestAddress.setText(g.getAddress());
        txtNIC.setText(g.getNic());
        txtContact.setText(g.getContact());
    }

    public void ValidationTwoKey(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void ValidationOneKey(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer g = new Customer(
                txtGuestId.getText(),txtGuestName.getText(),txtGuestAddress.getText(),txtNIC.getText(),txtContact.getText()
        );
        if (new CustomerController().addCustomer(g)){
            new Alert(Alert.AlertType.CONFIRMATION,"Add New Guest..").show();
            loadTableData();
            txtGuestId.clear();
            txtGuestName.clear();
            txtNIC.clear();
            txtContact.clear();
            txtGuestAddress.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            Stage stage = (Stage)manageCustomerContext.getScene().getWindow();
            stage.close();
        }
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer g = new Customer(
                txtGuestId.getText(),txtGuestName.getText(),txtGuestAddress.getText(),txtNIC.getText(),txtContact.getText()
        );

        if (new CustomerController().modifyGuest(g)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            loadTableData();
            txtGuestId.clear();
            txtGuestName.clear();
            txtNIC.clear();
            txtContact.clear();
            txtGuestAddress.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void removeCustomerOnAction(ActionEvent actionEvent) {
        try {
            String id = tblGuest.getSelectionModel().getSelectedItem().getGuestId();
            boolean isDelete;
            isDelete = CustomerController.deleteGuest(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Done..").show();
                loadTableData();
                txtGuestId.clear();
                txtGuestName.clear();
                txtNIC.clear();
                txtContact.clear();
                txtGuestAddress.clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error..").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
