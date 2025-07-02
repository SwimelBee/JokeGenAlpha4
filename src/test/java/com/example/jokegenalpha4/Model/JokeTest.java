package com.example.jokegenalpha4.Model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JokeTest {

    @Test
    public void testDefaultConstructor() {
        // when
        Joke joke = new Joke();
        
        // then
        assertThat(joke).isNotNull();
        assertThat(joke.getId()).isNull();
        assertThat(joke.getJokeContent()).isNull();
    }
    
    @Test
    public void testParameterizedConstructor() {
        // given
        String jokeContent = "Test joke content";
        
        // when
        Joke joke = new Joke(jokeContent);
        
        // then
        assertThat(joke).isNotNull();
        assertThat(joke.getId()).isNull();
        assertThat(joke.getJokeContent()).isEqualTo(jokeContent);
    }
    
    @Test
    public void testGettersAndSetters() {
        // given
        Joke joke = new Joke();
        Long id = 1L;
        String jokeContent = "Updated joke content";
        
        // when
        joke.setId(id);
        joke.setJokeContent(jokeContent);
        
        // then
        assertThat(joke.getId()).isEqualTo(id);
        assertThat(joke.getJokeContent()).isEqualTo(jokeContent);
    }
}