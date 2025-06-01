package com.juandgaines.todoapp.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskLocalDataSource: TaskLocalDataSource
) : ViewModel() {

    var state by mutableStateOf(HomeDataState())
        private set
    private val eventChannel = Channel<HomeScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        state = state.copy(
            date = LocalDate.now().let {
                DateTimeFormatter.ofPattern("EEE, MMMM dd yyyy").format(it)
            }
        )
        taskLocalDataSource.taskFlow
            .onEach {
                val completedTask =
                    it.filter { task -> task.isCompleted }.sortedByDescending { it.date }
                val pendingTask = it.filter { task -> !task.isCompleted }
                    .sortedByDescending {
                        it.date
                    }

                state = state.copy(
                    summary = pendingTask.size.toString(),
                    completedTasks = completedTask,
                    pendingTasks = pendingTask
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HomeScreenAction) {
        viewModelScope.launch {
            when (action) {
                HomeScreenAction.OnDeleteAllTasks -> {
                    taskLocalDataSource.deleteAllTasks()
                    eventChannel.send(HomeScreenEvent.TaskAllDeleted)
                }

                is HomeScreenAction.OnDeleteTask -> {
                    taskLocalDataSource.deleteTask(action.task)
                    eventChannel.send(HomeScreenEvent.TaskDeleted)
                }

                is HomeScreenAction.OnToggleTask -> {
                    val updateTask = action.task.copy(isCompleted = !action.task.isCompleted)
                    taskLocalDataSource.updateTask(updateTask)
                    eventChannel.send(HomeScreenEvent.UpdatedTask)
                }

                else -> Unit
            }
        }

    }
}