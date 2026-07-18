package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {

    fun getAllNotes(): Flow<List<NoteUiModel>> = dao.getAllNotes().map { entities ->
        entities.map { entity ->
            NoteUiModel.fromEntity(entity)
        }
    }

    suspend fun insert(note: NoteUiModel) {
        val entity = NoteEntity.toEntity(note)
        dao.insert(entity)
    }

    suspend fun update(note: NoteUiModel) {
        val entity = NoteEntity.toEntity(note)
        dao.update(entity)
    }

    suspend fun delete(id: Long) {
        // Find and delete the note by ID
        val notes = getAllNotes().collectAsList()
            .blockingGet()
            .filter { it.id == id }
            .firstOrNull()
        
        if (notes != null) {
            dao.delete(NoteEntity.toEntity(notes))
        }
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}