package com.example.noteapp.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private var _notes: Flow<List<NoteUiModel>> = repository.getAllNotes()
        .catch { emit(emptyList()) }

    val notes: Flow<List<NoteUiModel>> = _notes

    fun loadNotes() {
        // Trigger reload if needed (e.g., after delete)
    }

    suspend fun insert(note: NoteUiModel) {
        repository.insert(note)
    }

    suspend fun update(note: NoteUiModel) {
        repository.update(note)
    }

    suspend fun delete(note: NoteUiModel) {
        repository.delete(note)
    }

    suspend fun getNoteById(id: Long): NoteUiModel? {
        return repository.getNoteById(id)
    }
}