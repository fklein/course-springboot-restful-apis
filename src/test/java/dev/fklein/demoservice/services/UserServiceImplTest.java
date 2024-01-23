package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;
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

import static org.assertj.core.api.Assertions.assertThat;
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
    void createUser() {
    }
}