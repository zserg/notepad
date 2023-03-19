package com.zserg.notepad

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class NotepadApplication

fun main(args: Array<String>) {
	runApplication<NotepadApplication>(*args)
}
