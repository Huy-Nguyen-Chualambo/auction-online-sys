package com.uet.auction.controller;

import com.uet.auction.model.User;

/**
 * Base class for all controllers that need to know about the current user.
 */
public abstract class BaseController {
    protected User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }
}
