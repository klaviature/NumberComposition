package com.example.numbercomposition.domain.entities

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minRightAnswersCount: Int,
    val minRightAnswersPercentage: Int,
    val gameTimeSeconds: Int
) : Serializable