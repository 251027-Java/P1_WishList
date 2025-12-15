package com.revature.WishListApplication.Controller;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping
    public List<WishlistDTO> getAllWishlists() {
        return service.getAllWishlists();
    }
    @GetMapping("/search")
    public List<WishlistDTO> search(@RequestParam String id) {
        return service.searchByUserId(id);
    }
    @PostMapping
    public WishlistDTO create(@RequestBody WishlistWOIDDTO wishlistdto){
        return service.create(wishlistdto);
    }
    @GetMapping("/{id}")
    public WishlistDTO getById(@PathVariable String id){
        return service.getById(id);
    }
    @PutMapping("/{id}")
    public WishlistDTO update(@PathVariable String id, @RequestBody WishlistDTO dto){
        return service.update(id, dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}