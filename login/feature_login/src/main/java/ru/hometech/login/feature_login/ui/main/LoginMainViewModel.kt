package ru.hometech.login.feature_login.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hometech.common.BaseIntent
import ru.hometech.common.BaseSideEffect
import ru.hometech.common.BaseViewModel
import ru.hometech.common.BaseViewState
import ru.hometech.login.core_login.impl.UserManager
import ru.hometech.login.feature_login.domain.DataValidator
import ru.hometech.login.feature_login.domain.PinValidator
import ru.hometech.login.feature_login.domain.ValidatorKey
import ru.hometech.login.feature_login.ui.create_user.Step
import javax.inject.Inject

// Состояние экрана входа
data class LoginViewState(
    val pin: String = "",
    val errorMessage: String? = null
) : BaseViewState()

// Сайд-эффекты экрана входа (например, навигация или сообщения)
sealed class LoginSideEffect : BaseSideEffect() {
    data class ShowErrorMessage(val message: String) : LoginSideEffect()
    data object NavigateToCreateUser: LoginSideEffect()
}

// Намерения, вызываемые пользовательскими действиями
sealed class LoginIntent : BaseIntent() {
    data class UpdatePassword(val password: String) : LoginIntent()
    data object GetUserId : LoginIntent()
    data object SubmitLogin : LoginIntent()
}

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val userManager: UserManager,
) : BaseViewModel<LoginViewState, LoginSideEffect, LoginIntent>(
    LoginViewState()
) {

    init {
        sendIntent(LoginIntent.GetUserId)
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        super.handleIntent(intent)
        when (intent) {

            is LoginIntent.UpdatePassword -> {
                setState {
                    copy(pin = intent.password)
                }
            }

            is LoginIntent.SubmitLogin -> {
                // Обработка попытки входа

            }

            LoginIntent.GetUserId -> {
                if (userManager.getUserId() == null) {
                    setSideEffect(LoginSideEffect.NavigateToCreateUser)
                }
            }
        }
    }
}