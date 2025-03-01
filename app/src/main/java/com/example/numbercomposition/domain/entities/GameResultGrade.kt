package com.example.numbercomposition.domain.entities

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
enum class GameResultGrade : Parcelable {

    LOST,
    COULD_BE_BETTER,
    GOOD,
    PERFECT;

    companion object {

        private const val GRADE_STEPS = 2

        fun getGrade(
            minRatio: Double,
            answeredQuestionsCount: Int,
            rightAnswersCount: Int
        ): GameResultGrade {
            val ratioRange = round(1 - minRatio)
            val step = round(ratioRange / GRADE_STEPS)

            val couldBeBetterRatio = round(minRatio)
            val goodRatio = round(couldBeBetterRatio + step)
            val perfectRatio = 1

            val ratio = rightAnswersCount.toDouble() / answeredQuestionsCount.toDouble()
            return when {
                ratio < couldBeBetterRatio -> LOST
                ratio < goodRatio -> COULD_BE_BETTER
                ratio < perfectRatio -> GOOD
                else -> PERFECT
            }
        }

        @SuppressLint("DefaultLocale")
        private fun round(value: Double): Double {
            return String.format(Locale.US, "%.6f", value).toDouble()
        }
    }
}