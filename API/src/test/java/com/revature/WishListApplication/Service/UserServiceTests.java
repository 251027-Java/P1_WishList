package com.revature.WishListApplication.Service;

import com.revature.WishListApplication.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    // UserService Tests Using Mocks
    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    // User Account Creation
    @Test
    public void testCreation() {
        // Check account creation
        // Check against duplicate accounts
        // Check against missing username or password
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
