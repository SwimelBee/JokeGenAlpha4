package com.example.jokegenalpha4.JokeController;

import com.example.jokegenalpha4.Model.Joke;
import com.example.jokegenalpha4.Service.JokeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/jokes")
class JokeController {

    private final JokeService jokeserv;


    JokeController(JokeService jokeserv) {
        this.jokeserv = jokeserv;
    }

    @GetMapping("/")
    public List<Joke> getAllJokes() {
        return jokeserv.getAllJokes();
    }

    @GetMapping ("/random")
    public Joke getRandomJoke() {
        return jokeserv.getRandomJoke().orElse(null);
    }

    @PostMapping("/")
    public Joke addJoke(Joke joke) {
        return jokeserv.addJoke(joke);
    }
}
