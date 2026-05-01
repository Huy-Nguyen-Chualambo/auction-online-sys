package com.uet.auction.model;

public class Vehicle extends Item {
    public Vehicle(int id, String name, String description, double startingPrice, User owner) {
        super(id, name, description, startingPrice, owner);
    }

    @Override
    public String getItemType() {
        return "Vehicle";
    }
}
