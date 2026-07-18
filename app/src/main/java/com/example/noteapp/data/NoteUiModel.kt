package com.example.noteapp.data

data class NoteUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    companion object {
        fun fromEntity(entity: NoteEntity): NoteUiModel {
            return NoteUiModel(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }

        fun toEntity(uiModel: NoteUiModel): NoteEntity {
            return NoteEntity(
                id = uiModel.id,
                title = uiModel.title,
                content = uiModel.content,
                createdAt = uiModel.createdAt,
                updatedAt = uiModel.updatedAt
            )
        }
    }
}