package com.uet.auction.model;

/**
 * Class representing a Seller in the system.
 */
public class Seller extends User {
    public Seller(int id, String username, String password, double balance) {
        super(id, username, password, Role.SELLER, balance);
    }
}
