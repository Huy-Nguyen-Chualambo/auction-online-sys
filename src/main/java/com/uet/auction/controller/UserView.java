package com.uet.auction.controller;

import com.uet.auction.model.Item;
import com.uet.auction.model.User;
import com.uet.auction.repository.DataStorage;
import com.uet.auction.service.AuctionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Bidder view.
 */
@Component
public class UserView extends BaseController implements Initializable {

    @FXML
    private ListView<Item> auctionListView;

    @FXML
    private Label currentPriceLabel;

    @FXML
    private TextField bidAmountField;

    private final AuctionService auctionService;
    private final DataStorage dataStorage;

    @Autowired
    public UserView(AuctionService auctionService, DataStorage dataStorage) {
        this.auctionService = auctionService;
        this.dataStorage = dataStorage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshList();
        
        if (auctionListView != null) {
            auctionListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && currentPriceLabel != null) {
                    currentPriceLabel.setText("Current Price: " + newVal.getCurrentPrice());
                }
            });
        }
    }

    public void refreshList() {
        if (auctionListView != null) {
            auctionListView.setItems(FXCollections.observableArrayList(dataStorage.getActiveItems()));
        }
    }

    @FXML
    public void handleBid() {
        Item selectedItem = auctionListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null || currentUser == null) return;

        try {
            double amount = Double.parseDouble(bidAmountField.getText());
            boolean success = auctionService.placeBid(currentUser, selectedItem.getId(), amount);
            
            if (success) {
                refreshList();
                if (currentPriceLabel != null) {
                    currentPriceLabel.setText("Current Price: " + selectedItem.getCurrentPrice());
                }
                showAlert("Success", "Bid placed successfully!");
            } else {
                showAlert("Error", "Bid failed! Check the price, your balance, or the auction status.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount!");
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
