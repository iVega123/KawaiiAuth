package com.auth.kawaii.repository

import com.auth.kawaii.model.Role
import com.auth.kawaii.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository (
    private val encoder: PasswordEncoder
){

  private val users = mutableListOf(
      User(
          id = UUID.randomUUID(),
          email = "email-1gmail.com",
          password = encoder.encode("pass1"),
          role = Role.User
      ),
      User(
          id = UUID.randomUUID(),
          email = "email-2gmail.com",
          password = encoder.encode("pass2"),
          role = Role.Admin
      ),
      User(
          id = UUID.randomUUID(),
          email = "email-3gmail.com",
          password = encoder.encode("pass3"),
          role = Role.User
      ),
  )
    fun save(user: User): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))

        return users.add(updated)

    }



    fun findByEmail(email: String): User? =
        users
            .firstOrNull { it.email == email }

    fun findAll(): List<User> =
        users
    fun findByUUID(uuid: UUID): User? =
        users
            .firstOrNull{ it.id == uuid}

    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)

        return  foundUser?.let { 
             users.remove(it)
        } ?: false
    }
}