package com.revature.WishListApplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@IdClass(WishlistItemId.class)
@Table(name = "WishlistItem")
@Data
public class WishlistItem {

    @Id
    @ManyToOne()
    @JoinColumn(name = "itemId", insertable = false, updatable = false)
    @ToString.Exclude
    private Item item;

    @Id
    @ManyToOne()
    @JoinColumn(name = "wishlistId", insertable = false, updatable = false)
    @ToString.Exclude
    private Wishlist wishlist;

    public WishlistItem() {}

    public WishlistItem(Item item, Wishlist wishlist) {
        this.item = item;
        this.wishlist = wishlist;
    }
}
