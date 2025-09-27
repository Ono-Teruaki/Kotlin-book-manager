package com.book.manager.presentation.form

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class RentalStartRequest(
    val bookId: Long
)