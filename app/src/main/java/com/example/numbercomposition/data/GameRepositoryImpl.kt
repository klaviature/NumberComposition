package com.example.numbercomposition.data

import com.example.numbercomposition.domain.entities.GameSettings
import com.example.numbercomposition.domain.entities.Level
import com.example.numbercomposition.domain.entities.Question
import com.example.numbercomposition.domain.repository.GameRepository
import kotlin.random.Random

class GameRepositoryImpl : GameRepository {

    private companion object {

        private const val MIN_SUM_VALUE = 2

        private const val MIN_OPTIONS_VALUE = 0
    }

    override fun generateQuestion(maxSumValue: Int, optionsCount: Int): Question {
//        if (optionsCount < maxSumValue) {
//            throw IllegalArgumentException("The options count must be greater or the same as the maximum sum.")
//        }

        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(sum + 1)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber

        options.add(rightAnswer)
        while (options.size < optionsCount) {
            options.add(Random.nextInt(MIN_OPTIONS_VALUE, sum + 1))
        }

        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                maxSumValue = 20,
                minRightAnswersCount = 4,
                minRightAnswersRatio = 0.5,
                gameTimeSeconds = 8
            )

            Level.EASY -> GameSettings(
                maxSumValue = 10,
                minRightAnswersCount = 5,
                minRightAnswersRatio = 0.5,
                gameTimeSeconds = 60
            )

            Level.NORMAL -> GameSettings(
                maxSumValue = 40,
                minRightAnswersCount = 6,
                minRightAnswersRatio = 0.7,
                gameTimeSeconds = 40
            )

            Level.HARD -> GameSettings(
                maxSumValue = 100,
                minRightAnswersCount = 10,
                minRightAnswersRatio = 0.9,
                gameTimeSeconds = 30
            )
        }
    }
}