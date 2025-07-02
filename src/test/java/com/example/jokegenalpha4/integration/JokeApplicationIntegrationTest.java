package com.example.jokegenalpha4.integration;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Repository.JokeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class JokeApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        jokeRepository.deleteAll();
    }

    @Test
    public void whenAddJoke_thenGetJoke_thenSuccess() throws Exception {
        // Add a joke
        Joke joke = new Joke("Integration test joke");
        
        mockMvc.perform(post("/jokes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joke)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jokeContent", is("Integration test joke")));

        // Get all jokes
        mockMvc.perform(get("/jokes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].jokeContent", is("Integration test joke")));
    }

    @Test
    public void whenGetRandomJoke_withNoJokes_thenReturnNull() throws Exception {
        // Ensure no jokes exist
        jokeRepository.deleteAll();
        
        // Try to get a random joke
        mockMvc.perform(get("/jokes/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void whenGetRandomJoke_withJokes_thenReturnJoke() throws Exception {
        // Add a joke
        Joke joke = new Joke("Random joke for testing");
        jokeRepository.save(joke);
        
        // Get a random joke
        mockMvc.perform(get("/jokes/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jokeContent", is("Random joke for testing")));
    }
}