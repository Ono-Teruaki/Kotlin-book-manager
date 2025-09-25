package com.book.manager.infrastructure.database.mapper.custom

import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport
import com.book.manager.infrastructure.database.record.custom.BookWithRentalRecord
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.select

private val columnList = listOf(
    BookDynamicSqlSupport.id,
    BookDynamicSqlSupport.title,
    BookDynamicSqlSupport.author,
    BookDynamicSqlSupport.releaseDate,
    RentalDynamicSqlSupport.userId,
    RentalDynamicSqlSupport.rentalDatetime,
    RentalDynamicSqlSupport.returnDeadline
)

fun BookWithRentalMapper.select(): List<BookWithRentalRecord> {
    val selectStatement = select(columnList) {
        from(BookDynamicSqlSupport.book, "b")
        leftJoin(RentalDynamicSqlSupport.rental, "r") {
            on(BookDynamicSqlSupport.id) equalTo BookDynamicSqlSupport.book.id
        }
    }
    return selectMany(selectStatement)
}