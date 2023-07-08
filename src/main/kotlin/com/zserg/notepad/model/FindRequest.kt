package com.zserg.notepad.model

import java.time.LocalDateTime

data class FindRequest(
    val fromDate: LocalDateTime?,
    val toDate: LocalDateTime?,
    val title: String?,
    val tags: List<String>?,
    )
