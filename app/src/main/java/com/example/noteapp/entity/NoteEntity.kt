package com.example.noteapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val title: String,
    
    val content: String,
    
    val createdAt: Long, // Timestamp in milliseconds
    
    val updatedAt: Long? = null
)