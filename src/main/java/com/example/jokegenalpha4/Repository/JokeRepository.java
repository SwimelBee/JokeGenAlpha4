package com.example.jokegenalpha4.Repository;

import com.example.jokegenalpha4.Model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, Long> {
}
