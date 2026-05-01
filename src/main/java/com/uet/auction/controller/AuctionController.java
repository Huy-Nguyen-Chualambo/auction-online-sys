package com.uet.auction.controller;

import com.uet.auction.model.User;
import com.uet.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Auction-related operations.
 */
@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/bid")
    public boolean onBidButtonClick(@RequestBody BidRequest request) {
        // In a real app, 'user' would come from security context
        User user = request.getUser();
        if (user == null) return false;

        return auctionService.placeBid(
                user,
                request.getId(),
                request.getAmount()
        );
    }
}