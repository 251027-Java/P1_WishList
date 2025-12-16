// ...existing code...
package com.revature.WishListApplication.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.WishListApplication.Service.WishlistItemService;

@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {
    private final WishlistItemService service;

    public WishlistItemController(WishlistItemService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<ItemDTO> search(@RequestParam(required = false) String wishlistId) {
        if (wishlistId != null) {
            return service.searchByWishlistId(wishlistId);
        }
        return List.of();
    }

    @PostMapping
    public WishlistItemDTO create(@RequestBody WishlistItemDTO dto){
        return service.create(dto);
    }
}