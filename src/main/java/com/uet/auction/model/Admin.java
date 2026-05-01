package com.uet.auction.model;

/**
 * Class representing an Admin in the system.
 */
public class Admin extends User {
    public Admin(int id, String username, String password) {
        super(id, username, password, Role.ADMIN, 0.0);
    }
}
