package ru.hometech.login.feature_login.domain

import dagger.MapKey
import ru.hometech.login.feature_login.ui.create_user.Step
import javax.inject.Inject
import javax.inject.Qualifier

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ValidatorKey(val step: Step)

class StepValidator @Inject constructor(
    private val validatorMap: Map<Step, @JvmSuppressWildcards DataValidator>
) {
    fun validateStepData(step: Step, data: String?): Boolean {
        val validator = validatorMap[step]
        return validator?.isValid(data) ?: true // Если нет валидатора, то валидно
    }
}

interface DataValidator {
    fun isValid(data: String?): Boolean
}

class NameValidator : DataValidator {
    override fun isValid(data: String?): Boolean {
        return !data.isNullOrBlank() && data.length > 1
    }
}

class PinValidator : DataValidator {
    override fun isValid(data: String?): Boolean {
        return data?.toIntOrNull()?.let { it in (1000..9999) } ?: false
    }
}