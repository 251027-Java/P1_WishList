package com.revature.WishListApplication.Model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@Data
public class WishlistItemId implements Serializable {
    @ManyToOne()
    @JoinColumn(name = "wishlistId")
    @ToString.Exclude
    private Wishlist wishlist;

    @ManyToOne()
    @JoinColumn(name = "itemId")
    @ToString.Exclude
    private Item item;
}
