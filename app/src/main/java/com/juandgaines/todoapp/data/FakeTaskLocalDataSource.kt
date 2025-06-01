package com.juandgaines.todoapp.data

import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.juandgaines.todoapp.presentation.screens.home.providers.completedTask
import com.juandgaines.todoapp.presentation.screens.home.providers.pendingTask

object FakeTaskLocalDataSource: TaskLocalDataSource {
    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList()) //lista de tarea para volverla un flow
    // flow-> Notifica cambios
    init {
        _tasksFlow.value = completedTask + pendingTask
    }

    override val taskFlow: Flow<List<Task>>
        get() = _tasksFlow

    override suspend fun addTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.add(task)
        delay(100)
        _tasksFlow.value = tasks
    }

    override suspend fun getTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1){
            tasks[index] = task
            delay(100)
            _tasksFlow.value = tasks
        }
    }

    override suspend fun deleteTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.remove(task)
        delay(100)
        _tasksFlow.value = tasks
    }

    override suspend fun deleteAllTasks() {
        _tasksFlow.value = emptyList()
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return _tasksFlow.value.firstOrNull{
            it.id == taskId
        }
    }
}