package com.example.jokegenalpha4.Repository;

import com.example.jokegenalpha4.Model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JokeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JokeRepository jokeRepository;

    @Test
    public void whenFindById_thenReturnJoke() {
        // given
        Joke joke = new Joke();
        joke.setJokeContent("Why did the chicken cross the road? To get to the other side!");
        entityManager.persist(joke);
        entityManager.flush();

        // when
        Optional<Joke> found = jokeRepository.findById(joke.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getJokeContent()).isEqualTo(joke.getJokeContent());
    }

    @Test
    public void whenFindAll_thenReturnAllJokes() {
        // given
        Joke joke1 = new Joke();
        joke1.setJokeContent("Joke 1");
        entityManager.persist(joke1);

        Joke joke2 = new Joke();
        joke2.setJokeContent("Joke 2");
        entityManager.persist(joke2);
        entityManager.flush();

        // when
        List<Joke> jokes = jokeRepository.findAll();

        // then
        assertThat(jokes).hasSize(2);
        assertThat(jokes).extracting(Joke::getJokeContent).containsExactlyInAnyOrder("Joke 1", "Joke 2");
    }

    @Test
    public void whenSave_thenJokeIsPersisted() {
        // given
        Joke joke = new Joke();
        joke.setJokeContent("New joke");

        // when
        Joke saved = jokeRepository.save(joke);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(entityManager.find(Joke.class, saved.getId()).getJokeContent()).isEqualTo("New joke");
    }
}