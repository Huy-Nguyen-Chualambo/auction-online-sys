package com.uet.auction.controller;

import com.uet.auction.model.Product;
import com.uet.auction.repository.DataStorage;
import com.uet.auction.service.AuctionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SellerView extends BaseController implements Initializable {

    private final DataStorage dataStorage;
    private final AuctionService auctionService;

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;

    @Autowired
    public SellerView(DataStorage dataStorage, AuctionService auctionService) {
        this.dataStorage = dataStorage;
        this.auctionService = auctionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    @FXML
    private void handlePostAction() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            if (name == null || name.isEmpty()) {
                showAlert("Warning", "Product name cannot be empty!");
                return;
            }
            double price = Double.parseDouble(txtPrice.getText());

            // Create new Product and add to pending list
            Product newP = new Product(id, name, price, currentUser);
            dataStorage.addPendingItem(newP);

            showAlert("Success", "Product submitted for Admin approval!");
            
            // Clear fields
            txtId.clear();
            txtName.clear();
            txtPrice.clear();

        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid ID or Price format!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}