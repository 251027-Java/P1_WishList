package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.BrandDTO;
import com.revature.WishListApplication.Controller.BrandWOIDDTO;
import com.revature.WishListApplication.Model.Brand;
import com.revature.WishListApplication.Repository.BrandRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {
    // BrandService Tests Using Mocks
    @Mock
    BrandRepository repository;

    @InjectMocks
    BrandService service;

    // Brand Creation
    @Test
    public void testCreation() {
        // Check successful creation

        // Arrange
        Brand b1 = new Brand("Lulu lemon");
        Mockito.when(repository.save(b1)).thenReturn(b1);

        // Act

        BrandDTO result_test1 = service.create(new BrandWOIDDTO(b1.getBrandName()));
        // should successfully create

        // Assert
        // Check brand creation
        assertEquals(b1.getBrandName(), result_test1.brandName());
        Mockito.verify(repository).save(b1); // verify that b1 was saved to repo
    }

    // Brand Retrieval
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
    public void edgeCase_IdDoesNotExist_getById_throwsException() {
        //
    }



    @Test
    public void testRetrieval() {
        // Check get all Brands
        // Check get Brand by Brand name
        // Check get Brand by Id
        // Check unsuccessful for each one (Brand doesn't exist)
    }

    // Brand Updates
    @Test
    public void testUpdates() {
        // Check successful update
        // Check for when Brand doesn't exist
        // Check for incomplete information in dto
    }

    // Brand  Deletion
    @Test
    public void testDeletion() {
        // Check successful deletion
        // Check for when Brand doesn't exist
    }
}
