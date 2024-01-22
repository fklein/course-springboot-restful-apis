package dev.fklein.demoservice.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTest {

    @Autowired
    MockMvc mock;

    @Test
    @DisplayName("GET on /helloworld should return \"Hello World\"")
    public void helloWorld_shouldSayHello() throws Exception {
        mock.perform(get("/helloworld")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

}