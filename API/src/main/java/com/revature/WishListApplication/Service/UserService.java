package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.WishListApplication.Controller.UserDTO;
import com.revature.WishListApplication.Controller.UserWOIDDTO;
import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){ this.repository = repository;}

    public List<UserDTO> getAllUsers(){
        return repository.findAll().stream().map(this::UserToDto).toList();
    }

    public List<UserDTO> searchByUsername(String username){
        return repository.findByUserUsername(username).stream().map(this::UserToDto).toList();
    }

    public UserDTO create(UserWOIDDTO dto){
        User entity = new User(dto.userUsername(), dto.userPassword());
        return UserToDto(repository.save(entity));
    }

    public UserDTO getById(String id){
        return UserToDto(repository.findById(id).get());
    }
    
    public UserDTO update(String id, UserDTO dto){
        User user = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setUserUsername(dto.userUsername());
        user.setUserPassword(dto.userPassword());
        
        return UserToDto(repository.save(user));
    }

    public void delete(String id){
        repository.deleteById(id);
    }

    private UserDTO UserToDto(User user){
        return new UserDTO(user.getUserId(), user.getUserUsername(), user.getUserPassword());
    }

}
