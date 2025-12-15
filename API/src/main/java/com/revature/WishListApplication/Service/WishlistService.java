package com.revature.WishListApplication.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.WishListApplication.Model.Wishlist;
import com.revature.WishListApplication.Repository.WishlistRepository;
import com.revature.WishListApplication.DTO.WishlistDTO;
import com.revature.WishListApplication.DTO.WishlistWOIDDTO;
import com.revature.WishListApplication.Exception.ResourceNotFoundException;
@Service
public class WishlistService {
    private final WishlistRepository repository;
    @Autowired
    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }
    public List<WishlistDTO> getAllWishlists() {
        List<Wishlist> wishlists = repository.findAll();
        return wishlists.stream().map(this::convertToDTO).toList();
    }
    public List<WishlistDTO> searchByUserId(String userId) {
        return repository.findByUserId(userId).stream().map(this::convertToDTO).toList();
    }

    public WishlistDTO create(WishlistWOIDDTO wishlistdto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(wishlistdto.getUserId());
        wishlist.setItemIds(wishlistdto.getItemIds());
        Wishlist savedWishlist = repository.save(wishlist);
        return convertToDTO(savedWishlist);
    }
    public WishlistDTO getById(String id) {
        Wishlist wishlist = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + id));
        return convertToDTO(wishlist);
    }
    public WishlistDTO update(String id, WishlistDTO dto) {
        Wishlist wishlist = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found with id: " + id));
        wishlist.setUserId(dto.getUserId());
        wishlist.setItemIds(dto.getItemIds());
        Wishlist updatedWishlist = repository.save(wishlist);
        return convertToDTO(updatedWishlist);
    }
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Wishlist not found with id: " + id);
        }
        repository.deleteById(id);
    }
    private WishlistDTO convertToDTO(Wishlist wishlist) {
        WishlistDTO dto = new WishlistDTO();
        dto.setId(wishlist.getId());
        dto.setId(wishlist.getWishlistId());
         dto.setItemIds(wishlist.getItemIds());
        return dto;
    }
}