package com.uet.auction.repository;

import com.uet.auction.model.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataStorage {
    private List<Item> items = new ArrayList<>();
    private List<Item> pendingItems = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    public DataStorage() {
        initDummyData();
    }

    private void initDummyData() {
        // Users
        Admin admin = new Admin(1, "admin", "admin123");
        Seller seller = new Seller(2, "seller1", "seller123", 0.0);
        Bidder bidder1 = new Bidder(3, "bidder1", "bidder123", 1000000.0);
        Bidder bidder2 = new Bidder(4, "bidder2", "bidder234", 500000.0);

        users.add(admin);
        users.add(seller);
        users.add(bidder1);
        users.add(bidder2);

        // Active Items
        Electronics laptop = new Electronics(1, "MacBook M3", "Latest MacBook Pro", 30000000.0, seller);
        laptop.setStartTime(LocalDateTime.now().minusHours(1));
        laptop.setEndTime(LocalDateTime.now().plusDays(1));
        laptop.setStatus(Item.Status.RUNNING);
        
        Art painting = new Art(2, "Mona Lisa Copy", "A very good copy", 5000000.0, seller);
        painting.setStartTime(LocalDateTime.now().minusHours(2));
        painting.setEndTime(LocalDateTime.now().plusHours(1));
        painting.setStatus(Item.Status.RUNNING);

        items.add(laptop);
        items.add(painting);

        // Pending Items
        Vehicle car = new Vehicle(3, "Tesla Model 3", "Used Tesla", 800000000.0, seller);
        pendingItems.add(car);
    }

    public List<Item> getAllItems() {
        return items;
    }

    public List<Item> getActiveItems() {
        return items.stream()
                .filter(i -> i.getStatus() == Item.Status.RUNNING)
                .collect(Collectors.toList());
    }

    public Item getItemById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst().orElse(
            pendingItems.stream().filter(i -> i.getId() == id).findFirst().orElse(null)
        );
    }

    public List<Item> getPendingItems() {
        return pendingItems;
    }

    public void addPendingItem(Item item) {
        pendingItems.add(item);
    }

    public void approveItem(Item item) {
        pendingItems.remove(item);
        item.setStatus(Item.Status.RUNNING);
        items.add(item);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void addSystemLog(String message) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String fullLog = "[" + timeStamp + "] " + message;
        logs.add(fullLog);
        System.out.println(fullLog);
    }

    public List<String> getLogs() {
        return logs;
    }
}