package com.juandgaines.todoapp.domain

import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val desc: String?,
    val category: Category?,
    val isCompleted: Boolean = false,
    val date: LocalDateTime = LocalDateTime.now(),
)