package com.zserg.notepad.repository

import com.zserg.notepad.model.Note
import java.time.LocalDateTime

interface NoteCustomRepository {
    fun findByParams(fromDate: LocalDateTime?, toDate: LocalDateTime?, tags: List<String>): List<Note>
    fun findRandomOne(tags: List<String>): List<Note>
}