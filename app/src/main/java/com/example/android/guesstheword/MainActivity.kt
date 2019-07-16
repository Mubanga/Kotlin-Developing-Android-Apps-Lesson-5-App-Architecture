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

package com.example.android.guesstheword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

/**
 * Creates an Activity that hosts all of the fragments in the app
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d(": onRestart Called")
    }

    override fun onStart() {
        super.onStart()
        Timber.d(": onStart Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.d(": onStop Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d(": onPause Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.d(": onResume Called")
    }
}
