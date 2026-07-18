package com.example.noteapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) для работы с таблицей заметок в Room
 */
@Dao
interface NoteDao {
    
    // === Базовые операции CRUD ===
    
    /**
     * Вставить новую заметку и вернуть её ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long
    
    /**
     * Обновить существующую заметку по ID
     */
    @Update
    suspend fun update(note: Note)
    
    /**
     * Удалить заметку по ID
     */
    @Delete
    suspend fun delete(note: Note)
    
    /**
     * Получить все заметки из базы данных
     */
    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    /**
     * Получить все заметки (синхронно, для однократного доступа)
     */
    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    suspend fun getAllNotesSync(): List<Note>
    
    // === Поиск и фильтрация ===
    
    /**
     * Найти заметки по заголовку (частичное совпадение)
     */
    @Query("SELECT * FROM notes WHERE title LIKE :query ORDER BY created_at DESC")
    fun searchByTitle(query: String): Flow<List<Note>>
    
    /**
     * Найти заметки по содержимому (частичное совпадение)
     */
    @Query("SELECT * FROM notes WHERE content LIKE :query ORDER BY created_at DESC")
    fun searchByContent(query: String): Flow<List<Note>>
    
    /**
     * Найти заметки по ID
     */
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?
    
    // === Фильтрация по категориям/тегам ===
    
    /**
     * Получить все заметки для конкретной категории
     */
    @Query("SELECT * FROM notes WHERE category_id = :categoryId ORDER BY created_at DESC")
    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>
    
    // === Фильтрация по статусу ===
    
    /**
     * Получить все избранные заметки
     */
    @Query("SELECT * FROM notes WHERE is_favorite = 1 ORDER BY created_at DESC")
    fun getFavoriteNotes(): Flow<List<Note>>
    
    /**
     * Пометить заметку как избранную/неизбранную
     */
    @Query("UPDATE notes SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)
    
    // === Статистика и аналитика ===
    
    /**
     * Получить общее количество заметок
     */
    @Query("SELECT COUNT(*) FROM notes")
    fun getNotesCount(): Flow<Int>
    
    /**
     * Получить количество заметок по категориям
     */
    @Query("SELECT category_id, COUNT(*) as count FROM notes GROUP BY category_id ORDER BY count DESC")
    fun getCategoryStats(): Flow<List<Pair<Int, Int>>>
    
    // === Утилиты ===
    
    /**
     * Очистить все заметки из базы данных
     */
    @Query("DELETE FROM notes")
    suspend fun clearAllNotes()
}

/**
 * Расширения для работы с Flow (для реактивного доступа к данным)
 */
suspend fun <T> Flow<T>.asList(): List<T> = collectAsLazyDeferred().await()