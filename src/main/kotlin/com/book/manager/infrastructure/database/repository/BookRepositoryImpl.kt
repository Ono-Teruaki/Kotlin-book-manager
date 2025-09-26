package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.models.Book
import com.book.manager.domain.models.BookWithRental
import com.book.manager.domain.models.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.mapper.custom.BookWithRentalMapper
import com.book.manager.infrastructure.database.mapper.custom.select
import com.book.manager.infrastructure.database.record.Book as BookRecord
import com.book.manager.infrastructure.database.record.custom.BookWithRentalRecord
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.Date

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl (
    private val bookWithRentalMapper: BookWithRentalMapper
) : BookRepository {
    override fun findALlWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select().map { toModel(it)}
    }

    private fun toModel(record: BookWithRentalRecord): BookWithRental {
        val book = Book(
            id = record.id!!,
            title = record.title!!,
            author = record.author!!,
            releaseDate = record.releaseDate!!
        )
        val rental = record.userId?.let {
            Rental(
                record.id!!,
                record.userId!!,
                record.rentalDateTime!!,
                record.returnDeadline!!
            )
        }
        return BookWithRental(book, rental)
    }

    private fun toRecord(model: Book): BookRecord {
        return BookRecord(model.id, model.title, model.author, Date.from(model.releaseDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
    }
}