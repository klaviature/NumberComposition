package com.example.numbercomposition

import com.example.numbercomposition.data.GameRepositoryImpl
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
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun someTest() {
        val gameRepository = GameRepositoryImpl()

        val maxSumValue = 20
        val question = GenerateQuestionUseCase(gameRepository).invoke(maxSumValue)
        println(question)
    }
}