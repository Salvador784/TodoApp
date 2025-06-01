package com.juandgaines.todoapp.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.juandgaines.todoapp.domain.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: String): TaskEntity?

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Upsert // update o insert
    suspend fun upsertTask(task: TaskEntity)

}