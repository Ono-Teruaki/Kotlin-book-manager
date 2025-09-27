package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.models.Book
import com.book.manager.domain.models.BookWithRental
import com.book.manager.domain.models.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.mapper.insert
import com.book.manager.infrastructure.database.mapper.BookMapper
import com.book.manager.infrastructure.database.mapper.custom.BookWithRentalMapper
import com.book.manager.infrastructure.database.mapper.custom.select
import com.book.manager.infrastructure.database.mapper.custom.selectByPrimaryKey
import com.book.manager.infrastructure.database.mapper.deleteByPrimaryKey
import com.book.manager.infrastructure.database.mapper.updateByPrimaryKeySelective
import com.book.manager.infrastructure.database.record.Book as BookRecord
import com.book.manager.infrastructure.database.record.custom.BookWithRentalRecord
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl (
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
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

    override fun findWithRental(id: Long): BookWithRental? {
        return bookWithRentalMapper.selectByPrimaryKey(id)?.let { toModel(it) }
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        bookMapper.updateByPrimaryKeySelective(BookRecord(id, title, author, releaseDate))
    }

    private fun toRecord(model: Book): BookRecord {
        return BookRecord(model.id, model.title, model.author, model.releaseDate)
    }

    override fun delete(id: Long) {
        bookMapper.deleteByPrimaryKey(id)
    }
}