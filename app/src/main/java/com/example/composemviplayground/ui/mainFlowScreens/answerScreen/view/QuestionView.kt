package com.example.composemviplayground.ui.mainFlowScreens.answerScreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model.AnswerScreenEffect
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model.AnswerScreenEvent
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.viewModel.AnswerScreenViewModel
import com.example.composemviplayground.ui.theme.ComposeMVIPlaygroundTheme
import kotlinx.coroutines.flow.onEach

@Composable
fun QuestionView(
    viewModel: AnswerScreenViewModel,
    onConfirm: () -> Unit
) {
    ComposeMVIPlaygroundTheme {
        val textFieldState = remember { mutableStateOf(TextFieldValue()) }

        val viewState = viewModel.viewState.collectAsState()
        val onConfirmClicked = { viewModel.onAction(AnswerScreenEvent.AnswerConfirmed(textFieldState.value.text)) }

        LaunchedEffect("key") {
            viewModel.answerScreenEvents
                .onEach {
                    when (it) {
                        AnswerScreenEffect.NavigateToResults -> onConfirm()
                    }
                }
                .collect {}
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "What do you call a mexican cheese?")
            TextField(
                value = textFieldState.value,
                onValueChange = { textFieldState.value = it }
            )
            if (viewState.value.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = onConfirmClicked ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}
