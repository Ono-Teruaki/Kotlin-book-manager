package com.book.manager.domain.repository

import com.book.manager.domain.models.Rental

interface RentalRepository {
    fun startRental(rental: Rental)
    fun endRental(bookId: Long)
}