package com.uet.auction.controller;

import com.uet.auction.AuctionApplication;
import com.uet.auction.model.User;
import com.uet.auction.repository.DataStorage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginView implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<User.Role> roleComboBox;

    private final DataStorage dataStorage;

    @Autowired
    public LoginView(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (roleComboBox != null) {
            roleComboBox.setItems(FXCollections.observableArrayList(User.Role.values()));
            roleComboBox.setValue(User.Role.BIDDER);
        }
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        User.Role selectedRole = roleComboBox.getValue();

        User user = dataStorage.getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found!");
            // In a real app, show an alert
            return;
        }

        if (user.getRole() != selectedRole) {
            System.out.println("Role mismatch!");
            return;
        }

        String fxmlFile = "";
        switch (selectedRole) {
            case ADMIN:
                fxmlFile = "/com/uet/auction/view/admin.fxml";
                break;
            case SELLER:
                fxmlFile = "/com/uet/auction/view/seller.fxml";
                break;
            case BIDDER:
                fxmlFile = "/com/uet/auction/view/user.fxml";
                break;
        }

        if (!fxmlFile.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setControllerFactory(AuctionApplication.getSpringContext()::getBean);
            Parent root = loader.load();
            
            // Pass user to the next controller if needed
            Object controller = loader.getController();
            if (controller instanceof BaseController) {
                ((BaseController) controller).setUser(user);
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Auction System - " + selectedRole + ": " + username);
            stage.show();
        }
    }
}

