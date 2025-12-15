package com.revature.WishListApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByItemNameStartingWithIgnoreCase(String name);
    List<Item> findByBrand(String brand);
}
