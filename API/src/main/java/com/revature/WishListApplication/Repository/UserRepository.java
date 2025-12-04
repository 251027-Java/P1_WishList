package com.revature.WishListApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.User;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUserUsername(String username);
}
