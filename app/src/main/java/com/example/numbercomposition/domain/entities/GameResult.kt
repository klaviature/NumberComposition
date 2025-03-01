package com.example.numbercomposition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isWon: Boolean,
    val rightAnswersCount: Int,
    val answeredQuestionsCount: Int,
    val grade: GameResultGrade,
    val gameSettings: GameSettings
) : Parcelable