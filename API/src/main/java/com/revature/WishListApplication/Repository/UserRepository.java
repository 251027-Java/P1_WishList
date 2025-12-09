package com.revature.WishListApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserUsername(String username);
}
