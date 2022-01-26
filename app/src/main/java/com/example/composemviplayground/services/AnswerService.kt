package com.example.composemviplayground.services

import android.util.Log
import kotlinx.coroutines.delay
import javax.inject.Inject


class AnswerService @Inject constructor() {

    suspend fun save(answer: String) {
        println(answer)
        Log.v("Api call", "Make a call to an api")
        delay(1000)
    }
}