package com.zserg.notepad.model

data class NoteResponse(
    val id: String?,
    val title: String?,
    val content: String,
    val tags: List<String>?,
    ) {

    fun fromEntity(note: Note): NoteResponse {
        return NoteResponse(
            id = note.id,
            title = note.title,
            content = note.content,
            tags = note.tags,
        )
    }
}
