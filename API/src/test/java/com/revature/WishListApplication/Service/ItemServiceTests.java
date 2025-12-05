package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    // ItemService Tests Using Mocks
    @Mock
    ItemRepository repository;

    @InjectMocks
    ItemService service;

    // Item Creation
    @Test
    public void testCreation() {
        // Check successful creation
        // Check against duplicates
        // Check against missing Item information
    }

    // Item  Retrieval
    @Test
    public void testRetrieval() {
        // Check get all Items
        // Check get Item by Item name
        // Check get Item by Id
        // Check unsuccessful for each one (Item doesn't exist)
    }

    // Item  Updates
    @Test
    public void testUpdates() {
        // Check successful update
        // Check for when Item doesn't exist
        // Check for incomplete information in dto
    }

    // Item  Deletion
    @Test
    public void testDeletion() {
        // Check successful deletion
        // Check for when Item doesn't exist
    }
}
