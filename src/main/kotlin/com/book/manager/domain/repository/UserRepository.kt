package com.book.manager.domain.repository

import com.book.manager.domain.models.User

interface UserRepository {
    fun find(id: Long): User?
    fun find(email: String): User?
}