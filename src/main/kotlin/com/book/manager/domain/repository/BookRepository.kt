package com.book.manager.domain.repository

import com.book.manager.domain.models.Book
import com.book.manager.domain.models.BookWithRental
import java.time.LocalDate

interface BookRepository {
    fun findALlWithRental(): List<BookWithRental>
    fun findWithRental(id: Long): BookWithRental?
    fun register(book: Book)
    fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?)
    fun delete(id: Long)
}