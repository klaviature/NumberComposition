package com.example.numbercomposition.domain.entities

data class GameResult(
    val isWon: Boolean,
    val rightAnswersCount: Int,
    val answeredQuestionsCount: Int,
    val gameSettings: GameSettings
)