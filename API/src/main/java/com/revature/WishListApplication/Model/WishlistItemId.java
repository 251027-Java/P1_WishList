package com.revature.WishListApplication.Model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class WishlistItemId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String wishlistId;
    private String itemId;

    public WishlistItemId() {}

    public WishlistItemId(String wishlistId, String itemId) {
        this.wishlistId = wishlistId;
        this.itemId = itemId;
    }
}
