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

//    fun uploadFile(file: MultipartFile): UploadFileResponse {
//        val bytes = file.inputStream.readAllBytes()
//        val content = String(bytes)
//        val questions = parse(content)
//        var updatedNotes = 0
//        questions.forEach {
//            val noteOptional = noteRepository.findByTitle(it.first)
//            if (noteOptional.isPresent) {
//                val note = noteOptional.get()
//                if (note.content != it.second) {
//                    note.content = it.second
//                    noteRepository.save(note)
//                    updatedNotes++
//                }
//            } else {
//                NoteRequest(
//                    id = null,
//                    title = it.first,
//                    content = it.second,
//                    tags = listOf("flashcard")
//                ).let { noteRepository.save(it.toEntity()) }
//            }
//        }
//
//        return UploadFileResponse(questions.size, updatedNotes)
//    }
//
//    fun parse(content: String): MutableList<Pair<String, String>> {
//        val pairs = mutableListOf<Pair<String, String>>()
//        var currentPair: Pair<String, String>? = null
//
//        content.split("\n").forEach { line ->
//            if (line.isBlank()) {
//                // Start a new pair
//                currentPair?.let { pairs.add(it) }
//                currentPair = null
//            } else if (line.startsWith("###")) {
//                // Add a new element to the current pair
//                val element = line.removePrefix("###").trim()
//                currentPair?.let { pair ->
//                    currentPair = pair.copy(second = pair.second + element)
//                }
//            } else {
//                // Start a new pair or add to the existing pair
//                val text = line.trim()
//                if (currentPair == null) {
//                    currentPair = text to ""
//                } else {
//                    currentPair = currentPair!!.copy(second = currentPair!!.second + text + "\n")
//                }
//            }
//        }
//
//        currentPair?.let { pairs.add(it) }
//        return pairs
//    }

}