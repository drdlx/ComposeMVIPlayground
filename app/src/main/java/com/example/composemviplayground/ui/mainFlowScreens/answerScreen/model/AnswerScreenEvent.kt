package com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model

sealed class AnswerScreenEvent {
    class AnswerConfirmed(val answer: String) : AnswerScreenEvent()
}
