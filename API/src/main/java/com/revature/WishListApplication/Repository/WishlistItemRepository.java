package com.revature.WishListApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.WishlistItem;
import com.revature.WishListApplication.Model.WishlistItemID;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, WishlistItemID> {
	void deleteByWishlist_WishlistId(String wishlistId);
	List<WishlistItem> findByWishlist_WishlistId(String wishlistId);
}