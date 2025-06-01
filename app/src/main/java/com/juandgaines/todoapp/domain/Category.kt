package com.juandgaines.todoapp.domain

enum class Category {
    WORK,
    PERSONAL,
    SCHOOL,
    OTHER;

    companion object{
        fun fromOrdinal(ordinal: Int): Category?{
            return entries.find {
                it.ordinal == ordinal
            }
        }
    }
}
