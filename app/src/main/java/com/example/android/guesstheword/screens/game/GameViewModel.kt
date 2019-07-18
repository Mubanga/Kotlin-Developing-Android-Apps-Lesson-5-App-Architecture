package com.example.android.guesstheword.screens.game

import android.app.Activity
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 *****************************************************************
 * Created By Mubanga on 7/17/2019
 *****************************************************************
 */

/**
 *  Buzzing Variables
 */

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern: LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}


class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

//    // The current score
//    private var score = 0

    /**
     *  Live Data Variables
     */
    // The Current Word
    private val _Word by lazy { MutableLiveData<String>() }
    val Word: LiveData<String>
        get() = _Word

    // The Current Score
    private val _Score by lazy { MutableLiveData<Int>() }
    val Score: LiveData<Int>
        get() = _Score

    private val _isGameFinished by lazy { MutableLiveData<Boolean>() }
    val GameFinished: LiveData<Boolean>
        get() = _isGameFinished

    private val _CurrentTime by lazy { MutableLiveData<Long>() }
    val CurrentTime: LiveData<Long>
        get() = _CurrentTime

    private var _Timer: CountDownTimer

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     *  GameFragment UI Widgets
     */
    val _CurrentTimeString = Transformations.map(_CurrentTime) { currentTime ->
        DateUtils.formatElapsedTime(currentTime)
    }


    /**
     *  Event Variables
     */
    private val _isBuzzerPatternChanged by lazy { MutableLiveData<Boolean>() }
    val BuzzerPatern: LiveData<Boolean>
        get() = _isBuzzerPatternChanged

    private val _CurrentBuzzerPattern by lazy { MutableLiveData<BuzzType>() }
    val CurrentBuzzerPattern: LiveData<BuzzType>
        get() = _CurrentBuzzerPattern

    val _BuzzArrayType = Transformations.map(_CurrentBuzzerPattern, { BuzzArrayType ->
        when (BuzzArrayType) {
            BuzzType.NO_BUZZ -> NO_BUZZ_PATTERN
            BuzzType.GAME_OVER -> GAME_OVER_BUZZ_PATTERN
            BuzzType.COUNTDOWN_PANIC -> PANIC_BUZZ_PATTERN
            BuzzType.CORRECT -> CORRECT_BUZZ_PATTERN
        }
    })

    private val _isPanicModeBuzz by lazy { MutableLiveData<Boolean>() }
    private val _isGameOverBuzz by lazy { MutableLiveData<Boolean>() }
    private val _isCorrectAnswerBuzz by lazy { MutableLiveData<Boolean>() }
    private val _isIncorrectAnswerBuzz by lazy { MutableLiveData<Boolean>() }

    init {
        Timber.d(": GameViewModel Created")

        _isGameFinished.value = false
        _isCorrectAnswerBuzz.value = false
        _isGameOverBuzz.value = false
        _isIncorrectAnswerBuzz.value = false
        _isPanicModeBuzz.value = false
        _isBuzzerPatternChanged.value = false
        _CurrentBuzzerPattern.value = BuzzType.NO_BUZZ

        _Score.value = 0


        _Timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisecondsUntilFinished: Long) {
                _CurrentTime.value = millisecondsUntilFinished / ONE_SECOND
                if (_CurrentTime.value!! in 1..10) {
                    _isPanicModeBuzz.value = true
                    _CurrentBuzzerPattern.value = BuzzType.COUNTDOWN_PANIC
                }
//                else
//                {
//                    _CurrentBuzzerPattern.value = BuzzType.NO_BUZZ
//                }
//                when(_CurrentTime.value)
//                {
//                    in (10* ONE_SECOND) downTo (0* ONE_SECOND) -> {
//                        _isBuzzerPatternChanged.value = true
//
//                    }
//                }
            }

            override fun onFinish() {
                _CurrentBuzzerPattern.value = BuzzType.GAME_OVER
                _CurrentTime.value = DONE
                _isGameFinished.value = true
                _CurrentBuzzerPattern.value = BuzzType.NO_BUZZ
            }


        }.start()




        resetList()
        nextWord()

    }


    override fun onCleared() {
        super.onCleared()
        Timber.d(": GameViewModel Cleared/Destroyed")
        _Timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun ResetBuzzerFlags() {
        // Current State Is Panick State
        if ((_CurrentTime.value!! <= 10) && (_CurrentTime.value!! > 0)) {

        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        //    score--
//        if(!(_isPanicModeBuzz.value!!))
//        {
//            _CurrentBuzzerPattern.value = BuzzType.NO_BUZZ
//        }
        _isIncorrectAnswerBuzz.value = true
        _Score.value = (_Score.value)?.minus(1)
        nextWord()

    }

    fun onCorrect() {
        //   score++
        if (!(_isPanicModeBuzz.value!!)) {
            _CurrentBuzzerPattern.value = BuzzType.CORRECT
        }
        _isCorrectAnswerBuzz.value = true
        _Score.value = (_Score.value)?.plus(1)
        nextWord()

    }

    fun onClearBuzzState() {

        if (!(_isPanicModeBuzz.value!!)) {
            _CurrentBuzzerPattern.value = BuzzType.NO_BUZZ
        }

    }


    /**
     * Moves to the next word in the list
     */
//    public  fun getWord() : String
//    {
//        return word
//    }
    public fun getWordLiveData(): LiveData<String> {
        return _Word
    }

    public fun getScoreLiveData(): LiveData<Int> {
        return _Score
    }

//    public fun getScore() : Int
//    {
//        return score
//    }

//    public fun isGameFinished(): Boolean {
//        return _isGameFinished
//    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        //   word = wordList.removeAt(0)
        _Word.value = wordList.removeAt(0)
    }
}