package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.UserDTO;
import com.revature.WishListApplication.Controller.UserWOIDDTO;
import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;

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
public class UserServiceTests {
    // UserService Tests Using Mocks
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

/*  ---------------------
    User Account Creation
    --------------------- */

    @Test
    public void happyPath_create_returnsUserDTO() {
        // Arrange
        // Create user that will be added to db
        String id = "id";
        User user = new User("user", "secret");
        user.setUserId(id);
        // Convert user into user without id so it can be used as input
        UserWOIDDTO input = new UserWOIDDTO(user.getUserUsername(),
                user.getUserPassword());

        // expected result is userDTO
        UserDTO expected = new UserDTO(id, "user", "secret");

        when(repository.save(any())).thenReturn(user);

        // Method will check whether username has already been used
        when(repository.findByUserUsername("user")).thenReturn(Optional.empty());


        // Act
        UserDTO actual = service.create(input);

        // Assert
        // Actual userDTO should match the expected result
        assertThat(actual).isEqualTo(expected);
        // Verify that repository.save has only been run once
        verify(repository, times(1)).save(any(User.class));
    }
    @Test
    public void create_usernameAlreadyExists_throwsException() {
        // Arrange
        // Create user that we will try to add to db
        String id = "id";
        User user = new User("user", "secret");
        user.setUserId(id);
        // Convert user into user without id so it can be used as input
        UserWOIDDTO input = new UserWOIDDTO(user.getUserUsername(),
                user.getUserPassword());

        // Method will check whether username has already been used
        when(repository.findByUserUsername("user")).thenReturn(Optional.of(user));

        // Expected result is that a ResponseStatusException will be thrown
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            UserDTO actual = service.create(input);
        });

        // Check the exception message
        assertThat(exception.getMessage()).contains("Username already exists");

        // Verify that nothing has been saved to the repository
        verify(repository, times(0)).save(any());
    }


/*  ----------------------
    User Account Retrieval
    ---------------------- */

    // Check searchByUsername()
    @Test
    public void happyPath_searchByUsername_returnsUserDTO() {
        // Arrange
        String id = "test";
        String username = "username";

        // Load fake user into repo
        User user = new User("username", "somePassword");
        user.setUserId(id);
        when(repository.findByUserUsername(username)).thenReturn(Optional.of(user));

        // Expected: UserDTO with the requested username
        UserDTO expected = new UserDTO(id, "username", "somePassword");

        // Act
        UserDTO actual = service.searchByUsername(username);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void searchByUsername_usernameDoesNotExist_returnsNull() {
        // Arrange
        String username = "wrong";

        // Act
        // Since there is no account with username wrong, the UserDTO should be null
        UserDTO actual = service.searchByUsername(username);

        // Assert
        assertNull(actual);
    }

    // Check getById()
    @Test
    public void happyPath_getById_returnsUserDTO() {
        // Arrange
        String id = "thisIsTheId";
        User savedUser = new User("manu", "password123");
        savedUser.setUserId(id);

        UserDTO expected = new UserDTO(id, "manu", "password123");

        when(repository.findById(id)).thenReturn(Optional.of(savedUser));

        // Act
        UserDTO actual = service.getById(id);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void getById_idDoesNotExist_returnsNull() {
        // Arrange
        String id = "thisIsn'tTheId";

        //Act
        // Since this id is not tied to any account, the UserDTO should be null
        UserDTO actual = service.getById(id);

        // Assert
        assertNull(actual);
    }

    // Check getAllUsers()
    @Test
    public void happyPath_getAllUsers_returnsListOfUserDTO() {
        // Arrange
        User user1 = new User("brody", "password1");
        user1.setUserId("bId");
        User user2 = new User("alvey", "password2");
        user2.setUserId("aId");
        User user3 = new User("natalia", "password3");
        user3.setUserId("nId");

        List<User> userList = List.of(user1, user2, user3);

        // Expected: List<UserDTO> of all users
        List<UserDTO> expected = userList.stream().map( user -> {
            return new UserDTO(user.getUserId(), user.getUserUsername(),
                    user.getUserPassword());
        }).toList();

        when(repository.findAll()).thenReturn(userList);

        // Act
        List<UserDTO> actual = service.getAllUsers();

        // Assert
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
    @Test
    public void getAllUsers_noCurrentUsers_returnsEmptyListOfUserDTO() {
        // Act
        List<UserDTO> actual = service.getAllUsers(); // empty list

        // Assert
        assertThat(actual).isEmpty();
    }


/*  --------------------
    User Account Updates
    -------------------- */

    @Test
    public void happyPath_update_returnsUserDTO() {
        // Arrange
        // Create a user
        String id = "id";
        User user = new User("manasvini", "password");
        user.setUserId(id);

        // Expected result is userDTO with different username and password
        User updated = new User("manu", "secret");
        updated.setUserId(id);
        UserDTO expected = new UserDTO(updated.getUserId(),
                updated.getUserUsername(), updated.getUserPassword());

        // When repository checks if user exists, it should get our user
        when(repository.findById(id)).thenReturn(Optional.of(user));

        // When the updated user is saved, we return our updated user
        when(repository.save(any(User.class))).thenReturn(updated);

        // Act
        UserDTO actual = service.update(id, new UserDTO(id, "manu", "secret"));

        // Assert
        // Actual userDTO matches the updates we wanted
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void update_accountDoesNotExist_ThrowsException() {
        // Arrange
        String id = "id";
        // Create our input
        User updated = new User("manu", "secret");
        updated.setUserId(id);
        UserDTO input = new UserDTO(updated.getUserId(), updated.getUserUsername(),
                updated.getUserPassword());

        // When method checks if account exists, find that account does not exist
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Assert
        // Expected: a responseStatusException with a not_found message should be thrown
        assertThrows(ResponseStatusException.class, () -> {
            UserDTO result = service.update(id, input);
        }, "NOT_FOUND");

        // No calls should have been made to the repository
        verify(repository, times(0)).save(any(User.class));
    }


/*  ---------------------
    User Account Deletion
    --------------------- */

    @Test
    public void happyPath_delete_returnsVoid() {
        // Arrange
        String id = "id";

        // Act
        service.delete(id);

        // Assert
        // verify that repository's delete method was called
        verify(repository).deleteById(id);
    }
}
