package com.revature.WishListApplication;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.WishListApplication.Model.Brand;
import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Model.Wishlist;
import com.revature.WishListApplication.Model.WishlistItem;
import com.revature.WishListApplication.Repository.BrandRepository;
import com.revature.WishListApplication.Repository.ItemRepository;
import com.revature.WishListApplication.Repository.UserRepository;
import com.revature.WishListApplication.Repository.WishlistItemRepository;
import com.revature.WishListApplication.Repository.WishlistRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WishListApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishListApplication.class, args);
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    CommandLineRunner seedData (PasswordEncoder encoder, UserRepository userRepository,
                                ItemRepository itemRepository, BrandRepository brandRepository, WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository) {
        return args -> {
            var u1 = new User("brody", encoder.encode("password1"));
            var u2 = new User("manu", encoder.encode("password2"));
            var u3 = new User("natalia", encoder.encode("password3"));
            userRepository.saveAll(List.of(u1, u2, u3));
            var b1 = new Brand("Apple");
            var b2 = new Brand("Sony");
            var b3 = new Brand("Nvidia");
            brandRepository.saveAll(List.of(b1, b2, b3));
            var i1 = new Item("iPhone 17", b1, new BigDecimal(1699.99));
            var i2 = new Item("PlayStation 5", b2, new BigDecimal(599.99));
            var i3 = new Item("GeForce RTX 5090", b3, new BigDecimal(2999.99));
            itemRepository.saveAll(List.of(i1, i2, i3));
            var w1 = new Wishlist(u1);
            var w2 = new Wishlist(u2);
            var w3 = new Wishlist(u3);
            wishlistRepository.saveAll(List.of(w1, w2, w3));
            var wi1 = new WishlistItem(i1, w1);
            var wi2 = new WishlistItem(i2, w1);
            var wi3 = new WishlistItem(i1, w2);
            var wi4 = new WishlistItem(i3, w2);
            var wi5 = new WishlistItem(i2, w3);
            var wi6 = new WishlistItem(i3, w3);
            wishlistItemRepository.saveAll(List.of(wi1, wi2, wi3, wi4, wi5, wi6));
        };
    }

}
