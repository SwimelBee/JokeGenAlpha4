package com.example.jokegenalpha4.Model;

import jakarta.persistence.*;

@Entity
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jokeContent;

    public Joke() {
    }

    public Joke(String jokeContent) {
        this.jokeContent = jokeContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJokeContent() {
        return jokeContent;
    }

    public void setJokeContent(String jokeContent) {
        this.jokeContent = jokeContent;
    }
}
