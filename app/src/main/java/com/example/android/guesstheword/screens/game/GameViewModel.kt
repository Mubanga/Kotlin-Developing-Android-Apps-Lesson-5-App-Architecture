package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 *****************************************************************
 * Created By Mubanga on 7/17/2019
 *****************************************************************
 */
class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
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

    private lateinit var _Timer:CountDownTimer

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     *  GameFragment UI Widgets
     */
    private lateinit var _ScoreText: TextView
    private lateinit var _WordText: TextView

    init {
        Timber.d(": GameViewModel Created")
        _isGameFinished.value = false
        _Score.value = 0


        _Timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisecondsUntilFinished: Long) {
               _CurrentTime.value = millisecondsUntilFinished/ ONE_SECOND
            }
            override fun onFinish() {
               _CurrentTime.value = DONE
                _isGameFinished.value = true
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

    /** Methods for buttons presses **/

    fun onSkip() {
        //    score--
        _Score.value = (_Score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        //   score++
        _Score.value = (_Score.value)?.plus(1)
        nextWord()
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