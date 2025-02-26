package com.example.numbercomposition.domain.entities

data class GameSettings(
    val maxSumValue: Int,
    val minRightAnswersCount: Int,
    val minRightAnswersPercentage: Int,
    val gameTimeSeconds: Int
)