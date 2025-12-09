package com.revature.WishListApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserUsername(String username);
}
