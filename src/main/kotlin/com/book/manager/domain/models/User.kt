package com.book.manager.domain.models

import com.book.manager.domain.enums.RoleType

data class User (
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val roleType: RoleType
)