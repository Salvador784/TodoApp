package com.juandgaines.todoapp.data

import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomTaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao,
    private val dispacherIO: CoroutineDispatcher = Dispatchers.IO,
) : TaskLocalDataSource {
    override val taskFlow: Flow<List<Task>>
        get() = taskDao.getAllTasks().map {
            it.map { taskEntity ->
                taskEntity.toTask()
            }
        }.flowOn(dispacherIO)

    override suspend fun addTask(task: Task) = withContext(dispacherIO){
        taskDao.upsertTask(TaskEntity.fromTask(task))
    }

    override suspend fun getTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) = withContext(dispacherIO){
        taskDao.upsertTask(TaskEntity.fromTask(task))
    }

    override suspend fun deleteTask(task: Task) = withContext(dispacherIO){
        taskDao.deleteTaskById(task.id)
    }

    override suspend fun deleteAllTasks() =withContext(dispacherIO){
        taskDao.deleteAllTasks()
    }

    override suspend fun getTaskById(taskId: String): Task? = withContext(dispacherIO) {
        taskDao.getTaskById(taskId)?.toTask()
    }

}