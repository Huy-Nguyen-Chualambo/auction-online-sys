package com.uet.auction.model;

import java.time.LocalDateTime;

/**
 * Abstract class representing an Item for auction.
 */
public abstract class Item extends Entity {
    public enum Status {
        OPEN, RUNNING, FINISHED, PAID, CANCELED
    }

    protected String name;
    protected String description;
    protected double startingPrice;
    protected double currentPrice;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected User owner;
    protected User highestBidder;
    protected Status status = Status.OPEN;

    public Item(int id, String name, String description, double startingPrice, User owner) {
        super(id);
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.owner = owner;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(double startingPrice) { this.startingPrice = startingPrice; }

    public double getCurrentPrice() { return currentPrice; }
    public synchronized void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public User getHighestBidder() { return highestBidder; }
    public synchronized void setHighestBidder(User highestBidder) { this.highestBidder = highestBidder; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public abstract String getItemType();

    public boolean isExpired() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }
}
