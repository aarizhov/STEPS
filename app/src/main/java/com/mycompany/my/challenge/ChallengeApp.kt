package com.mycompany.my.challenge

import android.app.Application
import android.content.SharedPreferences

class ChallengeApp : Application() {
    companion object {
        lateinit var instance: ChallengeApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}