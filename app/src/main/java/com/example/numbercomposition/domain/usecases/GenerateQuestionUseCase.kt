package com.example.numbercomposition.domain.usecases

import com.example.numbercomposition.domain.entities.Question
import com.example.numbercomposition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, MAX_OPTIONS_NUMBER)
    }

    private companion object {

        private const val MAX_OPTIONS_NUMBER = 6
    }
}