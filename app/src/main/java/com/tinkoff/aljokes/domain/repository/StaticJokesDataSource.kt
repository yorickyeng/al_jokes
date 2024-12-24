package com.tinkoff.aljokes.domain.repository

import com.tinkoff.aljokes.domain.entity.Joke

object StaticJokesDataSource {
    val initialJokes = listOf(
        Joke(
            1, "What does Santa suffer from if he gets stuck in a chimney?", "Claustrophobia!"
        ),
        Joke(2, "What’s a math teacher’s favorite place in NYC?", "Times Square."),
        Joke(3, "What do you call a fish wearing a bowtie?", "Sofishticated."),
        Joke(
            4,
            "What was the spider doing on the computer?",
            "He was making a web-site.",
        ),
        Joke(
            5,
            "Why did the student bring a ladder to school?",
            "Because he wanted to go to high school."
        ),
        Joke(
            6,
            "Why did the biologist break up with the physicist?",
            "There was no chemistry.",
        ),
        Joke(7, "What do you call cheese that isn’t yours?", "Nacho cheese."),
    )
}