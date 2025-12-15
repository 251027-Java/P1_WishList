package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.BrandDTO;
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
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    // ItemService Tests Using Mocks
    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemService service;

/*  ---------------------
    Item Creation Tests
    --------------------- */

    @Test
    public void happyPath_create_returnsDTO() {
        // Arrange
        Brand brand = new Brand("Dell");
        ItemWOIDDTO dto = new ItemWOIDDTO("Laptop", brand, new BigDecimal("1000"));

        when(repository.save(any(Item.class))).thenAnswer(inv -> {
            Item saved = inv.getArgument(0, Item.class);
            saved.setItemId("generated123");
            return saved;
        });

        // Act
        ItemDTO result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("generated123", result.itemId());
        assertEquals("Laptop", result.itemName());
        verify(repository, times(1)).save(any(Item.class));
    }


/*  ----------------------
    Item Retrieval Tests
    ---------------------- */

    // Check getById
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
    public void getById_idDoesNotExist_returnsNull() {
        // Arrange
        when(repository.findById("missing")).thenReturn(Optional.empty());

        // Act
        ItemDTO actual = service.getById("missing");

        // Assert
        assertNull(actual);
    }

    // Check getByItemName
    @Test
    public void happyPath_searchItemname_returnsListOfItemDTO() {
        // Arrange
        Brand brand = new Brand("Prada");
        Item item = new Item("Laptop", brand, new BigDecimal("999.99"));
        item.setItemId("123");

        when(repository.findByItemNameStartingWithIgnoreCase("Laptop")).thenReturn(List.of(item));

        List<ItemDTO> expected = List.of(new ItemDTO(item.getItemId(),
                item.getItemName(), item.getBrand(), item.getItemPrice()));

        // Act
        List<ItemDTO> result = service.searchByItemname("Laptop");

        // Assert
        assertEquals(1, result.size());
        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
        verify(repository, times(1)).findByItemNameStartingWithIgnoreCase("Laptop");
    }
    @Test
    public void searchItemname_itemDoesNotExist_returnsEmptyList() {
        // Arrange
        when(repository.findByItemNameStartingWithIgnoreCase("missing")).thenReturn(List.of());

        // Act
        List<ItemDTO> actual = service.searchByItemname("missing");

        // Assert
        assertThat(actual).isEmpty();
    }

    // Check getAllItems
    @Test
    public void happyPath_getAllItems_returnsDTOList() {
        // Arrange
        Item item1 = new Item("Laptop", new Brand("Dell"), new BigDecimal("999.99"));
        item1.setItemId("123");
        Item item2 = new Item("Watch", new Brand("Rolex"), new BigDecimal("80.00"));
        item2.setItemId("5432");

        when(repository.findAll()).thenReturn(List.of(item1, item2));

        List<ItemDTO> expected = Stream.of(item1, item2).map(i -> {
            return new ItemDTO(i.getItemId(), i.getItemName(), i.getBrand(),
                    i.getItemPrice());
        }).toList();

        // Act
        List<ItemDTO> result = service.getAllItems();

        // Assert
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
    @Test
    public void getAllItems_noItems_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<ItemDTO> actual = service.getAllItems();

        // Assert
        assertThat(actual).isEmpty();
    }


/*  --------------------
    Item Update Tests
    -------------------- */

    @Test
    public void happyPath_update_returnItemDTO() {
        // Arrange
        Item existing = new Item("Laptop", new Brand("Dell"), new BigDecimal("999.99"));
        existing.setItemId("123");

        ItemDTO updated = new ItemDTO("123", "Laptop Pro", new Brand("HP"),
                new BigDecimal("1299.99"));

        when(repository.findById("123")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        // Act
        ItemDTO result = service.update("123", updated);

        // Assert
        assertThat(result).isEqualTo(updated);

    }
    @Test
    public void update_itemDoesNotExist_throwsException() {
        // Arrange
        when(repository.findById("missing")).thenReturn(Optional.empty());

        ItemDTO input = new ItemDTO("missing", "Dummy Name", new Brand("Fake Company"),
                new BigDecimal("1.00"));

        // Assert
        assertThrows(ResponseStatusException.class, () -> service.update("missing",
                input));

        verify(repository, times(0)).save(any());
    }

/*  --------------------
    Item Delete Tests
    -------------------- */

    @Test
    public void happyPath_delete_callsRepositoryDelete() {
        // Act
        Item item = new Item("something", new Brand("someBrand"), new BigDecimal("1.00"));
        item.setItemId("id");
        when(repository.findById("id")).thenReturn(Optional.of(item));

        // Act
        service.delete("id");

        // Assert
        verify(repository).deleteById("id");
    }
    @Test
    public void delete_itemDoesNotExist() {
        // Act
        service.delete("id");

        // Assert
        verify(repository, times(0)).deleteById("id");
    }
}
