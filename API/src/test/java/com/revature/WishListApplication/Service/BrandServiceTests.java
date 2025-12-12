package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.BrandDTO;
import com.revature.WishListApplication.Controller.BrandWOIDDTO;
import com.revature.WishListApplication.Model.Brand;
import com.revature.WishListApplication.Repository.BrandRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {
    // BrandService Tests Using Mocks
    @Mock
    private BrandRepository repository;

    @InjectMocks
    private BrandService service;

/*  ---------------------
    Brand Creation Tests
    --------------------- */

    @Test
    public void happyPath_create_returnsBrandDTO() {
        // Arrange
        Brand brand = new Brand("Lululemon");
        brand.setBrandId("id");

        BrandWOIDDTO input = new BrandWOIDDTO("Lululemon");

        BrandDTO expected = new BrandDTO("id", "Lululemon");

        when(repository.save(any(Brand.class))).thenReturn(brand);

        // Act
        BrandDTO actual = service.create(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(repository, times(1)).save(any(Brand.class));
    }


/*  ----------------------
    Brand Retrieval Tests
    ---------------------- */

    @Test
    public void happyPath_getById_returnsBrandDTO() {
        // Arrange
        String id = "thisIsTheId";
        Brand savedBrand = new Brand("Apple");
        savedBrand.setBrandId(id);

        // Expected
        BrandDTO expected = new BrandDTO(id, "Apple");

        // Result of actual
        when(repository.findById(id)).thenReturn(Optional.of(savedBrand));

        // Act
        BrandDTO actual = service.getById(id);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getById_idDoesNotExist_returnsNull() {
        // Arrange
        when(repository.findById("missing")).thenReturn(Optional.empty());

        // Act
        BrandDTO actual = service.getById("missing");

        // Assert
        assertNull(actual);
    }

    // Check getByBrandName
    @Test
    public void happyPath_searchBrandname_returnsListOfBrandDTO() {
        // Arrange
        Brand brand = new Brand("Prada");
        brand.setBrandId("pid");

        List<BrandDTO> expected = List.of(new BrandDTO("pid", "Prada"));

        when(repository.findByBrandName("Prada")).thenReturn(List.of(brand));

        // Act
        List<BrandDTO> actual = service.searchByBrandname("Prada");

        // Assert
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
    @Test
    public void searchBrandname_brandDoesNotExist_returnsEmptyList() {
        // Arrange
        when(repository.findByBrandName("missing")).thenReturn(List.of());

        // Act
        List<BrandDTO> actual = service.searchByBrandname("missing");

        // Assert
        assertThat(actual).isEmpty();
    }

    // getAllBrands()
    @Test
    public void happyPath_getAllBrands_returnsListOfBrandDTO(){
        // Arrange
        Brand b1 = new Brand("Nike");  b1.setBrandId("n");
        Brand b2 = new Brand("Adidas"); b2.setBrandId("a");
        Brand b3 = new Brand("Puma");   b3.setBrandId("p");

        List<Brand> brands = List.of(b1, b2, b3);

        List<BrandDTO> expected = brands.stream().map(b ->
                new BrandDTO(b.getBrandId(), b.getBrandName())).toList();

        when(repository.findAll()).thenReturn(brands);

        // Act
        List<BrandDTO> actual = service.getAllBrands();

        // Assert
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
    @Test
    public void getAllBrands_noBrands_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<BrandDTO> actual = service.getAllBrands();

        // Assert
        assertThat(actual).isEmpty();
    }


/*  --------------------
    Brand Update Tests
    -------------------- */

    @Test
    public void happyPath_update_returnsBrandDTO() {
        // Arrange
        String id = "id";
        Brand existing = new Brand("OldName");
        existing.setBrandId(id);

        Brand updatedEntity = new Brand("NewName");
        updatedEntity.setBrandId(id);

        BrandDTO input = new BrandDTO(id, "NewName");
        BrandDTO expected = new BrandDTO(id, "NewName");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Brand.class))).thenReturn(updatedEntity);

        // Act
        BrandDTO actual = service.update(id, input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void update_brandDoesNotExist_throwsException() {
        // Arrange
        when(repository.findById("missing")).thenReturn(Optional.empty());

        BrandDTO input = new BrandDTO("missing", "Name");

        // Assert
        assertThrows(ResponseStatusException.class,
                () -> service.update("missing", input));

        verify(repository, times(0)).save(any());
    }


/*  ----------------------
    Brand Deletion Tests
    ---------------------- */

    @Test
    public void happyPath_delete_callsRepositoryDelete() {
        // Arrange
        Brand brand = new Brand("something");
        brand.setBrandId("id");
        when(repository.findById("id")).thenReturn(Optional.of(brand));

        // Act
        service.delete("id");

        // Assert
        verify(repository).deleteById("id");
    }
    @Test
    public void delete_brandDoesNotExist() {
        // Act
        service.delete("id");

        // Assert
        verify(repository, times(0)).deleteById("id");
    }
}
