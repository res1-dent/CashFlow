package ru.hometech.login.core_login.impl

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private object UserPreferencesKeys {
    val UserId = stringPreferencesKey("user_id")
    val Username = stringPreferencesKey("username")
    val PinCode = stringPreferencesKey("pincode")
}

@Singleton
class UserManager @Inject constructor(context: Application) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    private val dataStore = context.dataStore

    val userPreferences: Flow<User> = dataStore.data
        .map { preferences ->
            // Получить данные пользователя из Preferences
            val id = preferences[UserPreferencesKeys.UserId] ?: ""
            val username = preferences[UserPreferencesKeys.Username] ?: ""
            val pinCode = preferences[UserPreferencesKeys.PinCode] ?: ""
            User(id, username, pinCode)
        }
        .filter { validateUserData(it) } // фильтрация пользователей по проверке валидации

    // Валидация данных пользователя
    private fun validateUserData(user: User): Boolean {
        return user.id.isNotBlank() && user.username.isNotBlank() && user.pinCode.isNotBlank() && user.pinCode.toIntOrNull()
            .let { it in 1000..9999 }
    }

    // Сохранение нового пользователя в DataStore
    suspend fun createUser(user: User) {
        dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.UserId] = user.id
            preferences[UserPreferencesKeys.Username] = user.username
            preferences[UserPreferencesKeys.PinCode] = user.pinCode
        }
    }

    suspend fun getUserId(): String? {
       return dataStore.data.first()[UserPreferencesKeys.UserId]
    }

    suspend fun verifyPinCode(pin: String) : Boolean =
        dataStore.data.first()[UserPreferencesKeys.PinCode].equals(pin)
}

data class User(val id: String, val username: String, val pinCode: String)