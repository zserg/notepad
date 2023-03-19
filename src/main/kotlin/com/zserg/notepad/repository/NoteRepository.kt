package com.zserg.notepad.repository

import com.zserg.notepad.model.Note
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, String> {
}