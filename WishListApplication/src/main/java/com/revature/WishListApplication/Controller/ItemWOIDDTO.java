package com.revature.WishListApplication.Controller;

import java.math.BigDecimal;
import com.revature.WishListApplication.Model.Brand;

public record ItemWOIDDTO(String itemName, Brand brand, BigDecimal itemPrice){}
