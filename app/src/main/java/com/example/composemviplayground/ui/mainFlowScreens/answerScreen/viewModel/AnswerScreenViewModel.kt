package com.example.composemviplayground.ui.mainFlowScreens.answerScreen.viewModel

import androidx.lifecycle.ViewModel
import com.example.composemviplayground.services.AnswerService
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model.AnswerScreenEffect
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model.AnswerScreenEvent
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.model.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnswerScreenViewModel @Inject constructor(
    private val answerService: AnswerService,
): ViewModel() {

    companion object {
        private const val TOO_MANY_JOKES = "You've heard too many cheese jokes"
        private const val NACHO_CHEESE = "Nacho cheese"
    }

    private val coroutineScope = MainScope()

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private val _answerScreenEvents = Channel<AnswerScreenEffect>(Channel.BUFFERED)
    val answerScreenEvents = _answerScreenEvents.receiveAsFlow()

    fun onAction(answerScreenEvent: AnswerScreenEvent) {
        when (answerScreenEvent) {
            is AnswerScreenEvent.AnswerConfirmed -> {
                coroutineScope.launch {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                    withContext(Dispatchers.IO) { answerService.save(answerScreenEvent.answer) }
                    val text = if (answerScreenEvent.answer == NACHO_CHEESE) {
                        TOO_MANY_JOKES
                    } else {
                        NACHO_CHEESE
                    }
                    _viewState.value = _viewState.value.copy(textToDisplay = text)
                    _answerScreenEvents.send(AnswerScreenEffect.NavigateToResults)
                    _viewState.value = _viewState.value.copy(isLoading = false)
                }
            }
        }
    }
}