package com.uet.auction.model;

/**
 * Base abstract class for all entities in the system.
 */
public abstract class Entity {
    protected int id;

    public Entity() {}

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
