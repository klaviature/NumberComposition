package com.example.numbercomposition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minRightAnswersCount: Int,
    val minRightAnswersRatio: Double,
    val gameTimeSeconds: Int
) : Parcelable {
    init {
        if (minRightAnswersRatio < 0 || minRightAnswersRatio > 1) {
            throw IllegalArgumentException("minRightAnswersRatio should be between 0 and 1")
        }
    }
}