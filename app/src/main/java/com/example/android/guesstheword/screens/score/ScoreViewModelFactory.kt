package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 *****************************************************************
 * Created By Mubanga on 7/17/2019
 *****************************************************************
 */

/**
 *  For the ViewModelProvider.Factory We Need To Check If The modelClass isAssignableFrom
 *  The ViewModel Class (ScoreViewModel::ckass.java) Then You Return The ViewModel as The Generic
 *  Template Type "T"
 */
class ScoreViewModelFactory(private val FinalScore : Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java))
        {
            return ScoreViewModel(FinalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}