package com.example.codingchallenge

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CodingChallengeApp: Application() {
    override fun onCreate() {
//        Log.d("CodingChallengeApp", "onCreate: CodingChallengeApp")
        super.onCreate()
    }
}
