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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding
import timber.log.Timber

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var _ScoreViewModelFactory:ScoreViewModelFactory
    private lateinit var _ScoreViewModel:ScoreViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Timber.d(": onCreateView Called")
        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        _ScoreViewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)

        /** The ViewModelProviders Should Always Get A ViewModel Specified From The ViewModel::class.java
         *  The .of Associates The ViewModel With The Fragment Or Activity ("this")  And Can Even Specify
         *  The Specific ViewModelFactory To Use Upon Creation (_ViewModelFactory)
         */
        _ScoreViewModel = ViewModelProviders.of(this,_ScoreViewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = _ScoreViewModel
        binding.setLifecycleOwner(this)

//        _ScoreViewModel.FinalScore.observe(this, Observer { TheFinalScore ->
//            binding.scoreText.text = TheFinalScore.toString()
//        })

        _ScoreViewModel.EventPlayAgain.observe(this, Observer { isPlayAgain->
            if(isPlayAgain)
            {
                Timber.d("I AM PLAYING AGAIN")
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
             //   onPlayAgain()
                _ScoreViewModel.onPlayAgainComplete()
            }
        })
    //    binding.scoreText.text = scoreFragmentArgs.score.toString()

    //    binding.playAgainButton.setOnClickListener { _ScoreViewModel.onPlayAgain() }

        return binding.root
    }

//    private fun onPlayAgain() {
//        Timber.d(": PlayAgain Called Navigation: ---> Restart")
//        findNavController().navigate(ScoreFragmentDirections.actionRestart())
//    }
}
