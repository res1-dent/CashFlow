package ru.hometech.cashflow.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.hometech.common.Default
import ru.hometech.common.DispatchersProvider
import ru.hometech.common.IO
import ru.hometech.common.Main
import ru.hometech.common.Unconfined
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider = object : DispatchersProvider {
        override val io: CoroutineDispatcher = Dispatchers.IO
        override val default: CoroutineDispatcher = Dispatchers.Default
        override val main: CoroutineDispatcher = Dispatchers.Main
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    }

    @Provides
    @Singleton
    @IO
    fun provideIo(dispatchersProvider: DispatchersProvider): CoroutineDispatcher = dispatchersProvider.io

    @Provides
    @Singleton
    @Default
    fun provideDefault(dispatchersProvider: DispatchersProvider): CoroutineDispatcher = dispatchersProvider.default

    @Provides
    @Singleton
    @Unconfined
    fun provideUnconfined(dispatchersProvider: DispatchersProvider): CoroutineDispatcher = dispatchersProvider.unconfined

    @Provides
    @Singleton
    @Main
    fun provideMain(dispatchersProvider: DispatchersProvider): CoroutineDispatcher = dispatchersProvider.main
}