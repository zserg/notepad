package com.zserg.notepad.model

data class NoteRequest(
    val id: String?,
    val title: String?,
    val content: String,
    val tags: List<String>?,
    ) {

    fun toEntity(): Note {
        return Note(
            id = id,
            title = title ?: "",
            content = content,
            tags = tags ?: emptyList(),
        )
    }
}
