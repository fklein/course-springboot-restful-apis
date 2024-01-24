package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserExistsException;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;  // = mock(UserRepository.class);

    @InjectMocks
    UserServiceImpl userService;

    List<User> users;

    @BeforeEach
    void setup() {
        users = new ArrayList<>();
        users.add(new User("uname1", "Firstname1", "Lastname1", "one@bla.com", "one", "ssn1"));
        users.add(new User("uname2", "Firstname2", "Lastname2", "two@bla.com", "two", "ssn2"));
        users.add(new User("uname3", "Firstname3", "Lastname3", "three@bla.com", "three", "ssn3"));
    }

    @Test
    void getAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertThat(result)
                .hasSize(3)
                .containsAll(users);
        verify(userRepositoryMock).findAll();
    }

    @Test
    void getAllUser_empty() {
        when(userRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<User> result = userService.getAllUsers();
        assertThat(result).isEmpty();
        verify(userRepositoryMock).findAll();
    }

    @Test
    void createUser() throws UserExistsException {
        User user = users.get(0);
        user.setId(999L);
        when(userRepositoryMock.findByUserName(anyString())).thenReturn(null);
        when(userRepositoryMock.save(any(User.class))).thenAnswer(invocation -> {
            User u = (User) invocation.getArguments()[0];
            u.setId(123L);
            return u;
        });
        User result = userService.createUser(user);
        assertThat(result).isEqualTo(user);
        verify(userRepositoryMock).findByUserName(anyString());
        verify(userRepositoryMock).save(any(User.class));
    }

    @Test
    void createUser_UserExists() {
        when(userRepositoryMock.findByUserName(anyString())).thenReturn(new User());
        assertThrows(UserExistsException.class, () -> userService.createUser(users.get(0)));
        verify(userRepositoryMock).findByUserName(eq(users.get(0).getUserName()));
    }

    @Test
    void getUserById() throws Exception {
        Long userId = 99L;
        User user = users.get(0);
        user.setId(userId);
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        User result = userService.getUserById(userId);
        assertThat(result).isEqualTo(user);
        verify(userRepositoryMock).findById(userId);
    }

    @Test
    void getUserById_unknownId() {
        Long unknownId = 123L;
        when(userRepositoryMock.findById(unknownId)).thenReturn(Optional.empty());
        Exception ex = catchException(() -> userService.getUserById(unknownId));
        assertThat(ex).isInstanceOf(UserNotFoundException.class);
        // assertThatThrownBy(() -> userService.getUserById(unknownId)).isInstanceOf(UserNotFoundException.class);
        // assertThrows(UserNotFoundException.class, () -> userService.getUserById(unknownId));
        verify(userRepositoryMock).findById(unknownId);
    }

    @Test
    void updateUserById() throws Exception {
        Long userId = 99L;
        User user = users.get(0);
        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(user));
        when(userRepositoryMock.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        User result = userService.updateUserById(userId, user);
        assertThat(result).isEqualTo(user);
        verify(userRepositoryMock).findById(userId);
        verify(userRepositoryMock).save(user);
    }

    @Test
    void updateUserById_userUnknown() throws Exception {
        Long unknownId = 123L;
        User user = users.get(0);
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());
        // assertThatThrownBy(() -> userService.updateUserById(unknownId, user)).isInstanceOf(Exception.class);
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.updateUserById(unknownId, user));
        verify(userRepositoryMock).findById(unknownId);
    }
}