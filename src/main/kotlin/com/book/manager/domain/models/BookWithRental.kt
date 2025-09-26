package com.book.manager.domain.models

data class BookWithRental (
    val book: Book,
    val rental: Rental?
) {
    val isRental: Boolean
    get() = rental != null
}