package ru.hometech.cashflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.hometech.common.IO
import ru.hometech.common.logger.Logger
import ru.hometech.common.logger.LoggerType
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loggers:Map<LoggerType, @JvmSuppressWildcards Logger>,
) : ViewModel() {

    init {
        viewModelScope.launch {
            Log.e("!!!", "loggers = $loggers")
            loggers.values.forEach {
                it.log()
            }
        }
    }

}