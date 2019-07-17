package com.example.android.guesstheword.screens.game

import android.widget.TextView
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

    // The current score
    private var score = 0

    private var _isGameFinished = false

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     *  GameFragment UI Widgets
     */
    private lateinit var  _ScoreText:TextView
    private lateinit var  _WordText:TextView

    init {
        Timber.d(": GameViewModel Created")
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
        score--
        nextWord()
    }

    fun onCorrect() {
        score++
        nextWord()
    }


    /**
     * Moves to the next word in the list
     */
    public  fun getWord() : String
    {
        return word
    }
    public fun getScore() : Int
    {
        return score
    }

    public fun isGameFinished() : Boolean
    {
        return _isGameFinished
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _isGameFinished = true
        } else {
            word = wordList.removeAt(0)
        }
    }
}