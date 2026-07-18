package com.example.noteapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Модель заметки для Room базы данных
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    var title: String = "",
    
    var content: String = "",
    
    var createdAt: Date? = null,
    
    var updatedAt: Date? = null,
    
    var isFavorite: Boolean = false,
    
    var categoryId: Int? = null, // ID категории/тега
    
    var wordCount: Int = 0, // Количество слов для статистики
)

/**
 * Константы для работы с базой данных
 */
object DatabaseConstants {
    const val DB_NAME = "noteapp_db"
    const val TABLE_NOTES = "notes"
    
    // Поля таблицы
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_CONTENT = "content"
    const val COLUMN_CREATED_AT = "created_at"
    const val COLUMN_UPDATED_AT = "updated_at"
    const val COLUMN_IS_FAVORITE = "is_favorite"
    const val COLUMN_CATEGORY_ID = "category_id"
    const val COLUMN_WORD_COUNT = "word_count"
}