package com.revature.WishListApplication.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "brands")
@Data
public class Brand {
    @Id
    @GeneratedValue
    private String brandId;

    @Column(name = "name")
    private String brandName;

    public Brand(){}

    public Brand(String brandName){
        this.brandName = brandName;
    }
}
