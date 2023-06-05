package com.zserg.notepad.service

import com.zserg.notepad.model.FlashcardUpdateRequest
import com.zserg.notepad.model.Note
import com.zserg.notepad.model.NoteRequest
import com.zserg.notepad.model.UploadFileResponse
import com.zserg.notepad.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*


@Service
class NoteService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    fun saveNote(note: NoteRequest): String? {
        val saved = noteRepository.save(note.toEntity())
        return saved.id
    }

    fun findById(id: String): Optional<Note> {
        val note = noteRepository.findById(id)
        return note
    }

    fun find(fromDate: LocalDateTime?, toDateTime: LocalDateTime?, title: String?, tags: List<String>): List<Note> {
        return noteRepository.findByParams(fromDate, toDateTime, title, tags)
    }

    fun getFlashcard(): Note {
        return noteRepository.findRandomFlashcard()
    }

    fun updateFlashCard(id: String, request: FlashcardUpdateRequest): Optional<Note> {
        return noteRepository.findById(id).map {
            it.title = request.front ?: it.title
            it.content = request.back ?: it.content
            noteRepository.save(it);
        }.flatMap { noteRepository.findById(id) }
    }


}