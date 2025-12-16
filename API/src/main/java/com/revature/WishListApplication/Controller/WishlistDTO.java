package com.revature.WishListApplication.Controller;

import com.revature.WishListApplication.Model.User;

public record WishlistDTO(String wishlistId, String wishlistName, User user){}
