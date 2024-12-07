package com.tinkoff.aljokes.domain.repository

import com.tinkoff.aljokes.domain.entity.Jokes

object StaticJokesDataSource {
    val initialJokes = listOf(
        Jokes(
            1, "What does Santa suffer from if he gets stuck in a chimney?", "Claustrophobia!"
        ),
        Jokes(2, "What’s a math teacher’s favorite place in NYC?", "Times Square."),
        Jokes(3, "What do you call a fish wearing a bowtie?", "Sofishticated."),
        Jokes(
            4,
            "What was the spider doing on the computer?",
            "He was making a web-site.",
        ),
        Jokes(
            5,
            "Why did the student bring a ladder to school?",
            "Because he wanted to go to high school."
        ),
        Jokes(
            6,
            "Why did the biologist break up with the physicist?",
            "There was no chemistry.",
        ),
        Jokes(7, "What do you call cheese that isn’t yours?", "Nacho cheese."),
    )
}