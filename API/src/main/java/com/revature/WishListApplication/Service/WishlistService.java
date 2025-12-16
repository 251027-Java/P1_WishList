package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.WishListApplication.Controller.WishlistDTO;
import com.revature.WishListApplication.Controller.WishlistWOIDDTO;
import com.revature.WishListApplication.Model.Wishlist;
import com.revature.WishListApplication.Repository.WishlistRepository;
import com.revature.WishListApplication.Repository.WishlistItemRepository;

@Service
public class WishlistService {
    private final WishlistRepository repository;
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistService(WishlistRepository repository, WishlistItemRepository wishlistItemRepository) {
        this.repository = repository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    public List<WishlistDTO> getAllWishlists() {
        return repository.findAll().stream().map(this::WishlistToDto).toList();
    }

    public List<WishlistDTO> getAllUserWishlists(String userId) {
        return repository.findByUser_UserId(userId).stream().map(this::WishlistToDto).toList();
    }

    public WishlistDTO create(WishlistWOIDDTO dto){
        Wishlist entity = new Wishlist(dto.wishlistName(), dto.user());
        return WishlistToDto(repository.save(entity));
    }

    public WishlistDTO getById(String id){
        return repository.findById(id)
                .map(this::WishlistToDto)
                .orElse(null);
    }

    public WishlistDTO update(String id, WishlistWOIDDTO dto){
        Wishlist wishlist = repository.findById(id).orElseThrow();
        wishlist.setWishlistName(dto.wishlistName());
        if (dto.user() != null) {
            wishlist.setUser(dto.user());
        }

        return WishlistToDto(repository.save(wishlist));
    }

    public void delete(String id){
        if(repository.findById(id).isPresent()){
            // delete child wishlist items first to avoid FK constraint violations
            try {
                wishlistItemRepository.deleteByWishlist_WishlistId(id);
            } catch (Exception e) {
                // ignore or log; we'll still attempt to delete the wishlist
            }
            repository.deleteById(id);
        }
    }

    private WishlistDTO WishlistToDto(Wishlist wishlist){
        return new WishlistDTO(wishlist.getWishlistId(), wishlist.getWishlistName(), wishlist.getUser());
    }
}
