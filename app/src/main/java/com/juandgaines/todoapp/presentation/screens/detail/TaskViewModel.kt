package com.juandgaines.todoapp.presentation.screens.detail

import TaskScreenState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import com.juandgaines.todoapp.presentation.navigation.TaskScreenDes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val taskLocalDataSource: TaskLocalDataSource
) : ViewModel() {

    private val taskData = saveStateHandle.toRoute<TaskScreenDes>()

    var state by mutableStateOf(TaskScreenState())
        private set

    private val eventsChannel = Channel<TaskEvent>()
    val events = eventsChannel.receiveAsFlow()
    private val canSaveTask = snapshotFlow { state.taskName.text.toString() }

    private var editableTask: Task? = null

    init {
        taskData.taskId?.let {
            viewModelScope.launch {
                taskLocalDataSource.getTaskById(taskData.taskId)?.let { task ->
                    editableTask = task
                    state = state.copy(
                        taskName = TextFieldState(task?.title ?: ""),
                        taskDescription = TextFieldState(task?.desc ?: ""),
                        category = task?.category,
                        isTaskDone = task?.isCompleted ?: false
                    )
                }
            }
        }
        canSaveTask.onEach {
            state = state.copy(
                canSaveTask = it.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAnction(action: ActionTask) {
        viewModelScope.launch {
            when (action) {
                is ActionTask.ChangeCategory -> {
                    state = state.copy(
                        category = action.category
                    )
                }

                is ActionTask.ChangeTaskDone -> {
                    state = state.copy(
                        isTaskDone = action.done
                    )
                }

                ActionTask.SaveTask -> {
                    editableTask?.let {
                        taskLocalDataSource.updateTask(
                            task = it.copy(
                                id = it.id,
                                title = state.taskName.text.toString(),
                                desc = state.taskDescription.text.toString(),
                                category = state.category,
                                isCompleted = state.isTaskDone
                            )
                        )
                    } ?: run {
                        val task = Task(
                            id = UUID.randomUUID().toString(),
                            title = state.taskName.text.toString(),
                            desc = state.taskDescription.text.toString(),
                            category = state.category,
                            isCompleted = state.isTaskDone
                        )
                        taskLocalDataSource.addTask(task)
                    }
                    eventsChannel.send(TaskEvent.TaskSave)

                }

                else -> Unit
            }
        }
    }
}