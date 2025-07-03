package com.example.jokegenalpha4.Service;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Repository.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JokeServiceIntegrationTest {

    @InjectMocks
    private JokeService jokeService;

    @Mock
    private JokeRepository jokeRepository;

    private Joke joke1;
    private Joke joke2;
    private List<Joke> allJokes;

    @BeforeEach
    public void setUp() {
        joke1 = new Joke("Why did the programmer quit his job? Because he didn't get arrays!");
        joke1.setId(1L);

        joke2 = new Joke("Why do Java developers wear glasses? Because they don't C#!");
        joke2.setId(2L);

        allJokes = Arrays.asList(joke1, joke2);

        when(jokeRepository.findAll()).thenReturn(allJokes);
        when(jokeRepository.findById(1L)).thenReturn(Optional.of(joke1));
        when(jokeRepository.findById(2L)).thenReturn(Optional.of(joke2));
        when(jokeRepository.save(any(Joke.class))).thenAnswer(invocation -> {
            Joke joke = invocation.getArgument(0);
            if (joke.getId() == null) {
                joke.setId(3L); // Simulate auto-generation of ID
            }
            return joke;
        });
    }

    @Test
    public void whenGetAllJokes_thenReturnAllJokes() {
        // when
        List<Joke> found = jokeService.getAllJokes();

        // then
        assertThat(found).hasSize(2);
        assertThat(found).containsExactlyInAnyOrderElementsOf(allJokes);
    }

    @Test
    public void whenGetRandomJoke_thenReturnJoke() {
        // when
        Optional<Joke> found = jokeService.getRandomJoke();

        // then
        assertThat(found).isPresent();
        assertThat(found.get()).isIn(allJokes);
    }

    @Test
    public void whenAddJoke_thenReturnSavedJoke() {
        // given
        Joke newJoke = new Joke("New joke content");

        // when
        Joke saved = jokeService.addJoke(newJoke);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getJokeContent()).isEqualTo("New joke content");
    }
}
