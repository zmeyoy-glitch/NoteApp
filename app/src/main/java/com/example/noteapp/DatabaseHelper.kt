package com.example.noteapp

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Helper класс для инициализации и управления базой данных NoteApp
 */
object DatabaseHelper {
    
    private var database: NoteDatabase? = null
    
    /**
     * Инициализировать базу данных (Singleton pattern)
     */
    fun init(context: Context): NoteDatabase {
        if (database == null) {
            synchronized(DatabaseHelper::class) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        DatabaseConstants.DB_NAME
                    )
                        .addMigrations(MIGRATION_1_2) // Добавить миграции при необходимости
                        .fallbackToDestructiveMigration() // Или использовать destructive migration для тестов
                        .build()
                }
            }
        }
        
        return database!!
    }
    
    /**
     * Получить глобальный экземпляр базы данных
     */
    fun getDatabase(): NoteDatabase {
        if (database == null) {
            throw IllegalStateException("Database not initialized. Call init() first.")
        }
        return database!!
    }
    
    /**
     * Очистить все данные из базы данных
     */
    suspend fun clearAllData(context: Context) {
        val db = getDatabase()
        db.noteDao().clearAllNotes()
        
        // Также можно очистить другие таблицы, если они есть
        /*
        val tempTable = "notes_temp"
        db.execSQL("DELETE FROM $tempTable")
        */
    }
    
    /**
     * Проверить наличие базы данных на устройстве
     */
    fun isDatabaseExists(context: Context): Boolean {
        return File(
            context.filesDir,
            "${DatabaseConstants.DB_NAME}.db"
        ).exists()
    }
    
    /**
     * Получить размер базы данных в байтах
     */
    fun getDatabaseSize(context: Context): Long {
        val dbFile = File(
            context.filesDir,
            "${DatabaseConstants.DB_NAME}.db"
        )
        
        return if (dbFile.exists()) {
            dbFile.length()
        } else {
            0L
        }
    }
    
    /**
     * Миграция с версии 1 на 2 (пример)
     */
    private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
        override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
            // Пример миграции: добавление нового поля
            database.execSQL("ALTER TABLE notes ADD COLUMN is_archived INTEGER DEFAULT 0")
            
            // Или копирование данных в новую таблицу с новыми полями
            /*
            val tempTable = "notes_temp"
            database.execSQL("""
                CREATE TABLE $tempTable (
                    id INTEGER PRIMARY KEY,
                    title TEXT NOT NULL,
                    content TEXT NOT NULL,
                    created_at INTEGER NOT NULL,
                    updated_at INTEGER NOT NULL,
                    is_favorite INTEGER DEFAULT 0,
                    category_id INTEGER,
                    word_count INTEGER DEFAULT 0,
                    is_archived INTEGER DEFAULT 0
                )
            """)
            
            database.execSQL("""
                INSERT INTO $tempTable (id, title, content, created_at, updated_at, 
                                       is_favorite, category_id, word_count, is_archived)
                SELECT id, title, content, created_at, updated_at, 
                       is_favorite, category_id, word_count, 0
                FROM notes
            """)
            
            database.execSQL("DROP TABLE notes")
            database.execSQL("ALTER TABLE $tempTable RENAME TO notes")
            */
        }
    }
}

/**
 * Расширения для удобной работы с базой данных в Activity/Fragment
 */
fun Context.getNoteDatabase(): NoteDatabase {
    return DatabaseHelper.init(this)
}

/**
 * Расширение для получения DAO из базы данных
 */
fun NoteDatabase.getNoteDao(): NoteDao = noteDao()