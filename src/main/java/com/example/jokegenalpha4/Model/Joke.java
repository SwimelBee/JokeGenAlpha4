package com.example.jokegenalpha4.Model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entity class representing a joke.
 */
@Entity
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jokeContent;

    /**
     * Default constructor required by JPA.
     */
    public Joke() {
    }

    /**
     * Constructor with jokeContent.
     * 
     * @param jokeContent The content of the joke
     */
    public Joke(String jokeContent) {
        this.jokeContent = jokeContent;
    }

    /**
     * Get the joke ID.
     * 
     * @return The joke ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the joke ID.
     * 
     * @param id The joke ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the joke content.
     * 
     * @return The joke content
     */
    public String getJokeContent() {
        return jokeContent;
    }

    /**
     * Set the joke content.
     * 
     * @param jokeContent The joke content to set
     * @throws IllegalArgumentException if jokeContent is null or empty
     */
    public void setJokeContent(String jokeContent) {
        if (jokeContent == null || jokeContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Joke content cannot be null or empty");
        }
        this.jokeContent = jokeContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joke joke = (Joke) o;
        return Objects.equals(id, joke.id) &&
               Objects.equals(jokeContent, joke.jokeContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jokeContent);
    }

    @Override
    public String toString() {
        return "Joke{" +
               "id=" + id +
               ", jokeContent='" + jokeContent + '\'' +
               '}';
    }
}
