package ru.hometech.login.feature_login.ui.create_user

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.hometech.common.BaseIntent
import ru.hometech.common.BaseSideEffect
import ru.hometech.common.BaseViewModel
import ru.hometech.common.BaseViewState
import ru.hometech.login.core_login.impl.User
import ru.hometech.login.core_login.impl.UserManager
import java.util.UUID
import javax.inject.Inject

data class StepViewState(
    val step: Step,
    val data: String?,
    val isValid: Boolean
)

data class CreateUserViewState(
    val stepViewState: StepViewState? = null
) : BaseViewState()


data class UserCreatedSideEffect(val message: String) : BaseSideEffect()


sealed class CreateUserIntent : BaseIntent() {
    data object IncStep : CreateUserIntent()
    data object DecStep : CreateUserIntent()
    data object CreateUser : CreateUserIntent()
    data class UpdateName(val name: String) : CreateUserIntent()
    data class UpdatePin(val pin: String) : CreateUserIntent()
}

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val stepManager: StepManager,
    private val userManager: UserManager
) : BaseViewModel<CreateUserViewState, UserCreatedSideEffect, CreateUserIntent>(CreateUserViewState()) {

    init {
        stepManager.currentStepStateFlow.onEach {
            setState { copy(stepViewState = it) }
        }.launchIn(viewModelScope)
    }

    override suspend fun handleIntent(intent: CreateUserIntent) {
        when (intent) {
            is CreateUserIntent.CreateUser -> {
                val name = checkNotNull(stepManager.getStepData(Step.NAME))
                val pin = checkNotNull(stepManager.getStepData(Step.PIN))
                userManager.createUser(
                    User(id = UUID.randomUUID().toString(), username = name, pinCode = pin)
                )
            }

            CreateUserIntent.DecStep -> {
                stepManager.moveToPrevStep()
            }
            CreateUserIntent.IncStep -> {
                stepManager.moveToNextStep()
            }
            is CreateUserIntent.UpdateName -> {
                stepManager.setStepData(Step.NAME, intent.name)
                stepManager.setStepData(Step.FINAL, intent.name)
            }

            is CreateUserIntent.UpdatePin -> {
                stepManager.setStepData(Step.PIN, intent.pin)
            }
        }
    }
}