package com.revature.WishListApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.WishListApplication.Model.Brand;

public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findByName(String name);

}
