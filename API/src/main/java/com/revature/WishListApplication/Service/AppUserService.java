package com.revature.WishListApplication.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;

@Service
public class AppUserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String rawPassword) {
        User user = new User();
        user.setUserUsername(username);
        user.setUserPassword(passwordEncoder.encode(rawPassword)); // important
        return repo.save(user);
    }
}
