package com.example.noteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Основная база данных для NoteApp (Room Database)
 */
@Database(
    entities = [Note::class],
    version = 1, // Версия базы данных
    exportSchema = true // Экспорт схемы для миграций
)
abstract class NoteDatabase : RoomDatabase() {
    
    /**
     * Получить DAO заметок из базы данных
     */
    abstract fun noteDao(): NoteDao
    
    companion object {
        private const val DATABASE_NAME = "noteapp_db"
        
        /**
         * Создать или получить экземпляр базы данных (Singleton pattern)
         */
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        
        /**
         * Получить глобальный экземпляр базы данных
         */
        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NoteDatabase::class.java,
                            DATABASE_NAME
                        )
                            .addMigrations(MIGRATION_1_2) // Добавить миграции при необходимости
                            .fallbackToDestructiveMigration() // Или использовать destructive migration для тестов
                            .build()
                    }
                }
            }
            
            return INSTANCE!!
        }
        
        /**
         * Миграция с версии 1 на 2 (пример)
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
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
}

/**
 * Расширения для удобной работы с базой данных
 */
fun Context.getNoteDatabase(): NoteDatabase = NoteDatabase.getDatabase(this)