package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.ItemDTO;
import com.revature.WishListApplication.Controller.ItemWOIDDTO;
import com.revature.WishListApplication.Model.Brand;
import com.revature.WishListApplication.Model.Item;
import com.revature.WishListApplication.Repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        // Arrange
        Item i1 = new Item("Sweater", new Brand("Lulu lemon"), new BigDecimal("199.99"));
        when(repository.save(i1)).thenReturn(i1);

        // Act

        ItemDTO result_test1 = service.create(new ItemWOIDDTO(i1.getItemName(),
                i1.getBrand(), i1.getItemPrice()));
        // should successfully create

        // Assert
        // Check item creation
        assertEquals(i1.getItemName(), result_test1.itemName());
        assertEquals(i1.getBrand(), result_test1.brand());
        assertEquals(i1.getItemPrice(), result_test1.itemPrice());

        Mockito.verify(repository).save(i1); // verify that i1 was saved to repo
    }

    // Item  Retrieval
    @Test
    public void happyPath_getById_returnsItemDTO() {
        // Arrange
        String id = "thisIsTheId";
        Brand brand = new Brand("Apple");
        Item savedItem = new Item("IPhone", brand, new BigDecimal("1000.00"));
        savedItem.setItemId(id);

        // Expected
        ItemDTO expected = new ItemDTO(id, "IPhone", brand, new BigDecimal("1000.00"));

        when(repository.findById(id)).thenReturn(Optional.of(savedItem));

        // Act
        ItemDTO actual = service.getById(id);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

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
