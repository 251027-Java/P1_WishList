package com.revature.WishListApplication.Controller;

import java.math.BigDecimal;
import com.revature.WishListApplication.Model.Brand;

public record ItemDTO(String itemId, String itemName, Brand brand, BigDecimal itemPrice){}
