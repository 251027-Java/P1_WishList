package com.revature.WishListApplication.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.WishListApplication.Service.WishlistService;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<WishlistDTO> getAllWishlists(@RequestParam(required = true) String userId) {
        return service.getAllUserWishlists(userId);
    }

    @GetMapping("/{id}")
    public WishlistDTO getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public WishlistDTO update(@PathVariable String id, @RequestBody WishlistWOIDDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PostMapping
    public WishlistDTO create(@RequestBody WishlistWOIDDTO dto) {
        return service.create(dto);
    }
}
