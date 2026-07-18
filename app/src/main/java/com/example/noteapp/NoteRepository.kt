package com.example.noteapp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Repository для абстракции над базой данных NoteDatabase
 * Предоставляет удобный API для работы с заметками
 */
class NoteRepository(private val noteDao: NoteDao) {
    
    // === Основной API для работы с заметками ===
    
    /**
     * Получить все заметки (реактивный поток)
     */
    fun getAllNotes(): Flow<List<Note>> = 
        noteDao.getAllNotes()
            .catch { exception ->
                android.util.Log.e("NoteRepository", "Error fetching notes", exception)
                emit(emptyList())
            }
    
    /**
     * Получить все заметки (синхронно, для однократного доступа)
     */
    suspend fun getAllNotesSync(): List<Note> = 
        noteDao.getAllNotesSync()
    
    // === Создание и обновление ===
    
    /**
     * Создать новую заметку
     */
    suspend fun createNote(note: Note): Long {
        return try {
            val id = noteDao.insert(note)
            
            // Обновить дату создания при создании
            updateNoteDate(id, System.currentTimeMillis())
            
            id
        } catch (e: Exception) {
            android.util.Log.e("NoteRepository", "Error creating note", e)
            0L
        }
    }
    
    /**
     * Обновить существующую заметку
     */
    suspend fun updateNote(note: Note): Boolean {
        return try {
            // Сначала обновляем дату изменения
            val currentTime = System.currentTimeMillis()
            noteDao.update(note.copy(updatedAt = java.util.Date(currentTime)))
            
            // Затем обновляем саму заметку с новой датой
            noteDao.update(note)
            
            true
        } catch (e: Exception) {
            android.util.Log.e("NoteRepository", "Error updating note", e)
            false
        }
    }
    
    /**
     * Удалить заметку
     */
    suspend fun deleteNote(id: Long): Boolean {
        return try {
            val note = noteDao.getNoteById(id) ?: return false
            
            noteDao.delete(note)
            true
        } catch (e: Exception) {
            android.util.Log.e("NoteRepository", "Error deleting note", e)
            false
        }
    }
    
    // === Поиск и фильтрация ===
    
    /**
     * Найти заметки по заголовку
     */
    fun searchByTitle(query: String): Flow<List<Note>> = 
        if (query.isBlank()) {
            noteDao.getAllNotes()
        } else {
            noteDao.searchByTitle("%$query%")
        }
    
    /**
     * Найти заметки по содержимому
     */
    fun searchByContent(query: String): Flow<List<Note>> = 
        if (query.isBlank()) {
            noteDao.getAllNotes()
        } else {
            noteDao.searchByContent("%$query%")
        }
    
    /**
     * Найти заметку по ID
     */
    suspend fun getNoteById(id: Long): Note? = 
        noteDao.getNoteById(id)
    
    // === Фильтрация по категориям/статусу ===
    
    /**
     * Получить все заметки для конкретной категории
     */
    fun getNotesByCategory(categoryId: Int?): Flow<List<Note>> {
        return if (categoryId == null) {
            noteDao.getAllNotes()
        } else {
            noteDao.getNotesByCategory(categoryId)
        }
    }
    
    /**
     * Получить все избранные заметки
     */
    fun getFavoriteNotes(): Flow<List<Note>> = 
        noteDao.getFavoriteNotes()
            .catch { exception ->
                android.util.Log.e("NoteRepository", "Error fetching favorites", exception)
                emit(emptyList())
            }
    
    /**
     * Пометить заметку как избранную/неизбранную
     */
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean): Boolean {
        return try {
            noteDao.toggleFavorite(id, isFavorite)
            
            // Обновить дату изменения
            val note = noteDao.getNoteById(id) ?: return false
            updateNoteDate(note.id, System.currentTimeMillis())
            
            true
        } catch (e: Exception) {
            android.util.Log.e("NoteRepository", "Error toggling favorite", e)
            false
        }
    }
    
    // === Статистика и аналитика ===
    
    /**
     * Получить общее количество заметок
     */
    fun getNotesCount(): Flow<Int> = 
        noteDao.getNotesCount()
            .catch { exception ->
                android.util.Log.e("NoteRepository", "Error fetching count", exception)
                emit(0)
            }
    
    /**
     * Получить количество заметок по категориям
     */
    fun getCategoryStats(): Flow<List<Pair<Int, Int>>> = 
        noteDao.getCategoryStats()
            .catch { exception ->
                android.util.Log.e("NoteRepository", "Error fetching category stats", exception)
                emit(emptyList())
            }
    
    // === Утилиты ===
    
    /**
     * Очистить все заметки из базы данных
     */
    suspend fun clearAllNotes(): Boolean {
        return try {
            noteDao.clearAllNotes()
            true
        } catch (e: Exception) {
            android.util.Log.e("NoteRepository", "Error clearing all notes", e)
            false
        }
    }
    
    /**
     * Обновить дату создания/изменения заметки
     */
    private suspend fun updateNoteDate(id: Long, timestamp: Long) {
        val note = noteDao.getNoteById(id) ?: return
        
        // Создаем копию с обновленной датой
        val updatedNote = note.copy(
            updatedAt = java.util.Date(timestamp),
            createdAt = if (note.updatedAt == null || note.createdAt != timestamp) 
                java.util.Date(timestamp) else note.createdAt
        )
        
        noteDao.update(updatedNote)
    }
}

/**
 * Расширения для удобной работы с Flow
 */
suspend fun <T> Flow<T>.asList(): List<T> = collectAsLazyDeferred().await()