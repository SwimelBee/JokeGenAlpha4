package com.example.jokegenalpha4.Service;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Repository.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JokeService {

    private final JokeRepository jokerep;
    private final Random random = new Random();

    JokeService(JokeRepository jokerep) {
        this.jokerep = jokerep;
    }

    public List<Joke> getAllJokes() {
        return jokerep.findAll();
    }

    public Optional<Joke> getJokeById(Long id) {
        return jokerep.findById(id);
    }

    public Optional<Joke> getRandomJoke() {
        List<Joke> jokes = jokerep.findAll();
        if (jokes.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = random.nextInt(jokes.size());
        return Optional.of(jokes.get(randomIndex));
    }

    public Joke addJoke(Joke joke) {
        return jokerep.save(joke);
    }
}
