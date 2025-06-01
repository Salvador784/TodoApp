package com.juandgaines.todoapp.presentation.screens.home

sealed class HomeScreenEvent {
    data object UpdatedTask : HomeScreenEvent()
    data object TaskAllDeleted : HomeScreenEvent()
    data object TaskDeleted : HomeScreenEvent()
}