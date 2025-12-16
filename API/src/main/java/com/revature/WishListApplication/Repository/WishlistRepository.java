package com.revature.WishListApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, String>{
    List<Wishlist> findByUserUserId(String userId);
}
