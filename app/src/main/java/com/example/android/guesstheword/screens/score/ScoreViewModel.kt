package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 *****************************************************************
 * Created By Mubanga on 7/17/2019
 *****************************************************************
 */

class ScoreViewModel(FinalScore: Int) : ViewModel() {

    /**
     *  LiveData Object Initialisations
     */
    private val _FinalScore by lazy { MutableLiveData<Int>() }
    val FinalScore: LiveData<Int>
        get() = _FinalScore

    private val _EventPlayAgain by lazy { MutableLiveData<Boolean>() }
    val EventPlayAgain: LiveData<Boolean>
        get() = _EventPlayAgain

    init {
        Timber.d(" Final Score is ${FinalScore}")
        _FinalScore.value = FinalScore ?: 0
        _EventPlayAgain.value = false
    }

    /**
     *  Fires/Triggers The onPlayAgain Event
     */
    public fun onPlayAgain()
    {
        Timber.d("onPlayAgain Called Here")
        _EventPlayAgain.value = true
    }

    /**
     *  Terminates/Completes The onPlayAgain Event
     */
    public fun onPlayAgainComplete()
    {
        _EventPlayAgain.value = false
    }
}