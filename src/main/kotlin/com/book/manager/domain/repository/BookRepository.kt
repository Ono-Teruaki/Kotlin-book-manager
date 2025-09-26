package com.book.manager.domain.repository

import com.book.manager.domain.models.BookWithRental

interface BookRepository {
    fun findALlWithRental(): List<BookWithRental>
}