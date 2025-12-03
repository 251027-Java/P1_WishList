package com.revature.WishListApplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "WishlistItem")
@Data
public class WishlistItem {
    @ManyToOne()
    @JoinColumn(name = "wishlistId")
    @ToString.Exclude
    private Wishlist wishlist;

    @ManyToOne()
    @JoinColumn(name = "itemId")
    @ToString.Exclude
    private Item item;
}
