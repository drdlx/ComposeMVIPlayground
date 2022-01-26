package com.example.composemviplayground.ui.mainFlowScreens.answerScreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.viewModel.AnswerScreenViewModel
import com.example.composemviplayground.ui.theme.ComposeMVIPlaygroundTheme

@Composable
fun AnswerView(
    viewModel: AnswerScreenViewModel
) {
    ComposeMVIPlaygroundTheme {
        val viewState = viewModel.viewState.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = viewState.value.textToDisplay)
        }
    }
}