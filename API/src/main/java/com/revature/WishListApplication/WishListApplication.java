package com.revature.WishListApplication;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;

@SpringBootApplication
public class WishListApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishListApplication.class, args);
	}

	@Bean
    CommandLineRunner seedData (UserRepository repository) {
        return args -> {
            var e1 = new User("brody", "password1");
            var e2 = new User("manu", "password2");
            var e3 = new User("natalia", "password3");
            repository.saveAll(List.of(e1, e2, e3));
        };
    }

}
