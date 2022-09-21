package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ReceptionistFormController {
    public AnchorPane receptionistContext;
    public AnchorPane lodeContext;

    public void openManageCustomerForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void openPaymentForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void openBookingForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/BookingForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void openMealPlanForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MealPlanForm.fxml");
        Parent load = FXMLLoader.load(resource);
        lodeContext.getChildren().clear();
        lodeContext.getChildren().add(load);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) receptionistContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
