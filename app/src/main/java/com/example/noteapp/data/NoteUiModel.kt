package com.example.noteapp.data

data class NoteUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun fromEntity(entity: NoteEntity): NoteUiModel = NoteUiModel(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}