package ru.hometech.login.feature_login.ui.create_user

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.hometech.login.feature_login.domain.StepValidator


enum class Step {
    NAME,
    PIN,
    FINAL
}

class StepManager(
    private val initialStep: Step = Step.NAME,
    private val maxStep: Step = Step.FINAL,
    private val stepValidator: StepValidator
) {
    private var currentStep = initialStep
    private val dataMap: MutableMap<Step, String> = mutableMapOf()

    private val _currentStepStateFlow: MutableStateFlow<StepViewState> =
        MutableStateFlow(StepViewState(initialStep, null, false))

    val currentStepStateFlow = _currentStepStateFlow.asStateFlow()


    fun setStepData(step: Step, data: String) {
        dataMap[step] = data
        if (step == currentStep) {
            _currentStepStateFlow.update {
                it.copy(step = step, data = data, isValid = validateStep(step))
            }
        }
    }

    private fun validateStep(step: Step) : Boolean =
        stepValidator.validateStepData(step, getStepData(step))


    fun getStepData(step: Step): String? {
        return dataMap[step]
    }

    fun getCurrentStep(): Step {
        return currentStep
    }

    fun moveToNextStep() {
        if (currentStep < maxStep) {
            currentStep = Step.entries.toTypedArray()[currentStep.ordinal + 1]
            _currentStepStateFlow.update {
                getCurrentStepState()
            }
        } else {
            println("Cannot move to next step, already at the maximum step")
        }
    }

    fun moveToPrevStep() {
        if (currentStep > initialStep) {
            currentStep = Step.entries.toTypedArray()[currentStep.ordinal - 1]
            _currentStepStateFlow.update {
                it.copy(step = currentStep, data = getStepData(currentStep))
            }
        } else {
            println("Cannot move to prev step, already at the initial step")
        }
    }

    private fun getCurrentStepState(): StepViewState {
        return StepViewState(currentStep, getStepData(currentStep), isValid = validateStep(currentStep))
    }
}