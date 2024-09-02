package com.Color.Sphere.Challenge.gamecolor.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private lateinit var prefs: SharedPreferences

    private const val KEY_START_STEP_COMPLETED = "StartStepCompleted"

    var startStepCompleted: Boolean
        get() = prefs.getBoolean(KEY_START_STEP_COMPLETED, false)
        set(value) {
            prefs.edit().putBoolean(KEY_START_STEP_COMPLETED, value).apply()
        }

    fun init(context: Context) {
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    var music: Float
        get() = prefs.getFloat("music", 1f)
        set(value) {
            prefs.edit().putFloat("music", value).apply()
        }
    var sound: Float
        get() = prefs.getFloat("sound", 1f)
        set(value) {
            prefs.edit().putFloat("sound", value).apply()
        }
}