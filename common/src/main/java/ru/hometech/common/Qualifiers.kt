package ru.hometech.common

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Unconfined

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default
