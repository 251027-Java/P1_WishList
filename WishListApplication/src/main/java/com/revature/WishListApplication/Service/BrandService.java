package com.revature.WishListApplication.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.WishListApplication.Controller.BrandDTO;
import com.revature.WishListApplication.Controller.BrandWOIDDTO;
import com.revature.WishListApplication.Model.Brand;
import com.revature.WishListApplication.Repository.BrandRepository;

@Service
public class BrandService {
    private final BrandRepository repository;

    public BrandService(BrandRepository repository){ this.repository = repository;}

    public List<BrandDTO> getAllBrands(){
        return repository.findAll().stream().map(this::BrandToDto).toList();
    }

    public List<BrandDTO> searchByBrandname(String name){
        return repository.findByName(name).stream().map(this::BrandToDto).toList();
    }

    public BrandDTO create(BrandWOIDDTO dto){
        Brand entity = new Brand(dto.brandName());
        return BrandToDto(repository.save(entity));
    }

    public BrandDTO getById(String id){
        return BrandToDto(repository.findById(id).get());
    }
    
    public BrandDTO update(String id, BrandDTO dto){
        Brand brand = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        brand.setBrandName(dto.brandName());
        
        return BrandToDto(repository.save(brand));
    }

    public void delete(String id){
        repository.deleteById(id);
    }

    private BrandDTO BrandToDto(Brand brand){
        return new BrandDTO(brand.getBrandId(), brand.getBrandName());
    }

}
