/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var _GameViewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Timber.d(": onCreateView Called")
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Timber.d(": GameViewModel Called With Instance Of!")
        /** Makes A Call To ViewModelProviders To Return An Instance Of GameViewModel
         * To Associate It With The GameFragment For The Duration Of The FragmentLifeCycle
         */
        _GameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)


        /**
         *  Setup DataBinding Expressions For The GameFragment Layout
         */
        binding.gameViewModel = _GameViewModel
        binding.lifecycleOwner = this

        //Initialise First Word & Score
        binding.wordText.text = (_GameViewModel.getWordLiveData()?: " ").toString()
        binding.scoreText.text = (_GameViewModel.getScoreLiveData()?: 0).toString()
        binding.timerText.text = "0:00"

        /** *******************************************************
         *  Registering Observers
         **********************************************************/

        // Register The GameFragment As An Observer Of The Score From GameViewModel's Score LiveData Variable
//        _GameViewModel.getScoreLiveData().observe(this, Observer { NewScore->
//            Timber.d(": updateScoreText Current Score = ${NewScore}")
//            binding.scoreText.text = NewScore.toString()
//        })

        // Register The GameFragment As An Observer Of The Word From GameViewModel's Word LiveData Variable
//        _GameViewModel.getWordLiveData().observe(this, Observer { NewWord ->
//            Timber.d(": updateWordText Current Word Is = ${NewWord}")
//            binding.wordText.text = NewWord.toString()
//        })

        // Register The GameFragment As An Observer Of The Game State (isGameFinished) From GameViewModel's isGameFinished LiveData Variable
        _GameViewModel.GameFinished.observe(this, Observer { GameIsFinished ->
            if(GameIsFinished)
            {
                gameFinished()
            }
        })

        _GameViewModel.CurrentBuzzerPattern.observe(this, Observer { Buzzing ->
            when(Buzzing)
            {
                BuzzType.NO_BUZZ -> buzz(_GameViewModel._BuzzArrayType.value!!)
                BuzzType.COUNTDOWN_PANIC -> buzz(_GameViewModel._BuzzArrayType.value!!)
                BuzzType.CORRECT -> buzz(_GameViewModel._BuzzArrayType.value!!)
                BuzzType.GAME_OVER -> buzz(_GameViewModel._BuzzArrayType.value!!)
            }
        })


//        _GameViewModel.CurrentTime.observe(this, Observer { ElapsedTime ->
//            binding.timerText.text = DateUtils.formatElapsedTime(ElapsedTime)
//        })


//        binding.correctButton.setOnClickListener {
//            _GameViewModel.onCorrect()
////            if(_GameViewModel.isGameFinished())
////            {
////                gameFinished()
////            }
//
//        }
//
//        binding.skipButton.setOnClickListener {
//            _GameViewModel.onSkip()
////            if(_GameViewModel.isGameFinished()) {
////                gameFinished()
////            }
//        }

        return binding.root

    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Timber.d("Navigation Game ----> Score")
        // Elvis Operator " ?: " Will Produce Zero If The LiveData From ScoreLiveData Ends Up Being Null
            val action = GameFragmentDirections.actionGameToScore(_GameViewModel.getScoreLiveData().value?: 0)
            findNavController(this).navigate(action)
    }


    /** Methods for updating the UI **/

//    private fun updateWordText() {
//        Timber.d(": updateWordText Current Word Is = ${_GameViewModel.getWord()}")
//        binding.wordText.text = _GameViewModel.getWord()
//
//    }
//
//    private fun updateScoreText() {
//        Timber.d(": updateScoreText Current Score = ${_GameViewModel.getScore()}")
//        binding.scoreText.text = _GameViewModel.getScore().toString()
//    }
}
