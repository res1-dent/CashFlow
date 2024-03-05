package ru.hometech.login.feature_login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.hometech.login.feature_login.domain.DataValidator
import ru.hometech.login.feature_login.domain.NameValidator
import ru.hometech.login.feature_login.domain.PinValidator
import ru.hometech.login.feature_login.domain.StepValidator
import ru.hometech.login.feature_login.domain.ValidatorKey
import ru.hometech.login.feature_login.ui.create_user.Step
import ru.hometech.login.feature_login.ui.create_user.StepManager


@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    @ViewModelScoped
    fun provideStepManager(stepValidator: StepValidator): StepManager {
        return StepManager(stepValidator = stepValidator)
    }

    @Provides
    @ViewModelScoped
    @[IntoMap ValidatorKey(Step.NAME)]
    fun provideNameValidator(): DataValidator {
        return NameValidator()
    }

    @Provides
    @ViewModelScoped
    @[IntoMap ValidatorKey(Step.PIN)]
    fun providePinValidator(): DataValidator {
        return PinValidator()
    }
}