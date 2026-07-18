package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {

    fun getAllNotes(): Flow<List<NoteUiModel>> = dao.getAllNotes()
        .map { entities ->
            entities.map { entity ->
                NoteUiModel(
                    id = entity.id,
                    title = entity.title,
                    content = entity.content
                )
            }
        }

    suspend fun insert(note: NoteUiModel) {
        dao.insert(NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content
        ))
    }

    suspend fun update(note: NoteUiModel) {
        dao.update(NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content
        ))
    }

    suspend fun delete(note: NoteUiModel) {
        dao.delete(NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content
        ))
    }

    suspend fun getNoteById(id: Long): NoteUiModel? {
        val entity = dao.getNoteById(id)
        return entity?.let {
            NoteUiModel(
                id = it.id,
                title = it.title,
                content = it.content
            )
        }
    }
}