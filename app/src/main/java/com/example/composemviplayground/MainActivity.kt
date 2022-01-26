package com.example.composemviplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composemviplayground.services.AnswerService
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.view.AnswerView
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.view.QuestionView
import com.example.composemviplayground.ui.mainFlowScreens.answerScreen.viewModel.AnswerScreenViewModel
import com.example.composemviplayground.ui.theme.ComposeMVIPlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "question") {
                val viewModel: AnswerScreenViewModel by viewModels()
                composable("question") {
                    QuestionView(
                        viewModel = viewModel,
                        // You could pass the nav controller to further composables,
                        // but I like keeping nav logic in a single spot by using the hoisting pattern
                        // hoisting probably won't work as well in deep hierarchies,
                        // in which case CompositionLocal might be more appropriate
                        onConfirm = { navController.navigate("result") },
                    )
                }
                composable("result") {
                    AnswerView(
                        viewModel,
                        // in which case CompositionLocal might be more appropriate
                    )
                }
            }

        }
    }
}
