package com.uet.auction.service;

import com.uet.auction.model.*;
import com.uet.auction.repository.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    private final DataStorage dataStorage;

    @Autowired
    public AuctionService(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Core Bidding Logic (Requirement 3.1.3 & 3.2.2)
     * Handles concurrent bids safely using synchronized on the item.
     */
    public boolean placeBid(User user, int itemId, double bidAmount) {
        Item item = dataStorage.getItemById(itemId);
        if (item == null) return false;

        synchronized (item) {
            // 1. Check if auction is running
            if (item.getStatus() != Item.Status.RUNNING) {
                System.out.println("Error: Auction is not running!");
                return false;
            }

            // 2. Check time
            if (item.isExpired()) {
                item.setStatus(Item.Status.FINISHED);
                System.out.println("Error: Auction has ended!");
                return false;
            }

            // 3. Check bid amount (must be higher than current price)
            if (bidAmount <= item.getCurrentPrice()) {
                System.out.println("Error: Bid must be higher than current price (" + item.getCurrentPrice() + ")!");
                return false;
            }

            // 4. Check user balance (Requirement 3.1.3)
            if (user.getBalance() < bidAmount) {
                System.out.println("Error: Insufficient balance!");
                return false;
            }

            // 5. Check role (Only Bidders can bid)
            if (!(user instanceof Bidder)) {
                System.out.println("Error: Only bidders can place bids!");
                return false;
            }

            // 6. Update item (Atomic update within synchronized block)
            item.setCurrentPrice(bidAmount);
            item.setHighestBidder(user);

            // Anti-sniping Algorithm (Requirement 3.2.3)
            // If bid within last 30 seconds, extend by 30 seconds
            LocalDateTime now = LocalDateTime.now();
            if (item.getEndTime().minusSeconds(30).isBefore(now)) {
                item.setEndTime(item.getEndTime().plusSeconds(30));
                dataStorage.addSystemLog("Auction for " + item.getName() + " extended due to late bid!");
            }

            dataStorage.addSystemLog("Success: " + user.getUsername() + " bid " + bidAmount + " on " + item.getName());
            return true;
        }
    }

    public void processApproval(Item item, boolean approved, String note) {
        if (approved) {
            dataStorage.approveItem(item);
            notifyAllUsers("New item '" + item.getName() + "' is now LIVE!");
        } else {
            dataStorage.getPendingItems().remove(item);
            notify(item.getOwner(), "Your item was rejected. Reason: " + note);
        }
    }

    public void notify(User targetUser, String message) {
        if (targetUser != null) {
            targetUser.addNotification(message);
            System.out.println("[PRIVATE NOTIFICATION] To " + targetUser.getUsername() + ": " + message);
        }
    }

    public void notifyAllUsers(String message) {
        dataStorage.addSystemLog(message);
        for (User u : dataStorage.getUsers()) {
            u.addNotification(message);
        }
    }

    // Auto-update auction statuses
    public void updateAuctionStatuses() {
        LocalDateTime now = LocalDateTime.now();
        for (Item item : dataStorage.getAllItems()) {
            if (item.getStatus() == Item.Status.RUNNING && item.isExpired()) {
                item.setStatus(Item.Status.FINISHED);
                User winner = item.getHighestBidder();
                if (winner != null) {
                    notifyAllUsers("Auction for " + item.getName() + " finished! Winner: " + winner.getUsername());
                } else {
                    notifyAllUsers("Auction for " + item.getName() + " finished with no winner.");
                }
            }
        }
    }
}
