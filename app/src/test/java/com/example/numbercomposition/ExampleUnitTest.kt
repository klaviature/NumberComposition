package com.example.numbercomposition

import com.example.numbercomposition.data.GameRepositoryImpl
import com.example.numbercomposition.domain.entities.GameResultGrade
import com.example.numbercomposition.domain.entities.GameSettings
import com.example.numbercomposition.domain.usecases.GenerateQuestionUseCase
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun lost_grade() {
        val gameSettings = GameSettings(100, 10, 0.2, 10)
        val grade = GameResultGrade.getGrade(gameSettings.minRightAnswersRatio, 10, 1)
        assertEquals(GameResultGrade.LOST, grade)
    }

    @Test
    fun could_be_better_grade() {
        val gameSettings = GameSettings(100, 10, 0.2, 10)
        val grade = GameResultGrade.getGrade(gameSettings.minRightAnswersRatio, 10, 3)
        assertEquals(GameResultGrade.COULD_BE_BETTER, grade)
    }

    @Test
    fun good_grade() {
        val gameSettings = GameSettings(100, 10, 0.2, 10)
        val grade = GameResultGrade.getGrade(gameSettings.minRightAnswersRatio, 10, 6)
        assertEquals(GameResultGrade.GOOD, grade)
    }

    @Test
    fun perfect_grade() {
        val gameSettings = GameSettings(100, 10, 0.2, 10)
        val grade = GameResultGrade.getGrade(gameSettings.minRightAnswersRatio, 10, 10)
        assertEquals(GameResultGrade.PERFECT, grade)
    }

    @Test
    fun some() {
        assertThrows(IllegalArgumentException::class.java) {
            GameSettings(100, 10, 1.2, 10)
        }
    }

    @Test
    fun some2() {
        assertThrows(IllegalArgumentException::class.java) {
            GameSettings(100, 10, -0.2, 10)
        }
    }
}