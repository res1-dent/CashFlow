package ru.hometech.login.feature_login.ui.create_user.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.hometech.login.feature_login.ui.create_user.CreateUserIntent
import ru.hometech.login.feature_login.ui.create_user.CreateUserViewModel
import ru.hometech.login.feature_login.ui.create_user.CreateUserViewState
import ru.hometech.login.feature_login.ui.create_user.Step
import ru.hometech.login.feature_login.ui.create_user.StepViewState

@Composable
fun CreateUserScreen(viewModel: CreateUserViewModel) {
    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {

            }
        }
    }
    val uiState by viewModel.state.collectAsState()
    CreateUserScreen(uiState, viewModel::sendIntent)
}

@Composable
fun CreateUserScreen(
    createUserViewState: CreateUserViewState,
    onSendIntent: (CreateUserIntent) -> Unit
) {
    createUserViewState.stepViewState?.let { StepView(stepViewState = it, onSendIntent) }
}

@Composable
fun StepView(stepViewState: StepViewState, onSendIntent: (CreateUserIntent) -> Unit) {
    Column {
        when (stepViewState.step) {
            Step.NAME -> {
                NameStep(
                    currentName = stepViewState.data.orEmpty(),
                    isValid = stepViewState.isValid,
                    onSendIntent = onSendIntent
                )
            }

            Step.PIN -> {
                PinCodeStep(
                    currentPin = stepViewState.data.orEmpty(),
                    isValid = stepViewState.isValid,
                    onSendIntent = onSendIntent
                )
            }

            Step.FINAL -> {
                FinalStep(
                    name = stepViewState.data.orEmpty(),
                    onSendIntent = onSendIntent
                )
            }
        }
    }
}

@Composable
fun NameStep(
    currentName: String,
    isValid: Boolean,
    onSendIntent: (CreateUserIntent) -> Unit
) {
    Column {
        Text(text = "Введите имя")
        TextField(
            value = currentName,
            onValueChange = {
                onSendIntent(CreateUserIntent.UpdateName(it))
            },
            isError = !isValid
        )
        AnimatedVisibility(visible = currentName.isNotBlank()) {
            Button(onClick = { onSendIntent(CreateUserIntent.IncStep) }, enabled = isValid) {
                Text(text = "Далее")
            }
        }
    }
}

@Composable
fun PinCodeStep(
    currentPin: String,
    isValid: Boolean,
    onSendIntent: (CreateUserIntent) -> Unit
) {
    Column {
        Text(text = "Введите пин")
        TextField(
            value = currentPin,
            onValueChange = {
                onSendIntent(CreateUserIntent.UpdatePin(it))
            },
            isError = !isValid

        )
        Row {
            Button(onClick = { onSendIntent(CreateUserIntent.DecStep) }) {
                Text(text = "Назад")
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { onSendIntent(CreateUserIntent.IncStep) }, enabled = isValid) {
                Text(text = "Далее")
            }
        }

    }
}

@Composable
fun FinalStep(name: String, onSendIntent: (CreateUserIntent) -> Unit) {
    Column {
        Text(text = "Вот и всё, $name")
        Row {
            Button(onClick = { onSendIntent(CreateUserIntent.DecStep) }) {
                Text(text = "Назад")
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = {
                onSendIntent(CreateUserIntent.CreateUser)
            }) {
                Text(text = "Готово")
            }
        }
    }
}