package ru.hometech.cashflow.logger

import ru.hometech.common.logger.Logger
import javax.inject.Inject

class ConsoleLogger @Inject constructor() : Logger {

    override suspend fun log() {
        println("logged")
    }
}
