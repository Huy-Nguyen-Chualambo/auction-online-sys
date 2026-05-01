package com.uet.auction.model;

public class Electronics extends Item {
    public Electronics(int id, String name, String description, double startingPrice, User owner) {
        super(id, name, description, startingPrice, owner);
    }

    @Override
    public String getItemType() {
        return "Electronics";
    }
}
