package com.zserg.notepad.controller

import com.zserg.notepad.model.*
import com.zserg.notepad.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*


@Controller
@RequestMapping("notes")
class Controller {

    @Autowired
    private lateinit var noteService: NoteService

    @PostMapping
    @ResponseBody
    fun addNote(@RequestBody note: NoteRequest): String? {
        return noteService.saveNote(note)
    }

    @GetMapping
    @ResponseBody
    fun findAll(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fromDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) toDate: LocalDateTime?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) tags: List<String>?
    ): List<NoteResponse> {
        return noteService.find(fromDate, toDate, title, tags ?: listOf()).map { NoteResponse(it) }
    }

    @PostMapping("/find")
    @ResponseBody
    fun findAllPost(@RequestBody findRequest: FindRequest): List<NoteResponse> {
        return noteService.find(findRequest.fromDate, findRequest.toDate, findRequest.title, findRequest.tags ?: listOf()).map { NoteResponse(it) }
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun findById(@PathVariable id: String): Optional<NoteResponse>? {
        return noteService.findById(id).map { NoteResponse(it) }
    }

    @GetMapping("/flashcard")
    @ResponseBody
    fun getFlashcard(): NoteResponse {
        val flashcard = noteService.getFlashcard()
        return NoteResponse(flashcard)
    }

    @PutMapping("/flashcard/{id}")
    @ResponseBody
    fun updateFlashcard(
        @PathVariable id: String,
        @RequestBody flashcardUpdateRequest: FlashcardUpdateRequest
    ): Optional<NoteResponse>? {
        return noteService.updateFlashCard(id, flashcardUpdateRequest)
            .map { NoteResponse(it) }
    }

}