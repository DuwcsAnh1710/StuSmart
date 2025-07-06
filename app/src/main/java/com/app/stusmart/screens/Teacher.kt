package com.app.stusmart.screens

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class Teacher(
    @PrimaryKey
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val school: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) 