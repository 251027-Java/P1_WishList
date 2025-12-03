package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.WishListApplication.Controller.ItemDTO;
import com.revature.WishListApplication.Controller.ItemWOIDDTO;
import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository){ this.repository = repository;}

    public List<ItemDTO> getAllItems(){
        return repository.findAll().stream().map(this::ItemToDto).toList();
    }

    public List<ItemDTO> searchByItemname(String name){
        return repository.findByName(name).stream().map(this::ItemToDto).toList();
    }

    public ItemDTO create(ItemWOIDDTO dto){
        Item entity = new Item(dto.itemName(), dto.brand(), dto.itemPrice());
        return ItemToDto(repository.save(entity));
    }

    public ItemDTO getById(String id){
        return ItemToDto(repository.findById(id).get());
    }
    
    public ItemDTO update(String id, ItemDTO dto){
        Item item = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setItemName(dto.itemName());
        item.setBrand(dto.brand());
        item.setItemPrice(dto.itemPrice());
        
        return ItemToDto(repository.save(item));
    }

    public void delete(String id){
        repository.deleteById(id);
    }

    private ItemDTO ItemToDto(Item item){
        return new ItemDTO(item.getItemId(), item.getItemName(), item.getBrand(), item.getItemPrice());
    }

}
