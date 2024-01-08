package ru.hometech.login.feature_login.ui.main.content

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.hometech.login.feature_login.ui.main.LoginIntent
import ru.hometech.login.feature_login.ui.main.LoginMainViewModel
import ru.hometech.login.feature_login.ui.main.LoginSideEffect
import ru.hometech.login.feature_login.ui.main.LoginViewState
import ru.hometech.login.feature_login.ui.navigation.LoginGraph

@Composable
fun LoginMainScreen(viewModel: LoginMainViewModel, onNavigate: (LoginGraph) -> Unit) {
    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                is LoginSideEffect.ShowErrorMessage -> {

                }

                LoginSideEffect.NavigateToCreateUser -> {
                    onNavigate(LoginGraph.CreateUser)
                }
            }
        }
    }
    val uiState by viewModel.state.collectAsState()
    LoginMainScreen(uiState = uiState, onSendIntent = viewModel::sendIntent)
}

@Composable
fun LoginMainScreen(uiState: LoginViewState, onSendIntent: (LoginIntent) -> Unit) {
    Column {
        Text(text = "Введите пин-код")
        TextField(value = uiState.pin, onValueChange = {
            onSendIntent(LoginIntent.UpdatePassword(it))
        })

        Button(onClick = { /*TODO*/ }) {

        }
    }
}