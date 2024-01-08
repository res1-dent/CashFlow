package ru.hometech.cashflow.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ru.hometech.cashflow.logger.BasicLogger
import ru.hometech.cashflow.logger.ConsoleLogger
import ru.hometech.common.logger.Logger
import ru.hometech.common.logger.LoggerKey
import ru.hometech.common.logger.LoggerType

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    @Binds
    @IntoMap
    @LoggerKey(LoggerType.Basic)
    fun bindBasicLogger(impl: BasicLogger): Logger

    @Binds
    @IntoMap
    @LoggerKey(LoggerType.Console)
    fun bindConsoleLogger(impl: ConsoleLogger): Logger
}