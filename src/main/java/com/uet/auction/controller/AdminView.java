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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class AdminView extends BaseController implements Initializable {

    private final DataStorage dataStorage;
    private final AuctionService auctionService;

    @FXML private TableView<Item> tablePending;
    @FXML private TextField txtReason;
    @FXML private DatePicker datePickerStart;
    @FXML private DatePicker datePickerEnd;

    @Autowired
    public AdminView(DataStorage dataStorage, AuctionService auctionService) {
        this.dataStorage = dataStorage;
        this.auctionService = auctionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshPendingTable();
    }

    private void refreshPendingTable() {
        if (tablePending != null) {
            tablePending.setItems(FXCollections.observableArrayList(dataStorage.getPendingItems()));
        }
    }

    @FXML
    public void handleApprove() {
        Item selectedItem = tablePending.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Default time if not set: start now, end in 24h
            LocalDateTime start = (datePickerStart != null && datePickerStart.getValue() != null) 
                    ? datePickerStart.getValue().atStartOfDay() : LocalDateTime.now();
            LocalDateTime end = (datePickerEnd != null && datePickerEnd.getValue() != null) 
                    ? datePickerEnd.getValue().atTime(23, 59) : LocalDateTime.now().plusHours(24);
            
            selectedItem.setStartTime(start);
            selectedItem.setEndTime(end);
            
            auctionService.processApproval(selectedItem, true, "");
            refreshPendingTable();
            showAlert("Success", "Product approved and listed!");
        }
    }

    @FXML
    public void handleReject() {
        Item selectedItem = tablePending.getSelectionModel().getSelectedItem();
        String reason = txtReason.getText();
        if (selectedItem != null) {
            auctionService.processApproval(selectedItem, false, reason);
            refreshPendingTable();
            showAlert("Info", "Product rejected.");
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
