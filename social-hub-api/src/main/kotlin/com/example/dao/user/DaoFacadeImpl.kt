package com.example.dao.user

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.User
import com.example.model.UserTable
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DaoFacadeImpl: IDaoFacade {
    private fun rowToUser(row: ResultRow): User =
        User(
            username = row[UserTable.username],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            email = row[UserTable.email],
            phoneNumber = row[UserTable.phoneNumber],
            country = row[UserTable.country],
            gender = row[UserTable.gender]
        )
    override suspend fun getUser(username: String): User? =
        dbQuery {
            UserTable.select {
                UserTable.username eq username
            }.map(::rowToUser).singleOrNull()
        }

    override suspend fun checkCredentials(username: String, password: String): Boolean {
        return transaction {
            val userPassword = UserTable.slice(UserTable.password)
                .select { UserTable.username eq username }
                .singleOrNull()
            userPassword?.let {
                 userPassword[UserTable.password] == password
            } ?: false
        }
    }


    override suspend fun getUserByEmail(email: String): User? {
        return dbQuery {
            UserTable.select {
                UserTable.email eq email
            }.map(::rowToUser).singleOrNull()
        }
    }

    override suspend fun getUserByUserName(userName: String): User? {
        return dbQuery {
            UserTable.select {
                UserTable.username eq userName
            }.map(::rowToUser).singleOrNull()
        }
    }

    override suspend fun getAllUsers(): List<User> =
        dbQuery {
            UserTable.selectAll().map(::rowToUser)
        }

    override suspend fun addUser(
        username: String,
        firstName: String,
        lastName: String,
        gender: String,
        email: String,
        phoneNumber: String,
        country: String,
        password: String
    ): User? =
        dbQuery {
            val insertStatement = UserTable.insert {
                it[UserTable.username] = username
                it[UserTable.firstName] = firstName
                it[UserTable.lastName] = lastName
                it[UserTable.gender] = gender
                it[UserTable.email] = email
                it[UserTable.phoneNumber] = phoneNumber
                it[UserTable.country] = country
                it[UserTable.password] = password
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::rowToUser)
        }


    override suspend fun updateUser(user: User): Boolean =
        dbQuery {
            UserTable.update({ UserTable.username eq user.username }) {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[gender] = user.gender
                it[email] = user.email
                it[phoneNumber] = user.phoneNumber
                it[country] = user.country
            } > 0
        }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): User? {
        return dbQuery {
            UserTable.select {
                UserTable.phoneNumber eq phoneNumber
            }.map(::rowToUser).singleOrNull()
        }
    }

    override suspend fun deleteUser(username: String): Boolean =
        dbQuery {
            UserTable.deleteWhere { UserTable.username eq username } > 0
        }
}
// Database object
val dao: IDaoFacade = DaoFacadeImpl().apply {
    runBlocking {

    }
}