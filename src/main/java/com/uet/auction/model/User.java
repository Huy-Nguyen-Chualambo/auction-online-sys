package com.uet.auction.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a User in the system.
 */
public abstract class User extends Entity {
    public enum Role {
        ADMIN, SELLER, BIDDER
    }

    protected String username;
    protected String password;
    protected Role role;
    protected double balance;
    protected List<String> notifications = new ArrayList<>();

    public User(int id, String username, String password, Role role, double balance) {
        super(id);
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addNotification(String message) {
        this.notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
