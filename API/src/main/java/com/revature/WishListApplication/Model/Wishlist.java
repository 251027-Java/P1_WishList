package com.revature.WishListApplication.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Wishlists")
@Data
public class Wishlist {
    @Id
    @GeneratedValue
    private String wishlistId;

    @ManyToOne()
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;

    @Column(name = "wishlistName")
    private String wishlistName;

    public Wishlist() {}

    public Wishlist(User user, String wishlistName) {
        this.user = user;
        this.wishlistName = wishlistName;
    }
}
