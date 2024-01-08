package ru.hometech.common.logger

import dagger.MapKey


interface Logger {
    suspend fun log()
}


enum class LoggerType {
    Basic, Console
}

@MapKey
annotation class LoggerKey(val logger: LoggerType)
