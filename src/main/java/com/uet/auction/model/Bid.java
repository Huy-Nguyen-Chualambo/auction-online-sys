package com.uet.auction.model;

import java.time.LocalDateTime;

/**
 * Class representing a Bid transaction in the system.
 */
public class Bid extends Entity {
    private final User bidder;
    private final Item item;
    private final double amount;
    private final LocalDateTime timestamp;

    public Bid(int id, User bidder, Item item, double amount) {
        super(id);
        this.bidder = bidder;
        this.item = item;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public User getBidder() {
        return bidder;
    }

    public Item getItem() {
        return item;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
