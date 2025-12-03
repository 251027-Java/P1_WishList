package com.revature.WishListApplication.Controller;

import com.revature.WishListApplication.Service.BrandService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    public List<BrandDTO> getAllBrands() {
        return service.getAllBrands();
    }

    @GetMapping("/search")
    public List<BrandDTO> search(@RequestParam String merchant) {
        return service.searchByBrandname(merchant);
    }

    @PostMapping
    public BrandDTO create(@RequestBody BrandWOIDDTO branddto){
        return service.create(branddto);
    }

    @GetMapping("/{id}")
    public BrandDTO getById(@PathVariable String id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public BrandDTO update(@PathVariable String id, @RequestBody BrandDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
