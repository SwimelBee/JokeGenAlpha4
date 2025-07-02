package com.example.jokegenalpha4.Service;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Repository.JokeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for joke operations.
 * Provides methods for retrieving, adding, and managing jokes.
 * Includes exception handling and logging for all operations.
 */
@Service
public class JokeService {

    private static final Logger logger = Logger.getLogger(JokeService.class.getName());
    private final JokeRepository jokerep;
    private final Random random = new Random();

    /**
     * Constructor with JokeRepository dependency.
     * 
     * @param jokerep The joke repository
     */
    JokeService(JokeRepository jokerep) {
        this.jokerep = jokerep;
    }

    /**
     * Get all jokes from the repository.
     * 
     * @return A list of all jokes, or an empty list if an error occurs
     */
    public List<Joke> getAllJokes() {
        try {
            return jokerep.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Error retrieving all jokes", e);
            return new ArrayList<>();
        }
    }

    /**
     * Get a random joke from the repository.
     * Uses the optimized findRandomJoke method from the repository.
     * 
     * @return An Optional containing a random joke, or empty if no jokes exist
     */
    public Optional<Joke> getRandomJoke() {
        try {
            // Use the optimized repository method to get a random joke directly
            Optional<Joke> randomJoke = jokerep.findRandomJoke();

            if (randomJoke.isEmpty()) {
                logger.log(Level.INFO, "No jokes available to select a random one");
            }

            return randomJoke;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Error retrieving random joke", e);
            return Optional.empty();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error when getting random joke", e);
            return Optional.empty();
        }
    }

    /**
     * Add a new joke to the repository.
     * 
     * @param joke The joke to add
     * @return The saved joke with generated ID
     * @throws IllegalArgumentException if joke is null
     * @throws RuntimeException if there's an error saving the joke
     */
    public Joke addJoke(Joke joke) {
        if (joke == null) {
            logger.log(Level.WARNING, "Attempted to add a null joke");
            throw new IllegalArgumentException("Joke cannot be null");
        }

        try {
            return jokerep.save(joke);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Error saving joke to database", e);
            throw new RuntimeException("Failed to save joke", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error when adding joke", e);
            throw new RuntimeException("Unexpected error occurred while saving joke", e);
        }
    }
}
