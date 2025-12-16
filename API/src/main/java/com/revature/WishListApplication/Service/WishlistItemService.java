package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.WishListApplication.Controller.ItemDTO;
import com.revature.WishListApplication.Controller.WishlistItemDTO;
import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Model.WishlistItem;
import com.revature.WishListApplication.Repository.WishlistItemRepository;

@Service
public class WishlistItemService {
    private final WishlistItemRepository repository;

    public WishlistItemService(WishlistItemRepository repository){
        this.repository = repository;
    }

    public List<ItemDTO> searchByWishlistId(String wishlistId){
        return repository.findByWishlist_WishlistId(wishlistId).stream().map(wi -> itemToDto(wi.getItem())).toList();
    }

    public WishlistItemDTO create(WishlistItemDTO dto){
        var wishlistItem = repository.save(new WishlistItem(dto.item(), dto.wishlist()));
        return wishlistItemToDto(wishlistItem);
    }

    public WishlistItemDTO wishlistItemToDto(WishlistItem wishlistItem){
        var dto = new WishlistItemDTO();
        dto.setItem(wishlistItem.getItem());
        dto.setWishlist(wishlistItem.getWishlist());
        return dto;
    }

    private ItemDTO itemToDto(Item item){
        return new ItemDTO(item.getItemId(), item.getItemName(), item.getBrand(), item.getItemPrice());
    }
}
