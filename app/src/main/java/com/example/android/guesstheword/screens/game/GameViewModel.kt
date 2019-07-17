package com.example.android.guesstheword.screens.game

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

    // The current word
    private var word = ""

//    // The current score
//    private var score = 0

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

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     *  GameFragment UI Widgets
     */
    private lateinit var _ScoreText: TextView
    private lateinit var _WordText: TextView

    init {
        Timber.d(": GameViewModel Created")
        _Score.value = 0
        resetList()
        nextWord()

    }

    override fun onCleared() {
        super.onCleared()
        Timber.d(": GameViewModel Cleared/Destroyed")
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
            _isGameFinished.value = true
        } else {
            //   word = wordList.removeAt(0)
            _Word.value = wordList.removeAt(0)
        }
    }
}