package com.revature.WishListApplication.Controller;

import com.revature.WishListApplication.Service.ItemService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemDTO> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/search")
    public List<ItemDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand) {

        if (name != null) {
            return service.searchByItemname(name);
        }

        if (brand != null) {
            return service.searchByBrand(brand);
        }

        return List.of();
    }

    @PostMapping
    public ItemDTO create(@RequestBody ItemWOIDDTO itemdto){
        return service.create(itemdto);
    }

    @GetMapping("/{id}")
    public ItemDTO getById(@PathVariable String id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ItemDTO update(@PathVariable String id, @RequestBody ItemDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
