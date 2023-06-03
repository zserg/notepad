package com.zserg.notepad

import com.zserg.notepad.repository.NoteRepository
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(
    TestContainersConfiguration::class
)
@ActiveProfiles("test")
class BaseIntegrationTest {
    @Autowired
    protected lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    protected lateinit var noteRepository: NoteRepository
//
//    @Autowired
//    protected var usersRepository: UsersRepository? = null
//    @AfterEach
//    fun cleanup() {
//        articlesRepository.deleteAll()
//        usersRepository.deleteAll()
//    }
}