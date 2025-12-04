package com.revature.WishListApplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "WishlistItem")
@Data
public class WishlistItem {
    @EmbeddedId
    private WishlistItemId id;

    @MapsId("wishlistId")
    @ManyToOne()
    @JoinColumn(name = "wishlistId")
    @ToString.Exclude
    private Wishlist wishlist;

    @MapsId("itemId")
    @ManyToOne()
    @JoinColumn(name = "itemId")
    @ToString.Exclude
    private Item item;
}
