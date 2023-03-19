package com.zserg.notepad.service

import com.zserg.notepad.model.Note
import com.zserg.notepad.model.NoteRequest
import com.zserg.notepad.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

//    fun getRandomSubtitles(): Subtitles? {
//        return flashcardsRepository.findAll().random()?.let {
//            getParallelSubtitles(it, -1, 20)
////            val subtitles = getParallelSubtitles(it, -1, 20)
////            SubtitlesView(
////                subtitles.title,
////                subtitles.subs1.joinToString(separator = "\n"),
////                subtitles.subs2.joinToString(separator = "\n"),
////            )
//        }
//    }

//    fun getSubtitles(id: String, start: Long, length: Long): Optional<Subtitles> {
//        return flashcardsRepository.findById(id).map { p ->
//            getParallelSubtitles(p, start, length)
//        }
//    }
//
//    private fun getParallelSubtitles(pairSubs: PairSubs, start: Long, length: Long): Subtitles {
//        val (startTime1, endTime1, startTime2, endTime2) = pairSubs.getIntervals(start, length)
//
//        val subtitles = Subtitles(
//            pairSubs.title,
//            getSubtitles(pairSubs.subs1.subs, startTime1, endTime1),
//            getSubtitles(pairSubs.subs2.subs, startTime2, endTime2),
//        )
//        return subtitles;
//
//    }
//
//    private fun getSubtitles(subs: List<SubItem>, startTime: LocalTime, endTime: LocalTime): List<String> {
//        return subs.filter { s -> s.start.isAfter(startTime) && s.end.isBefore(endTime) }
//            .map(SubItem::text)
//            .toList()
//    }


}