package com.zserg.notepad.repository

import com.zserg.notepad.model.Note
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime
import java.util.*

interface NoteRepository: MongoRepository<Note, String>, NoteCustomRepository {
    fun findByCreatedAtBetween(fromDate: LocalDateTime, toDate: LocalDateTime): List<Note>
    @Aggregation(pipeline = arrayOf(
        "{\$match: {tags: \"flashcard\"}}",
        "{\$sample: {size: 1}}"
    ))
    fun findRandomFlashcard(): Note
    fun findByTitle(first: String): Optional<Note>
}