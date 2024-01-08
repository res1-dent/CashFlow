package ru.hometech.common

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Базовый класс для состояния
abstract class BaseViewState

// Базовый класс для сайд-эффектов
abstract class BaseSideEffect

// Базовый класс для намерений
abstract class BaseIntent

// Базовая ViewModel
abstract class BaseViewModel<S : BaseViewState, E : BaseSideEffect, I : BaseIntent> (
    initialState: S
) : ViewModel() {

    // Бэкпрессур не используется, так что используем Channel с вариантом конфликта DROP_OLDEST, чтобы сохранить только самые актуальные данные
    private val intentChannel = Channel<I>(Channel.RENDEZVOUS)

    // Поток состояний, начинается с начального состояния (переопределяется в наследниках)
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // Канал для сайд-эффектов, обработка потока будет эквивалентна SingleLiveEvent (событие не сохраняется для новых подписчиков)
    private val _sideEffect = Channel<E>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        Log.d(this::class.simpleName, "INIT ${this.hashCode()}")
        // Обработка входящих намерений
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                handleIntent(intent)
            }
        }
    }

    // Метод для отправки намерений в канал
    fun sendIntent(intent: I) {
        Log.d(this::class.simpleName, "sendIntent $intent")
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    // Базовый метод обработки отдельных намерений (должен быть переопределен наследниками)
    protected open suspend fun handleIntent(intent: I) {
        Log.d(this::class.simpleName, "handleIntent $intent")
    }

    // Вспомогательный метод для установки нового состояния
    protected fun setState(reduce: S.() -> S) {
        val newState = state.value.reduce()
        Log.d(this::class.simpleName, "setState $newState")
        _state.value = newState
    }

    // Вспомогательный метод для отправки новых сайд-эффектов
    protected suspend fun setSideEffect(newSideEffect: E) {
        Log.d(this::class.simpleName, "setSideEffect $newSideEffect")
        _sideEffect.send(newSideEffect)
    }
}
