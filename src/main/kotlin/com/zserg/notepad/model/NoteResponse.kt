package com.zserg.notepad.model

import java.time.LocalDateTime

data class NoteResponse(
    val id: String?,
    val title: String?,
    val content: String,
    val tags: List<String>?,
    val createdAt: LocalDateTime?
) {
    constructor(note: Note) : this(
        id = note.id,
        title = note.title,
        content = note.content,
        tags = note.tags,
        createdAt = note.createdAt
    )

    fun fromEntity(note: Note): NoteResponse {
        return NoteResponse(
            id = note.id,
            title = note.title,
            content = note.content,
            tags = note.tags,
            createdAt = note.createdAt
        )
    }
}
