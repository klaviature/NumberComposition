package com.example.numbercomposition.presentation.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbercomposition.data.GameRepositoryImpl
import com.example.numbercomposition.domain.entities.GameSettings
import com.example.numbercomposition.domain.entities.Level
import com.example.numbercomposition.domain.entities.OptionColor
import com.example.numbercomposition.domain.entities.Question
import com.example.numbercomposition.domain.usecases.GenerateQuestionUseCase
import com.example.numbercomposition.domain.usecases.GetGameSettingsUseCase

class GameViewModel : ViewModel() {

    private val TAG = "GameViewModel"

    private val repository = GameRepositoryImpl()

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private val _questionNumber = MutableLiveData<Int>()
    val questionNumber: LiveData<Int> get() = _questionNumber

    private val _sum = MutableLiveData<Int>()
    val sum: LiveData<Int> get() = _sum

    private val _visibleNumber = MutableLiveData<Int>()
    val visibleNumber: LiveData<Int> get() = _visibleNumber

    private val _options = MutableLiveData<List<Int>>()
    val options: LiveData<List<Int>> get() = _options

    private val _optionColors = MutableLiveData<List<OptionColor>>()
    val optionColors: LiveData<List<OptionColor>> get() = _optionColors

    private val _minRightAnswersCount = MutableLiveData<Int>()
    val minRightAnswersCount: LiveData<Int> get() = _minRightAnswersCount

    private val _rightAnswersCount = MutableLiveData<Int>(0)
    val rightAnswersCount: LiveData<Int> get() = _rightAnswersCount

    private val _answersCount = MutableLiveData<Int>(0)
    val answersCount: LiveData<Int> get() = _answersCount

    private val _minRightAnswersProgress = MutableLiveData<Int>()
    val minRightAnswersProgress: LiveData<Int> get() = _minRightAnswersProgress

    private val _answersProgress = MutableLiveData<Int>()
    val answersProgress: LiveData<Int> get() = _answersProgress

    private val _timeRemainingFormatted = MutableLiveData<String>()
    val timeRemainingFormatted: LiveData<String> get() = _timeRemainingFormatted

    private val _timeRemainingProgress = MutableLiveData<Int>()
    val timeRemainingProgress: LiveData<Int> get() = _timeRemainingProgress

    private val _isRightAnswer = MutableLiveData<Boolean>()
    val isRightAnswer: LiveData<Boolean> get() = _isRightAnswer

    private val _isGameFinished = MutableLiveData<Unit>()
    val isGameFinished: LiveData<Unit> get() = _isGameFinished

    private lateinit var gameSettings: GameSettings
    private lateinit var question: Question
    private lateinit var timer: CountDownTimer

    private var currentQuestionNumber = 0
    private var currentAnswersCount = 0
    private var currentRightAnswersCount = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer(gameSettings.gameTimeSeconds)
        generateQuestion()
    }

    fun giveAnswer(numberInput: String) {
        val number = numberInput.toInt()
        checkAnswer(number)
        currentAnswersCount++
        _answersCount.value = currentAnswersCount
        _answersProgress.value = calculateProgress()
        if (_rightAnswersCount.value == _minRightAnswersCount.value) {
            finishGame()
        } else {
            generateQuestion()
        }
    }

    private fun checkAnswer(number: Int) {
        if (number == question.rightAnswer) {
            _isRightAnswer.value = true
            currentRightAnswersCount++
            _rightAnswersCount.value = currentRightAnswersCount
        } else {
            _isRightAnswer.value = false
        }
    }

    private fun calculateProgress(): Int {
        val rightAnswersCount = _rightAnswersCount.value!!
        val answersCount = _answersCount.value!!
        val ratio = rightAnswersCount.toDouble() / answersCount
        return (ratio * 100).toInt()
    }

    private fun startTimer(duration: Int) {
        timer = object : CountDownTimer(duration * MILLIS_IN_SECOND, MILLIS_IN_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val timeProgress = calculateTimeProgress(millisUntilFinished)
                _timeRemainingProgress.value = timeProgress
                val formattedTime = formatTime(millisUntilFinished)
                _timeRemainingFormatted.value = formattedTime
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    private fun getGameSettings(level: Level) {
        gameSettings = getGameSettingsUseCase(level)
        _minRightAnswersCount.value = gameSettings.minRightAnswersCount
        _minRightAnswersProgress.value = (gameSettings.minRightAnswersRatio * 100).toInt()
    }

    private fun calculateTimeProgress(millisUntilFinished: Long): Int {
        val seconds = millisUntilFinished / MILLIS_IN_SECOND
        val ratio = seconds.toDouble() / gameSettings.gameTimeSeconds
        return (ratio * 100).toInt()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val secondsUntilFinished = millisUntilFinished / MILLIS_IN_SECOND.toInt()
        val minutes = secondsUntilFinished / SECONDS_IN_MINUTE
        val seconds = secondsUntilFinished - minutes * SECONDS_IN_MINUTE
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun generateQuestion() {
        question = generateQuestionUseCase(gameSettings.maxSumValue)
        val colors = generateOptionColorsPositions()
        currentQuestionNumber++
        _questionNumber.value = currentQuestionNumber
        _sum.value = question.sum
        _visibleNumber.value = question.visibleNumber
        _options.value = question.options
        _optionColors.value = colors
    }

    private fun generateOptionColorsPositions(): List<OptionColor> {
        return OptionColor.entries.shuffled()
    }

    private fun finishGame() {
        _isGameFinished.value = Unit
    }

    private companion object {

        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}