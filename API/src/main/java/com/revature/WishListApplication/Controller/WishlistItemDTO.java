package com.revature.WishListApplication.Controller;

import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Model.Wishlist;

public record WishlistItemDTO(Item item, Wishlist wishlist){

    public void setItem(Item item2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setItem'");
    }

    public void setWishlist(Wishlist wishlist2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setWishlist'");
    }}
