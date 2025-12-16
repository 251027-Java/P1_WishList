package com.revature.WishListApplication.Controller;

import com.revature.WishListApplication.Service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/search")
    public UserDTO search(@RequestParam String username) {
        return service.searchByUsername(username);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserWOIDDTO userdto){
        return service.create(userdto);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable String id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
