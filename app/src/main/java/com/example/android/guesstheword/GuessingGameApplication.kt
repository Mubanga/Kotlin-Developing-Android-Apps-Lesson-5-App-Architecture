package com.example.android.guesstheword

import android.app.Application
import timber.log.Timber

/**
 *****************************************************************
 * Created By Mubanga on 7/17/2019
 *****************************************************************
 */
class GuessingGameApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialise Timber Logging
        Timber.plant(Timber.DebugTree())
    }
}