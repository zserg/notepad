package com.zserg.notepad

import com.zserg.notepad.model.Note
import com.zserg.notepad.repository.NoteRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry

import org.springframework.test.context.DynamicPropertySource

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container


@DataMongoTest
class MongoTest {


    @Autowired
    private lateinit var noteRepository: NoteRepository

    @Test
    fun test() {
        val note = Note(
            title = "title",
            content = "content",
            tags = listOf("tag1", "tag2"),
            createdAt = null
        )
        val save = noteRepository.save(note)
        val all = noteRepository.findAll()
        val date = ObjectId(all[0].id).date
        val timestamp = ObjectId(all[0].id).timestamp


        assert(save.id != null)
    }
    companion object {

        @JvmStatic
        @Container
        private val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:6.0")
            .withExposedPorts(27017)

        @JvmStatic
        @DynamicPropertySource
        fun mongoDbProperties(registry: DynamicPropertyRegistry) {
            mongoDBContainer.start()
            registry.add("spring.data.mongodb.uri", { mongoDBContainer.getReplicaSetUrl() })
        }

//        init { MONGO_CONTAINER.start() }

    }

}