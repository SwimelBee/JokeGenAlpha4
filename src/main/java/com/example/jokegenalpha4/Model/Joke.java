package com.example.jokegenalpha4.Model;

import jakarta.persistence.*;

@Entity
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jokeContent;
}
