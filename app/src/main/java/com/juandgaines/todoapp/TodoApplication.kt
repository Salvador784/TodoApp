package com.juandgaines.todoapp

import android.app.Application
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class TodoApplication:Application()