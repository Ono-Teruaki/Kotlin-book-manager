package com.book.manager.presentation.controller

import com.book.manager.application.service.BookService
import com.book.manager.domain.models.Book
import com.book.manager.domain.models.BookWithRental
import com.book.manager.presentation.form.BookInfo
import com.book.manager.presentation.form.GetBookListResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets
import java.time.LocalDate

internal class BookControllerTest {
    private val bookService = mock<BookService>()
    private val bookController = BookController(bookService)

    @Test
    fun `getList is success`() {
        val bookId = 100L
        val book = Book(bookId, "Java入門", "琴麟太郎", LocalDate.now())
        val bookList = listOf(BookWithRental(book, null))

        whenever(bookService.getList()).thenReturn(bookList)
        val expectedResult = GetBookListResponse(listOf(BookInfo(bookId, "Java入門", "琴麟太郎",false)))
        val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectedResult)
        val mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()
        val resultResponse = mockMvc.perform(get("/book/list"))
            .andExpect(status().isOk).andReturn().response

        val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)

        assertThat(expected).isEqualTo(result)
    }
}