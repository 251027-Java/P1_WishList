package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.WishListApplication.Controller.ItemDTO;
import com.revature.WishListApplication.Controller.ItemWOIDDTO;
import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Repository.ItemRepository;
import com.revature.WishListApplication.Repository.WishlistItemRepository;

@Service
public class ItemService {
    private final ItemRepository repository;


    public ItemService(ItemRepository repository, WishlistItemRepository wishlistItemRepository){
        this.repository = repository;
    }

    public List<ItemDTO> getAllItems(){
        return repository.findAll().stream().map(this::ItemToDto).toList();
    }

    public List<ItemDTO> searchByItemname(String name){
        return repository.findByItemNameStartingWithIgnoreCase(name).stream().map(this::ItemToDto).toList();
    }

    public List<ItemDTO> searchByBrand(String brand){
        return repository.findByBrand(brand).stream().map(this::ItemToDto).toList();
    }

    public ItemDTO create(ItemWOIDDTO dto){
        Item entity = new Item(dto.itemName(), dto.brand(), dto.itemPrice());
        return ItemToDto(repository.save(entity));
    }

    public ItemDTO getById(String id){
        return repository.findById(id)
                .map(this::ItemToDto)
                .orElse(null);
    }
    
    public ItemDTO update(String id, ItemDTO dto){
        Item item = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setItemName(dto.itemName());
        item.setBrand(dto.brand());
        item.setItemPrice(dto.itemPrice());
        
        return ItemToDto(repository.save(item));
    }

    public void delete(String id){
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
    }

    private ItemDTO ItemToDto(Item item){
        return new ItemDTO(item.getItemId(), item.getItemName(), item.getBrand(), item.getItemPrice());
    }

}
