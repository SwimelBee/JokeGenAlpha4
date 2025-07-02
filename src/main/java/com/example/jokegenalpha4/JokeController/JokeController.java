package com.example.jokegenalpha4.JokeController;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Service.JokeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST controller for joke operations.
 */
@RestController
@RequestMapping("/jokes")
public class JokeController {

    private static final Logger logger = Logger.getLogger(JokeController.class.getName());
    private final JokeService jokeserv;

    /**
     * Constructor with JokeService dependency.
     *
     * @param jokeserv The joke service
     */
    public JokeController(JokeService jokeserv) {
        this.jokeserv = jokeserv;
    }

    /**
     * Get all jokes.
     *
     * @return ResponseEntity with a list of all jokes
     */
    @GetMapping("/")
    public ResponseEntity<List<Joke>> getAllJokes() {
        try {
            List<Joke> jokes = jokeserv.getAllJokes();
            return ResponseEntity.ok(jokes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving all jokes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a random joke.
     *
     * @return ResponseEntity with a random joke, or 404 if no jokes exist
     */
    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke() {
        try {
            return jokeserv.getRandomJoke()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving random joke", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a new joke.
     *
     * @param joke The joke to add
     * @return ResponseEntity with the added joke
     */
    @PostMapping("/")
    public ResponseEntity<Joke> addJoke(@RequestBody Joke joke) {
        try {
            if (joke == null || joke.getJokeContent() == null || joke.getJokeContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Joke savedJoke = jokeserv.addJoke(joke);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedJoke);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid joke data", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding joke", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
