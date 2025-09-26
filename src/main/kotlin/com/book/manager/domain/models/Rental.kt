package com.book.manager.domain.models

import java.time.LocalDateTime

data class Rental (
    val bookId: Long,
    val userId: Long,
    val rentalDateTime: LocalDateTime,
    val returnDeadline: LocalDateTime
)