package com.revature.WishListApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, String>{}