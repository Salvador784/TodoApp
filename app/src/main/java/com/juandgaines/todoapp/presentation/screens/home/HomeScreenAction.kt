package com.juandgaines.todoapp.presentation.screens.home

import com.juandgaines.todoapp.domain.Task

sealed interface HomeScreenAction {
    data class OnClickTask(val taskId:String):HomeScreenAction // Accion que muestra el detalle de una tarea
    data class OnToggleTask(val task:Task):HomeScreenAction // Accion que marca una tarea como completada o no completada
    data class OnDeleteTask(val task:Task):HomeScreenAction // Accion que elimina una tarea
    data object OnDeleteAllTasks:HomeScreenAction // Accion que elimina todas las tareas
    data object OnAddTask:HomeScreenAction // Accion que agrega una tarea -> sig. pantall
}