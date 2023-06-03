package com.zserg.notepad

import com.fasterxml.jackson.databind.ObjectMapper
import com.zserg.notepad.model.FlashcardUpdateRequest
import com.zserg.notepad.model.Note
import com.zserg.notepad.model.NoteRequest
import com.zserg.notepad.repository.NoteRepository
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class YourControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var noteRepository: NoteRepository

    @Autowired
    private lateinit var om: ObjectMapper


//    @BeforeEach
//    fun setup() {
//        mongoDBContainer.start()
//    }
//
    @AfterEach
    fun teardown() {
        noteRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateDocument() {
        // Create a sample document in the test MongoDB
        val document = NoteRequest(null, "title", "content", listOf("tag1", "tag2"))

        // Perform an HTTP POST request to create the document
        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(document))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect { MockMvcResultMatchers.jsonPath("$.id").exists() }
            .andReturn()
        val id = result.response.contentAsString

        // Update the document
        val updateRequest = FlashcardUpdateRequest("front", "back")

        // Perform an HTTP PUT request to update the document
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/notes/flashcard/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updateRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())

        // Verify the updated document
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/notes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("front"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("back"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `file upload`() {
        val document = NoteRequest(null, "Some question", "Answer", listOf("flashcard"))

        // Perform an HTTP POST request to create the document
        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(document))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect { MockMvcResultMatchers.jsonPath("$.id").exists() }
            .andReturn()
        val id = result.response.contentAsString

        val content = """
           Some question
###
New answer

2. How do you approach designing and architecting software solutions? Can you walk us through your process?
        """.trimIndent()

        val file = MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, content.toByteArray())
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/notes/flashcard/upload")
            .file(file)
        mockMvc!!.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `find test 1`() {
        val note1 = Note(title = "title1", content = "content1", tags = listOf("tag1"), createdAt = LocalDateTime.now())
        val note2 = Note(title = "title2", content = "content2", tags = listOf("flashcard", "tag2"), createdAt = LocalDateTime.now().minusDays(2))
        val note3 = Note(title = "title3", content = "content3", tags = listOf("flashcard", "tag2"), createdAt = LocalDateTime.now().minusDays(2))
        noteRepository.saveAll(listOf(note1, note2, note3))

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/notes")
                .queryParam("tags", "flashcard")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize<String>(2)))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    fun `find test 2`() {
        val note1 = Note(title = "title1", content = "content1", tags = listOf("tag1"), createdAt = LocalDateTime.now())
        val note2 = Note(title = "title2", content = "content2", tags = listOf("flashcard", "tag2"), createdAt = LocalDateTime.now().minusDays(2))
        val note3 = Note(title = "title3", content = "content3", tags = listOf("flashcard", "tag2"), createdAt = LocalDateTime.now().minusDays(2))
        noteRepository.saveAll(listOf(note1, note2, note3))
        val findAll = noteRepository.findAll()

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/notes")
                .queryParam("fromDate", LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize<String>(1)))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    companion object {
        @Container
        private val mongoDBContainer = MongoDBContainer("mongo:latest")

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }
}
