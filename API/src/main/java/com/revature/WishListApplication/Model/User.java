package com.revature.WishListApplication.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private String userId;

    @Column(name = "username")
    private String userUsername;

    @Column(name = "password")
    private String userPassword;

    public User(){}
    
    public User(String username, String password){
        this.userUsername = username;
        this.userPassword = password;
    }
    
}
