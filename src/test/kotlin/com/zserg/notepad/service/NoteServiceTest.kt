package com.zserg.notepad.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import com.zserg.notepad.service.NoteService

class NoteServiceTest {

    @Test
    fun parse() {
        val service = NoteService()

        val content = """
           1. Can you describe a complex project you've worked on in the past? What were the challenges you faced and how did you overcome them?
###
adssdd
sadsd

2. How do you approach designing and architecting software solutions? Can you walk us through your process?


5. How do you ensure the quality and maintainability of your code? Describe any practices or tools you typically use for code reviews, testing, and documentation.
###
It's some answer

6. Have you ever had to optimize code for performance? How did you identify and address bottlenecks in the system?
        """.trimIndent()

        val parse: MutableList<Pair<String, String>> = service.parse(content)
        assertEquals(4, parse.size)
    }

}