package com.zserg.notepad

import com.zserg.notepad.model.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*


class ArticlesIntegrationTest : BaseIntegrationTest() {
    @Test
    fun shouldReturnSavedArticle() {
        // given
        val note = Note((UUID.randomUUID().toString()), "front", "back", listOf("flashcard"), null)
        noteRepository.save(note)

        // when
        val findRandomFlashcard = noteRepository.findRandomFlashcard()

        // then
        assertThat(note.id).isEqualTo(findRandomFlashcard.id)
    }
}