package com.uet.auction.model;

/**
 * Class representing a Bidder (Buyer) in the system.
 */
public class Bidder extends User {
    public Bidder(int id, String username, String password, double balance) {
        super(id, username, password, Role.BIDDER, balance);
    }
}
