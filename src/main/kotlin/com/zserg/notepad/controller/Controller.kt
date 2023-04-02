package com.zserg.notepad.controller

import com.zserg.notepad.model.Note
import com.zserg.notepad.model.NoteRequest
import com.zserg.notepad.model.NoteResponse
import com.zserg.notepad.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

import java.util.*

@Controller
@RequestMapping("notes")
class Controller {

    @Autowired
    private lateinit var noteService: NoteService

//    @GetMapping
//    fun index(model: Model): String {
//        val subtitles = flashcardsService.getRandomSubtitles()
//        model.addAttribute("subtitles", subtitles)
//        return "pairsubs"
//    }

    @PostMapping
    @ResponseBody
    fun upload(@RequestBody note: NoteRequest): String? {
        return noteService.saveNote(note)
    }

    @GetMapping
    @ResponseBody
    fun findAll(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fromDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) toDate: LocalDateTime?,
    ): List<NoteResponse> {
        return if(fromDate == null && toDate == null) {
            noteService.findAll().map { NoteResponse(it) }
        }else{
            noteService.find(fromDate, toDate).map { NoteResponse(it) }
        }
    }

//    @GetMapping("{id}", params = ["!textOnly"])
//    @ResponseBody
//    fun findById(@PathVariable id: String): Optional<PairSubs> {
//        return flashcardsService.findById(id)
//    }
//
//    @GetMapping("{id}", params = ["textOnly=true"])
//    @ResponseBody
//    fun findById1(
//        @PathVariable id: String,
//        @RequestParam textOnly: Boolean,
//        @RequestParam start: Long,
//        @RequestParam length: Long,
//    ): Optional<Subtitles> {
//        return flashcardsService.getSubtitles(id, start, length)
//    }
}