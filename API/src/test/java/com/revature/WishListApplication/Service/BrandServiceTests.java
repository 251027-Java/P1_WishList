package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        // Check against duplicates
        // Check against missing Brand information
    }

    // Brand Retrieval
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
