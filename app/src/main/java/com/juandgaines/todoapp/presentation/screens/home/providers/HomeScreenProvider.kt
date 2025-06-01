package com.juandgaines.todoapp.presentation.screens.home.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.juandgaines.todoapp.domain.Category
import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.presentation.screens.home.HomeDataState

class HomeScreenProvider: PreviewParameterProvider<HomeDataState> {
    override val values: Sequence<HomeDataState>
        get() = sequenceOf(
            HomeDataState(
                date = "March 9, 2025",
                isLoading = false,
                completedTasks = completedTask,
                pendingTasks = pendingTask,
                summary = "List task"
            )
        )
}

val completedTask = mutableListOf<Task>()
    .apply {
        repeat(10){
            add(
                Task(
                    id = it.toString(),
                    title = "Task $it",
                    desc = "Description $it",
                    category = Category.PERSONAL,
                    isCompleted = false
                )
            )
        }
    }

val pendingTask = mutableListOf<Task>()
    .apply {
        repeat(20){
            add(
                Task(
                    id = (it+30).toString(),
                    title = "Task $it",
                    desc = "Description $it",
                    category = Category.OTHER,
                    isCompleted = true
                )
            )
        }
    }