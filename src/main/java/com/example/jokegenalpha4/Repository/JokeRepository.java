package com.example.jokegenalpha4.Repository;

import com.example.jokegenalpha4.Model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Joke entity with exception handling and bug prevention.
 */
public interface JokeRepository extends JpaRepository<Joke, Long> {
    @Override
    List<Joke> findAll() throws DataAccessException;

    /**
     * Save a joke with explicit exception handling.
     * 
     * @param joke The joke to save
     * @return The saved joke
     * @throws DataAccessException If there's an issue accessing the database
     * @throws IllegalArgumentException If the joke is null
     */
    @Override
    <S extends Joke> S save(S joke) throws DataAccessException, IllegalArgumentException;

    /**
     * Find a random joke directly from the database.
     * This is more efficient than fetching all jokes and selecting one randomly.
     * 
     * @return An Optional containing a random joke, or empty if no jokes exist
     * @throws DataAccessException If there's an issue accessing the database
     */
    @Query(value = "SELECT * FROM joke ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Joke> findRandomJoke() throws DataAccessException;

    /**
     * Find jokes containing the given content.
     * 
     * @param content The content to search for
     * @return A list of jokes containing the given content
     * @throws DataAccessException If there's an issue accessing the database
     */
    List<Joke> findByJokeContentContaining(@Param("content") String content) throws DataAccessException;
}
