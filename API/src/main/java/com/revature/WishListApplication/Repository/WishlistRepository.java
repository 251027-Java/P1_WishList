package com.revature.WishListApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, String>{}
