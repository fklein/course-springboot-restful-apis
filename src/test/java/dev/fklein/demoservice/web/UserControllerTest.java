package dev.fklein.demoservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.services.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;


    @MockBean
    UserService userService;

    List<User> users;

    @BeforeEach
    void setup() {
        users = new ArrayList<>();
        users.add(new User("uname1", "Firstname1", "Lastname1", "one@bla.com", "one", "ssn1"));
        users.add(new User("uname2", "Firstname2", "Lastname2", "two@bla.com", "two", "ssn2"));
        users.add(new User("uname3", "Firstname3", "Lastname3", "three@bla.com", "three", "ssn3"));
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(users);
        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("3"))
                .andExpect(content().json("""
                        [
                        {userName: uname1, firstName: Firstname1, lastName: Lastname1, email: one@bla.com, role: one, ssn: ssn1},
                        {userName: uname2, email: two@bla.com, role: two, ssn: ssn2},
                        {userName: uname3, email: three@bla.com, role: three, ssn: ssn3}
                        ]
                        """));
        verify(userService).getAllUsers();
    }

    @Test
    void getAllUsers_empty() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value("0"))
                .andExpect(content().json("[]"));
        verify(userService).getAllUsers();
    }

    @Test
    void createUser() throws Exception{
        User user = new User("dnukem", "Duke", "Nukem", "duke@nukem.com", "boss", "NUKE1");
        user.setId(999L);
        String userJson = """
            {
                "userName": "dnukem",
                "firstName": "Duke",
                "lastName": "Nukem",
                "email": "duke@nukem.com",
                "role": "boss",
                "ssn": "NUKE1"
            }
            """;
        when(userService.createUser(any())).thenReturn(user);
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(userJson))
                .andExpect(content().json("{id: 999}"));
        verify(userService).createUser(any(User.class));
    }

}