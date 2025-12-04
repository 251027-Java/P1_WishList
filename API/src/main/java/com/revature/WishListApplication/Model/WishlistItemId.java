package com.revature.WishListApplication.Model;

import java.io.Serializable;

public class WishlistItemId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Wishlist wishlist;
    private Item item;

    public WishlistItemId() {}

    public WishlistItemId(Item item, Wishlist wishlist) {
        this.item = item;
        this.wishlist = wishlist;
    }
}
