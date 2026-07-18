package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val dao: NoteDao
) {

    val notes: Flow<List<NoteEntity>> = dao.getAllNotes()

    suspend fun insert(note: NoteEntity) {
        dao.insert(note)
    }

    suspend fun update(note: NoteEntity) {
        dao.update(note)
    }

    suspend fun delete(note: NoteEntity) {
        dao.delete(note)
    }

    suspend fun getNoteById(id: Long): NoteEntity? = dao.getNoteById(id)
}