package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminFormController {
    public AnchorPane adminContext;
    public AnchorPane lodeContext;

    public void EmployeeManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EmployeeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void IncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/IncomeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void RoomManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RoomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) adminContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
