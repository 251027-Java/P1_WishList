package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Controller.UserDTO;
import com.revature.WishListApplication.Controller.UserWOIDDTO;
import com.revature.WishListApplication.Model.User;
import com.revature.WishListApplication.Repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    // UserService Tests Using Mocks
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    // User to Dto for utility
    private UserDTO UserToDto(User user){
        return new UserDTO(user.getUserId(), user.getUserUsername(), user.getUserPassword());
    }

    // User Account Creation
    @Test
    public void testCreation() {
        // Arrange
        User u1 = new User("manu", "password");
        User u2 = new User("manu", "secret");

        User u3 = new User();
        u3.setUserPassword("abc");

        User u4 = new User();
        u3.setUserUsername("myuser");

        Mockito.when(repository.save(u1)).thenReturn(u1);
        Mockito.when(repository.save(u2)).thenReturn(u2);
        Mockito.when(repository.save(u3)).thenReturn(u3);
        Mockito.when(repository.save(u4)).thenReturn(u4);


        // Act
        UserDTO result_test1 = service.create(new UserWOIDDTO(u1.getUserUsername(),
                u1.getUserPassword())); // should successfully create
        service.create(new UserWOIDDTO(u1.getUserUsername(),
                u1.getUserPassword())); // DUPLICATE - should not create
        service.create(new UserWOIDDTO(u2.getUserUsername(),
                u2.getUserPassword())); // DUPLICATE USERNAME - should not create
        service.create(new UserWOIDDTO(u3.getUserUsername(),
                u3.getUserPassword())); // MISSING USERNAME - should not create
        service.create(new UserWOIDDTO(u4.getUserUsername(),
                u4.getUserPassword())); // MISSING PASSWORD - should not create


        // Assert

        // Check account creation
        assertEquals(u1.getUserUsername(), result_test1.userUsername());
        assertEquals(u1.getUserPassword(), result_test1.userPassword());
        Mockito.verify(repository).save(u1); // verify that u1 was saved to repo

        /*
         * Verify that save() has only been called once, since User objects with:
         * Existing usernames,
         * Same username and password,
         * or missing username/password
         * Should NOT be added to our DB
         */
        Mockito.verify(repository).save(any());

        // TODO: Possible exception handling to test for??

    }

    // User Account Retrieval
    @Test
    public void testRetrieval() {
        // Check get all users
        // Check get user by username
        // Check get user by Id
        // Check unsuccessful for each one (user doesn't exist)
    }

    // User Account Updates
    @Test
    public void testUpdates() {
        // Check successful update
        // Check for when user doesn't exist
        // Check for incomplete information in dto
    }

    // User Account Deletion
    @Test
    public void testDeletion() {
        // Check successful deletion
        // Check for when user doesn't exist
    }
}
