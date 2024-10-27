package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.dto.UserRequestDTO;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO("test@example.com", "password123");
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        // Настройка репозитория для отсутствующего пользователя
        when(userRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.registerUser(userRequestDTO.getEmail(), userRequestDTO.getPassword());

        // Assert
        assertNotNull(result);
        assertEquals(userRequestDTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(email, "password123"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindUserById_UserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

//    @Test
//    void testFindUserById_UserDoesNotExist() {
//        // Arrange
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(null);
//
//        // Act
//        Optional<User> result = userService.findById(userId);
//
//        // Assert
//        assertNull(result);
//        verify(userRepository, times(1)).findById(userId);
//    }
//
    @Test
    void testUpdateUser_Success() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("test@example.com");

        User userDetails = new User();
        userDetails.setEmail("updated@example.com");
        userDetails.setPassword("newpassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        User result = userService.updateUser(userId, userDetails);

        // Assert
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        User userDetails = new User();
        userDetails.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, userDetails));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userId));
        verify(userRepository, never()).deleteById(userId);
    }
}
