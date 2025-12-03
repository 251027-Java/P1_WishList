package com.revature.WishListApplication.Model;

import java.math.BigDecimal;

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
@Table(name = "items")
@Data
public class Item {
    @Id
    @GeneratedValue
    private String itemId;

    @Column(name = "name")
    private String itemName;

    @ManyToOne()
    @JoinColumn(name = "brandId")
    @ToString.Exclude
    private Brand brand;

    @Column(name = "price")
    private BigDecimal itemPrice;

    public Item(){}

    public Item(String itemName, Brand brand, BigDecimal itemPrice){
        this.itemName = itemName;
        this.brand = brand;
        this.itemPrice = itemPrice;
    }

    
}
