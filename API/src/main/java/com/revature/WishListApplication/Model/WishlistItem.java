package com.revature.WishListApplication.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "WishlistItem")
@Data
public class WishlistItem {

    @EmbeddedId
    private WishlistItemID wishListItemId;
}
