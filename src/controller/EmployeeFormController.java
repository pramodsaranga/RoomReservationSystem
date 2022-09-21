package controller;

import controller.main.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import view.tm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane addNewEmployeeAnchorPaneContext;
    public TextField txtEmployeeId;
    public TextField txtEmployeeAddress;
    public TextField txtBirthday;
    public TextField txtPost;
    public TextField txtEmployeeName;
    public TextField txtNIC;
    public TextField txtContact;
    public TableView<EmployeeTm> tblEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeAddress;
    public TableColumn colNIC;
    public TableColumn colBirthday;
    public TableColumn colContact;
    public TableColumn colPost;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[E][-][0-9]{3,20}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-Z  a-z -9 ]{3,}$");
    Pattern NICPattern = Pattern.compile("^[0-9]{9,}[V]$");
    Pattern birthdayPattern = Pattern.compile("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$");
    Pattern contactPattern = Pattern.compile("^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$");
    Pattern postPattern = Pattern.compile("^[A-z ]{3,20}$");


    private void storeValidations() {
        map.put(txtEmployeeId,idPattern);
        map.put(txtEmployeeName, namePattern);
        map.put(txtEmployeeAddress,addressPattern);
        map.put(txtNIC,NICPattern);
        map.put(txtBirthday,birthdayPattern);
        map.put(txtContact, contactPattern);
        map.put(txtPost,postPattern);
    }

    private Object validate() {
        //btnSaveBook.setDisable(true);

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

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("post"));

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getTableData(newValue);
        });

    }

    ObservableList<EmployeeTm> tableData = FXCollections.observableArrayList();

    public void onAddNewEmployee(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Employee e = new Employee(
                txtEmployeeId.getText(),txtEmployeeName.getText(),txtEmployeeAddress.getText(),txtNIC.getText(),
                txtBirthday.getText(),txtContact.getText(),txtPost.getText()
        );

        if (new EmployeeController().addEmployee(e)){
            new Alert(Alert.AlertType.CONFIRMATION,"Add New Employee").show();
            loadTableData();
            txtEmployeeId.clear();
            txtEmployeeName.clear();
            txtEmployeeAddress.clear();
            txtContact.clear();
            txtNIC.clear();
            txtBirthday.clear();
            txtPost.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            Stage stage =(Stage)addNewEmployeeAnchorPaneContext.getScene().getWindow();
            stage.close();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<Employee> allEmployees = EmployeeController.getAllEmployees();
        ArrayList<EmployeeTm> employees = new ArrayList<>();
        for (Employee e : allEmployees){
            employees.add(new EmployeeTm(
                    e.getEmployeeId(),
                    e.getEmployeeName(),
                    e.getEmployeeAddress(),
                    e.getNic(),
                    e.getBirthday(),
                    e.getContact(),
                    e.getPost()
            ));
            tableData.clear();
            tableData.addAll(employees);
            tblEmployee.setItems(tableData);
        }
    }

    public void getTableData(EmployeeTm et){
        try{
            String id=tblEmployee.getSelectionModel().getSelectedItem().getEmployeeId();
           Employee e= EmployeeController.getEmployee(id);
            if (et==null){
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }else {
                setData(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setData(Employee e){
        txtEmployeeId.setText(e.getEmployeeId());
        txtEmployeeName.setText(e.getEmployeeName());
        txtEmployeeAddress.setText(e.getEmployeeAddress());
        txtNIC.setText(e.getNic());
        txtBirthday.setText(e.getBirthday());
        txtContact.setText(e.getContact());
        txtPost.setText(e.getPost());
    }

    public void onModifyEmployee(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        Employee e = new Employee(
                txtEmployeeId.getText(),txtEmployeeName.getText(),txtEmployeeAddress.getText(),txtNIC.getText(),txtBirthday.getText(),
                txtContact.getText(),txtPost.getText()
        );

        if (new EmployeeController().modifyEmployee(e)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            loadTableData();
            txtEmployeeId.clear();
            txtEmployeeName.clear();
            txtEmployeeAddress.clear();
            txtContact.clear();
            txtNIC.clear();
            txtBirthday.clear();
            txtPost.clear();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void deleteEmployee(MouseEvent mouseEvent) {
        try {
            String id = tblEmployee.getSelectionModel().getSelectedItem().getEmployeeId();
            boolean isDelete;
            isDelete = EmployeeController.deleteEmployee(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Done..").show();
                loadTableData();
                txtEmployeeId.clear();
                txtEmployeeName.clear();
                txtEmployeeAddress.clear();
                txtContact.clear();
                txtNIC.clear();
                txtBirthday.clear();
                txtPost.clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error..").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void ValidationOne(KeyEvent keyEvent) {
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

    public void ValidationTwo(KeyEvent keyEvent) {
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
}
