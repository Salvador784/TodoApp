package com.juandgaines.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Named("dispatcherIO")
    @Singleton
    fun provideDispatcherIO():CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named("dispatcherMain")
    fun provideDispatcherMain():CoroutineDispatcher = Dispatchers.Main
}