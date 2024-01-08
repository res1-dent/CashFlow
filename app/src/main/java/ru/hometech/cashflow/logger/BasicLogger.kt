package ru.hometech.cashflow.logger

import android.util.Log
import ru.hometech.common.logger.Logger
import javax.inject.Inject

class BasicLogger @Inject constructor(): Logger {
    override suspend fun log() {
        Log.e("!!!", "logged")
    }
}
