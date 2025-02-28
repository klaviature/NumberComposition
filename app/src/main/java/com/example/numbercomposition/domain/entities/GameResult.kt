package com.example.numbercomposition.domain.entities

import java.io.Serializable

data class GameResult(
    val isWon: Boolean,
    val rightAnswersCount: Int,
    val answeredQuestionsCount: Int,
    val gameSettings: GameSettings
) : Serializable