package com.revature.WishListApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public UserDTO searchByUsername(String username){
        return repository.findByUserUsername(username)
                .map(this::UserToDto)
                .orElse(null);
    }

    public UserDTO create(UserWOIDDTO dto){
        User entity = new User(dto.userUsername(), dto.userPassword());

        // Make sure username is available
        if (repository.findByUserUsername(entity.getUserUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(422), "Username " +
                    "already exists");
        }

        return UserToDto(repository.save(entity));
    }

    public UserDTO getById(String id){
        return repository.findById(id)
                .map(this::UserToDto)
                .orElse(null);
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
