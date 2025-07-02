package com.example.jokegenalpha4.JokeController;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Service.JokeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JokeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public JokeService jokeService() {
            return Mockito.mock(JokeService.class);
        }
    }

    @Autowired
    private JokeService jokeService;

    @Test
    public void whenGetAllJokes_thenReturnJsonArray() throws Exception {
        // given
        Joke joke1 = new Joke("Joke 1");
        joke1.setId(1L);
        Joke joke2 = new Joke("Joke 2");
        joke2.setId(2L);
        List<Joke> allJokes = Arrays.asList(joke1, joke2);

        when(jokeService.getAllJokes()).thenReturn(allJokes);

        // when & then
        mockMvc.perform(get("/jokes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].jokeContent", is("Joke 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].jokeContent", is("Joke 2")));
    }

    @Test
    public void whenGetRandomJoke_thenReturnJson() throws Exception {
        // given
        Joke joke = new Joke("Random joke");
        joke.setId(1L);

        when(jokeService.getRandomJoke()).thenReturn(Optional.of(joke));

        // when & then
        mockMvc.perform(get("/jokes/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.jokeContent", is("Random joke")));
    }

    @Test
    public void whenAddJoke_thenReturnJson() throws Exception {
        // given
        Joke joke = new Joke("New joke");
        joke.setId(1L);

        when(jokeService.addJoke(any(Joke.class))).thenReturn(joke);

        // when & then
        mockMvc.perform(post("/jokes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"jokeContent\":\"New joke\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.jokeContent", is("New joke")));
    }
}
