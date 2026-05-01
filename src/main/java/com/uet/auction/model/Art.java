package com.uet.auction.model;

public class Art extends Item {
    public Art(int id, String name, String description, double startingPrice, User owner) {
        super(id, name, description, startingPrice, owner);
    }

    @Override
    public String getItemType() {
        return "Art";
    }
}
