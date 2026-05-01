package com.uet.auction.model;

import java.time.LocalDateTime;

/**
 * Concrete implementation of Item (Product) for the auction system.
 */
public class Product extends Item {
    
    public Product(int id, String name, double startingPrice, String ownerName) {
        super(id, name, "", startingPrice, null); // owner will be set later or use ownerName
        this.currentPrice = startingPrice;
    }

    public Product(int id, String name, double startingPrice, User owner) {
        super(id, name, "", startingPrice, owner);
    }

    // Compatibility methods for old code
    public double setEndingPrice(double bidAmount) {
        this.currentPrice = bidAmount;
        return bidAmount;
    }

    public double getEndingPrice() {
        return this.currentPrice;
    }

    public void setHighestBidder(User user) {
        super.setHighestBidder(user);
    }

    public String getHighestBidderName() {
        return highestBidder != null ? highestBidder.getUsername() : "";
    }

    public void setActive(boolean active) {
        this.status = active ? Status.RUNNING : Status.OPEN;
    }

    public int getPrice() {
        return (int) startingPrice;
    }

    @Override
    public String getItemType() {
        return "Product";
    }
}
