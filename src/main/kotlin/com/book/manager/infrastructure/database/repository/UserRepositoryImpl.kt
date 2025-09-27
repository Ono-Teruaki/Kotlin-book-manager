package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.models.User
import com.book.manager.domain.repository.UserRepository
import com.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.UserMapper
import com.book.manager.infrastructure.database.mapper.selectByPrimaryKey
import com.book.manager.infrastructure.database.mapper.selectOne
import org.springframework.stereotype.Repository
import com.book.manager.infrastructure.database.record.User as UserRecord

@Suppress("SpringJavaInfectionPointsAutowiringInspection")
@Repository
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {

    override fun find(id: Long): User? {
        val record = mapper.selectByPrimaryKey(id)
        return record?.let { toModel(it)}
    }

    override fun find(email: String): User? {
        val record = mapper.selectOne {
            where {
                UserDynamicSqlSupport.user.email isEqualTo email
            }
        }
        return record?.let { toModel(it) }
    }

    private fun toModel(record: UserRecord): User {
        return User(
            id = record.id!!,
            email = record.email!!,
            password = record.password!!,
            name = record.name!!,
            roleType = record.roleType!!
        )
    }
}