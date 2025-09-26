package com.book.manager.application.service

import com.book.manager.domain.models.BookWithRental
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService (
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findALlWithRental()
    }

    fun getDetail(bookId: Long): BookWithRental {
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
    }
}